package inf4410tp1;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import inf4410tp1.ServerInterface;
import inf4410tp1.file;

public class Server implements ServerInterface{
	
	private int uniqueId = 0;
	private List<file> listOfElements;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}


	/**
	  * Constructor
	  */
	public Server() {
		super();
		listOfElements = new ArrayList<file>();
	}
	/** Method to run the server (ask what it do exactly!!!)  */
	private void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			ServerInterface stub = (ServerInterface) UnicastRemoteObject
					.exportObject(this, 0);

			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("server", stub);
			System.out.println("Server ready.");
		} catch (ConnectException e) {
			System.err
					.println("Impossible de se connecter au registre RMI. Est-ce que rmiregistry est lancé ?");
			System.err.println();
			System.err.println("Erreur: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erreur: " + e.getMessage());
		}
	}

	/** Generate an unique id for each client who connect to server. The id increment for every new client */
	@Override
	public int generateClientId() {
		uniqueId++;
		return uniqueId;
	}
	
	/** Create an object file in the list of all files in the server  */
	public boolean create(String name){
		for (int i = 0; i < listOfElements.size(); i++) {
			if(listOfElements.get(i).getName() == name){
				return false;
			}
		}
		
		listOfElements.add(new file(name));
		return true;
	}
	
	public ArrayList<ArrayList<String>> list(){
		ArrayList<ArrayList<String>> k=new ArrayList();
        ArrayList<String> l=new ArrayList();
        for (int i = 0; i < listOfElements.size(); i++) {
        	
        }
		return k;
	}
	

}
