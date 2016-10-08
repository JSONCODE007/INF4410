package ca.polymtl.inf4410.tp1.client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.polymtl.inf4410.tp1.shared.ServerInterface;
import ca.polymtl.inf4410.tp1.shared.CustomFile;


public class Client {
	
	public static void main(String[] args) {
		String distantHostname = null;
        String operation = null;
        String argument =null;
		if (args.length > 0) {
			distantHostname = args[0];
			operation = args[1];
			argument = args[2];
			System.out.println("the operation is "+ operation);
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

	private void run() {

		System.out.println("Client started" );

		if (distantServerStub != null) {
			appelRMIDistant();
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
     
	

	private void appelRMIDistant() {
	   performOperation();
	}
	
	
	/**
	 * 
	 */
	private void performOperation(){
		System.out.println("Client slslsddsl = " + operation);
		try {
		switch(operation){
		case "id":
	        int result = distantServerStub.generateClientId();
			System.out.println("Client id = " + result);
			break;
			
		case "create":
			String createResult = distantServerStub.create(argument);
			System.out.println("Creation resulta = " + createResult);
			createTest();
			break;
		case "list":
			
			break;
		case "syncLocalDir":
			
			break;
		case "get":
			
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
	private void createTest(){
		List<CustomFile> listOfElements =  new ArrayList<CustomFile>();
	   listOfElements.add(new CustomFile("blablabl"));
		boolean x = true;
		String kopp="blablabl";
		//listOfElements.add(new CustomFile("fuck you "));
		if(listOfElements.size()>0){
		for (CustomFile customFile : listOfElements) {
			if(customFile.getName() == kopp)
				x= false;
			}
		}else{
			System.out.println("given array is empty");
		}
		if(x)
			listOfElements.add(new CustomFile(kopp));
		System.out.println("array size"+listOfElements.size());
	}
}
