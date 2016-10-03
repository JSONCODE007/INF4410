package ca.polymtl.inf4410.tp1.shared;

public class file {
	
	private String name;
	private int checkSum;
	private boolean isLocked;
	private byte[] content;
	
	
	/**
	  * Constructor
	  * 
	  * @param name (required) name of the file to create
	  */
	public file(String name) {
		this.name = name;
		this.checkSum = 0;
		this.isLocked = false;	
		this.content = null;
	}
	
	/** Return name of the file  */
	public String getName(){
		return name;
	}

}
