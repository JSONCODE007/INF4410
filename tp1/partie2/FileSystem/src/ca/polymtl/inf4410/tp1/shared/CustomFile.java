package ca.polymtl.inf4410.tp1.shared;

public class CustomFile {
	
	private String _name;
	private int _checkSum;
	private boolean _isLocked;
	private byte[] _content;
	private int lockedBy;
	
	
	/**
	  * Constructor
	  * 
	  * @param name (required) name of the file to create
	  */
	public CustomFile(String name) {
		this._name = name;
		this._checkSum = 0;
		this._isLocked = false;	
		this._content = null;
		this.lockedBy = 0;//TODO:fix me please
	}
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return _name;
	}
	/***
	 * 
	 * @return
	 */
	public int getCheckSum() {
		return _checkSum;
	}
	/**
	 * 
	 * @return
	 */
	public byte[] getContent() {
		return _content;
	}
	/***
	 * 
	 * @return
	 */
	public boolean isLocked(){
		return _isLocked;
	}
	/***
	 * 
	 * @return
	 */
	public int getLockedBy() {
		return lockedBy;
	}
	/***
	 * 
	 * @param clientId
	 */
	public void lock(int clientId) {
		this.lockedBy = clientId;
		this._isLocked  = true;
	}

}
