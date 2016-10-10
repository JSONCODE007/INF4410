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
		}

		Client client = new Client(distantHostname);
		client.run(operation,argument);
	}


	private ServerInterface distantServerStub = null;

	public Client(String distantServerHostname) {
		super();

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		if (distantServerHostname != null) {
			distantServerStub = loadServerStub(distantServerHostname);
		}
	}

	private void run(String operation,String fileName) throws NoSuchAlgorithmException, IOException {

		System.out.println("Client started  \n" );

		if (distantServerStub != null && operation !=null) {
			performOperation(operation,fileName);
		}else{
			System.out.println("Provide an argument to interact wiht the server");
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
	private void performOperation(String operation,String fileName) throws IOException, NoSuchAlgorithmException{

		//create or get the file containing the client id
		int clientId = getClientId();	

		try {

			switch(operation){

			case "create":

				String createResult = distantServerStub.create(fileName);
				System.out.println(createResult);
				break;

			case "list":

				List<String> listResult = new ArrayList<String>();
				listResult = distantServerStub.list();
				for (String string : listResult) {
					System.out.println(string);
				}
				break;

			case "syncLocalDir":

				HashMap<String,byte[]> syncLocalDirResult = distantServerStub.syncLocalDir();
				for (Entry<String, byte[]> file : syncLocalDirResult.entrySet()) {
					Utils.WriteFileInClientDirectory(file.getKey(),file.getValue());
				}

				break;

			case "get":

				File f = new File(fileName);
				if(!f.exists()) { 
					byte[] fileContent = distantServerStub.get(fileName, "-1");
					if(fileContent!= null){
						Utils.WriteFileInClientDirectory(fileName,fileContent);
						System.out.println(Constant.FILE_UPLOADED);
					}
					else{
						System.out.println(Constant.FILE_DOESNT_EXIST);
					}
				}
				else{

					String checkSum = Utils.getFileChecksum(f);
					byte[] fileContent = distantServerStub.get(fileName, checkSum);
					if(fileContent!=null){
						Utils.WriteFileInClientDirectory(fileName, fileContent);
						System.out.println(Constant.FILE_UPLOADED);
					}	else {
						System.out.println(Constant.FILE_SAME);
					}
				}

				break;

			case "lock":

				File fileToLock = new File(fileName);  
				String fileCheckSum = fileToLock.exists() ? Utils.getFileChecksum(fileToLock) : "-1"; 
				byte[] lockContent = distantServerStub.lock(fileName, clientId,fileCheckSum);
				if(lockContent !=null ){
					Utils.WriteFileInClientDirectory(fileName, lockContent);
					System.out.println(Constant.FILE_IN_EDIT_STATE(fileName));
				}else{
					System.out.println("fichier inexistant ou verouiller par un autre utilisateur !");
				}

				break;
				
			case "push":

				File fileToPush = new File(fileName);  
				byte[] toPushContent  = Utils.getFileContent(fileToPush);
				String pushResponse = distantServerStub.push(fileName, toPushContent, clientId);
				System.out.println(pushResponse);

				break;

			default:
				System.out.println("This operation is not supported !");
			}
		} catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}

		System.out.println();
	}	

	/***
	 * recuperer le fichier de .client_id ou en creer un 
	 * @return
	 * @throws RemoteException
	 */
	private int getClientId() throws RemoteException {

		File clientFile = new File(Constant.CLIENT_ID_FILE_NAME);
		int clientId =  -1;
		byte[] configContent  = null;
		//parse configuration file to get clietn id or generate new 
		if(clientFile.exists()) { 
			try {
				configContent = Utils.getFileContent(clientFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//TODO:check if file is not altered if id it there exists
			clientId = Integer.parseInt(new String(configContent)); 

		}else{
			clientId = distantServerStub.generateClientId();
			configContent =  String.valueOf(clientId).getBytes();
			try {
				Utils.WriteFileInClientDirectory(Constant.CLIENT_ID_FILE_NAME, configContent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return clientId;

	}

}