package ca.polymtl.inf4410.tp1.server;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.polymtl.inf4410.tp1.shared.Constant;
import ca.polymtl.inf4410.tp1.shared.CustomFile;
import ca.polymtl.inf4410.tp1.shared.ServerInterface;
import ca.polymtl.inf4410.tp1.shared.Utils;

public class Server implements ServerInterface {

	private int uniqueId = 0;
	private List<CustomFile> listOfElements;

	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}

	public Server() {
		super();
		listOfElements = new ArrayList<CustomFile>();
	}

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
	public int generateClientId() throws RemoteException {
		uniqueId++;
		return uniqueId;
	}

	/** Create an object file in the list of all files in the server  */
	public String create(String name) throws RemoteException{
		if(listOfElements.size() > 0) {
			for (CustomFile customFile : listOfElements) {
				if(customFile.getName().equals(name)){
					return Constant.DUPLICATED_FILENAME;
				}
			}
		}
		listOfElements.add(new CustomFile(name));
		return Constant.FILE_ADDED(name);
	}

	public List<String> list() throws RemoteException{
		List<String> fileList = new ArrayList<String>();

		if(listOfElements.size() >0){
			for(CustomFile customFile : listOfElements){
				if(customFile.isLocked()){
					fileList.add(Constant.LOCKED(customFile.getName(),customFile.getLockedBy()));
				}else{
					fileList.add(Constant.NOT_LOCKED(customFile.getName()));

				}
			}
		}else{
			fileList.add(Constant.EMPTY_LIST);
		}

		return fileList;
	}

	@Override
	public ArrayList<CustomFile> syncLocalDir() throws RemoteException {
		return (ArrayList<CustomFile>) listOfElements;
	}

	@Override
	public byte[]  get(String name, String checkSum) throws RemoteException {

		for(CustomFile customFile : listOfElements){
			if(customFile.getName().equals(name)){
				//TODO:la deuxieme condition est jamais verifier
				if(checkSum.equals("-1") || (!customFile.getCheckSum().equals(checkSum))){
					System.out.println("new file to get -1");
					return customFile.getContent();
				}
			}
		}

		System.out.println("je retourne null");
		return null;

	}

	@Override
	public byte[] lock(String name, int clientId, String checkSum) throws RemoteException {
		CustomFile fileToLock = null;
		//file file to lock
		for(CustomFile customFile : listOfElements){
			if(customFile.getName().equals(name)){
				fileToLock = customFile;
				break; 
			}
		}
		//if file was founded in the list of files
		if(fileToLock!=null){
			if(!fileToLock.isLocked()){
				System.out.println("file not  locked");
				if(checkSum.equals("-1") || (!fileToLock.getCheckSum().equals(checkSum))){
					System.out.println("new file to get  in lock-1");
					return fileToLock.getContent();
				}
			}else{
				System.out.println("file is already locked by "+fileToLock.getLockedBy());
			}
			fileToLock.lock(clientId);
		}
		
		System.out.println("file not founded");

		return null;
	}

	@Override
	public String push(String name, byte[] content, int clientId)
			throws RemoteException {
		// TODO Auto-generated method stub


		return null;
	}
}
