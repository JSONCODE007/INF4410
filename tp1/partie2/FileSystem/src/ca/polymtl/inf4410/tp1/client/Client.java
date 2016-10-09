package ca.polymtl.inf4410.tp1.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.polymtl.inf4410.tp1.shared.ServerInterface;
import ca.polymtl.inf4410.tp1.shared.CustomFile;
import ca.polymtl.inf4410.tp1.shared.Constant;


public class Client {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String distantHostname = null;
		String operation = null;
		String argument =null;
		if (args.length > 0) {
			distantHostname = args[0];
			operation = args.length>1?args[1]:null;
			argument = args.length>2 ?args[2]:null;
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
			case "id":
				int result = distantServerStub.generateClientId();
				System.out.println("Client id = " + result);
				break;

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
				fileListDistant = distantServerStub.syncLocalDir();
				for (CustomFile file : fileListDistant) {
					FileOutputStream out = new FileOutputStream(file.getName());
					out.write(file.getContent());
					out.close();
				}

				break;
			case "get":
				
				File f = new File(argument);
				if(!f.exists()) { 
					CustomFile getResult = distantServerStub.get(argument, "-1");
					if(getResult!=null){
						FileOutputStream out = new FileOutputStream(getResult.getName());
						out.write(getResult.getContent());
						out.close();
					}
					else{
						System.out.println(Constant.FILE_DOESNT_EXIST);
					}
				}
				
				else{
					MessageDigest md = MessageDigest.getInstance("MD5");
					try (InputStream is = Files.newInputStream(Paths.get(argument));
					     DigestInputStream dis = new DigestInputStream(is, md)) 
					{
					  /* Read decorated stream (dis) to EOF as normal... */
					}
					String digest = md.digest().toString();
					
					CustomFile getResult = distantServerStub.get(argument, digest);
					
					if(getResult!=null){
						FileOutputStream out = new FileOutputStream(getResult.getName());
						out.write(getResult.getContent());
						out.close();
					}
					
					else{
						System.out.println(Constant.FILE_SAME);
					}
				}
				
				
				

				break;
			case "lock":

				break;
			case "push":

				break;

			}
		}catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}
	}	
}
