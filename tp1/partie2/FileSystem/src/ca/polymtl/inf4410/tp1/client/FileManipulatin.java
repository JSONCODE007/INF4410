package ca.polymtl.inf4410.tp1.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.polymtl.inf4410.tp1.shared.CustomFile;
import ca.polymtl.inf4410.tp1.shared.Utils;

public class FileManipulatin {


	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		System.out.println("you know my name ? Bitch !");
		/*//write file 
		byte data[] = {1,2,3};
		//FileOutputStream out = new FileOutputStream("bitzer");
		//out.write(data);
		//out.close();

		FileInputStream fileInputStream=null;

		File file = new File("bitzer");
		MessageDigest md = MessageDigest.getInstance("MD5");

		String digest = Utils.getFileChecksum(md,file);
		System.out.println("the md5 hash is "+digest);

		byte[] bFile = new byte[(int) file.length()];

		//convert file into array of bytes
		fileInputStream = new FileInputStream(file);
		fileInputStream.read(bFile);
		fileInputStream.close();

		for (int i = 0; i < bFile.length; i++) {
			System.out.print((char)bFile[i]);
		}

		System.out.println("Done"+fileInputStream.hashCode()+bFile);*/


		List<CustomFile> listOfElements = new ArrayList<CustomFile>();
		CustomFile c1 = new CustomFile("thierno");
		CustomFile c2 = new CustomFile("barry");
		listOfElements.add(c1);
		listOfElements.add(c2);
		c1.setCheckSum("ssldslsd");
		
		String checkSum = "ssldslsd";
		String name = "thierno";
		for(CustomFile customFile : listOfElements){
			if(checkSum == "-1" && customFile.getName().equals(name)){
				System.out.println("new file to get -1");
				return ;
			}
			else if(customFile.getName().equals(name)){
				System.out.println("new file to get checksum");
				return;
			}
		}



	}
}