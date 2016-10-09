package ca.polymtl.inf4410.tp1.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManipulatin {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("you know my name ? Bitch !");

		//write file 
		byte data[] = {1,2,3};
		//FileOutputStream out = new FileOutputStream("bitzer");
		//out.write(data);
		//out.close();

		FileInputStream fileInputStream=null;

		File file = new File("bitzer");

		byte[] bFile = new byte[(int) file.length()];

		//convert file into array of bytes
		fileInputStream = new FileInputStream(file);
		fileInputStream.read(bFile);
		fileInputStream.close();

		for (int i = 0; i < bFile.length; i++) {
			System.out.print((char)bFile[i]);
		}

		System.out.println("Done"+fileInputStream.hashCode()+bFile);

	}
}