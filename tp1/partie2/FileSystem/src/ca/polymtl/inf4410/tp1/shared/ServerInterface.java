package ca.polymtl.inf4410.tp1.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ServerInterface extends Remote {
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	int generateClientId() throws RemoteException;
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	String create(String name)throws RemoteException;
	
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List<String>  list() throws RemoteException;
	
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	ArrayList<CustomFile> syncLocalDir() throws RemoteException;
	
	/**
	 * 
	 * @param name
	 * @param digest
	 * @return
	 * @throws RemoteException
	 */
	CustomFile get(String name, String digest) throws RemoteException;
	
	/**
	 * 
	 * @param name
	 * @param clientId
	 * @param checkSum
	 * @return
	 * @throws RemoteException
	 */
	boolean lock(String name, int clientId, int checkSum)throws RemoteException;
	
	/**
	 * 
	 * @param name
	 * @param content
	 * @param clientId
	 * @return
	 * @throws RemoteException
	 */
	String push(String name, byte[] content, int clientId)throws RemoteException;
}
