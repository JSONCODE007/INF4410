package ca.polymtl.inf4410.tp1.shared;

public class CustomFile {
	
	private String _name;
	private int _checkSum;
	private boolean _isLocked;
	private byte[] _content;
	
	
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
	}
	
	public String getName() {
		return _name;
	}
	public int getCheckSum() {
		return _checkSum;
	}
	public byte[] getContent() {
		return _content;
	}

}
