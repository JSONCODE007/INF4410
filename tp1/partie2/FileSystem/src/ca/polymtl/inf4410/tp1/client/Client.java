package ca.polymtl.inf4410.tp1.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.rmi.CORBA.Util;

import ca.polymtl.inf4410.tp1.shared.ServerInterface;
import ca.polymtl.inf4410.tp1.shared.Utils;
import ca.polymtl.inf4410.tp1.shared.CustomFile;
import ca.polymtl.inf4410.tp1.shared.Constant;


public class Client {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String distantHostname = null;
		String operation = null;
		String argument =null;
		if (args.length > 0) {
			distantHostname = args[0];
			operation = args.length>1 ? args[1]:null;
			argument = args.length>2  ? args[2]:null;
			System.out.println("current operation "+ operation);
		}

		Client client = new Client(distantHostname,operation,argument);
		client.run();
	}


	private ServerInterface distantServerStub = null;
	private String operation = null;
	private String argument = null;

	public Client(String distantServerHostname,String op,String arg) {
		super();

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}


		if (distantServerHostname != null) {
			distantServerStub = loadServerStub(distantServerHostname);
			operation = op ;
			argument = arg;
			System.out.println("test id = " + operation +argument);
		}
	}

	private void run() throws NoSuchAlgorithmException, IOException {

		System.out.println("Client started" );

		if (distantServerStub != null) {
			performOperation();
		}
	}

	private ServerInterface loadServerStub(String hostname) {
		ServerInterface stub = null;

		try {
			Registry registry = LocateRegistry.getRegistry(hostname);
			stub = (ServerInterface) registry.lookup("server");
		} catch (NotBoundException e) {
			System.out.println("Erreur: Le nom '" + e.getMessage()
			+ "' n'est pas défini dans le registre.");
		} catch (AccessException e) {
			System.out.println("Erreur: " + e.getMessage());
		} catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}

		return stub;
	}
	/**
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	private void performOperation() throws IOException, NoSuchAlgorithmException{
		System.out.println("------perform operation ------");
		try {
			switch(operation){
			case "create":
				String createResult = distantServerStub.create(argument);
				System.out.println(createResult);
				//createTest();
				break;
			case "list":
				List<String> fileList = new ArrayList<String>();
				fileList = distantServerStub.list();
				for (String string : fileList) {
					System.out.println(string);
				}
				break;
			case "syncLocalDir":
				
				List<CustomFile> fileListDistant = new ArrayList<CustomFile>();
				HashMap<String,byte[]> fileMap = distantServerStub.syncLocalDir();
				for (Entry<String, byte[]> file : fileMap.entrySet()) {
					Utils.WriteFileInClientDirectory(file.getKey(),file.getValue());
				}

				break;
			case "get":

				File f = new File(argument);
				if(!f.exists()) { 
					System.out.println("in a new please");
					byte[] fileContent = distantServerStub.get(argument, "-1");
					System.out.println("in a new please");
					if(fileContent!= null){
						Utils.WriteFileInClientDirectory(argument,fileContent);
					}
					else{
						System.out.println(Constant.FILE_DOESNT_EXIST);
					}
				}
				else{

					String checkSum = Utils.getFileChecksum(f);
					byte[] fileContent = distantServerStub.get(argument, checkSum);
					System.out.println(checkSum);
					if(fileContent!=null){
						Utils.WriteFileInClientDirectory(argument, fileContent);
					}

					else{
						System.out.println(Constant.FILE_SAME);
					}
				}

				break;
			case "lock":

				File clientFile = new File(Constant.CLIENT_ID_FILE_NAME);
				int clientId =  -1;
				byte[] configContent  = null;
				//parse configuration file to get clietn id or generate new 
				if(clientFile.exists()) { 
					System.out.println("un fichier de config existe deja !");
					configContent = Utils.getFileContent(clientFile);
					//TODO:check if file is not altered if id it there exists
					clientId = Integer.parseInt(new String(configContent));

				}else{
					clientId = distantServerStub.generateClientId();
					System.out.println("Client id = " + clientId);
					configContent =  String.valueOf(clientId).getBytes();
					Utils.WriteFileInClientDirectory(Constant.CLIENT_ID_FILE_NAME, configContent);
				}

				File fileToLock = new File(argument);  
				String fileCheckSum = fileToLock.exists() ? Utils.getFileChecksum(fileToLock) : "-1"; 
				System.out.println("file to lock checsum"+fileCheckSum);
				byte[] lockContent = distantServerStub.lock(argument, clientId,fileCheckSum);
				if(lockContent !=null){
					Utils.WriteFileInClientDirectory(argument, lockContent);
					System.out.println(Constant.FILE_IN_EDIT_STATE(argument));
				}else{
					System.out.println("fichier verouille ou inexistant");
				}

				break;
			case "push":
				byte[] clientconfigContent = Utils.getFileContent(new File(Constant.CLIENT_ID_FILE_NAME));
				//TODO:check if file is not altered if id it there exists
				int currentClientId = Integer.parseInt(new String(clientconfigContent));

				byte[] toPushContent  = Utils.getFileContent(new File(argument));

				String pushResponse = distantServerStub.push(argument, toPushContent, currentClientId);

				System.out.println(pushResponse);

				break;

			}
		}catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}
	}	



}