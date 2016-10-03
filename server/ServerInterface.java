package inf4410tp1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
	
	int generateClientId();
	boolean create(String name);
}
