package ca.polymtl.inf4410.tp1.shared;

public class CustomFile {
	
	private String _name;
	private String _checkSum;
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
		this._checkSum = "";
		this._isLocked = false;	
		this._content = new byte[0];
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
	public String getCheckSum() {
		return _checkSum;
	}
	/**
	 * 
	 * @return
	 */
	public byte[] getContent() {
		return _content;
	}
	/**
	 * 
	 * @param _content
	 */
	public void setContent(byte[] _content) {
		this._content = _content;
	}
	public void setCheckSum(String _checkSum) {
		this._checkSum = _checkSum;
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
	 * lock a file 
	 * @param clientId The genrated client id
	 */
	public void lock(int clientId) {
		this.lockedBy = clientId;
		this._isLocked  = true;
	}
	/***
	 * unlock a  file
	 */
	public void unlock() {
			_isLocked = false;
			 this.lockedBy =0;
	}

}
