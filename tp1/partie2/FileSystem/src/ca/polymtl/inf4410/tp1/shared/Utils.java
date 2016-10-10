package ca.polymtl.inf4410.tp1.shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	
	/***
	 * This function is taken from http://howtodoinjava.com/core-java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
	 * @param digest
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	public static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("MD5");

	    //Get file input stream for reading the file content
	    FileInputStream fis = new FileInputStream(file);
	     
	    //Create byte array to read data in chunks
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0; 
	      
	    //Read file data and update in message digest
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };
	     
	    //close the stream; We don't need it now.
	    fis.close();
	     
	    //Get the hash's bytes
	    byte[] bytes = digest.digest();
	     
	    //This bytes[] has bytes in decimal format;
	    //Convert it to hexadecimal format
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i< bytes.length ;i++)
	    {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	     
	    //return complete hash
	   return sb.toString();
	}
	
	/***
	 * 
	 * @return
	 * @throws IOException 
	 */
	public static  void  WriteFileInClientDirectory(String name,byte[] content) throws IOException{
		FileOutputStream out = new FileOutputStream(name);
		out.write(content);
		out.close();;
	}
	
	/***
	 * 
	 * @return
	 * @throws IOException 
	 */
	public static byte[]  getFileContent(File file) throws IOException{
		byte[] bFile = new byte[(int) file.length()];

		//convert file into array of bytes
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bFile);
		fileInputStream.close();

		return bFile;
	}
	
	
	
}
