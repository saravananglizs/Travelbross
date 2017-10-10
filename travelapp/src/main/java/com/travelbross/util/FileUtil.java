package com.travelbross.util;

import java.io.*;


public class FileUtil {

	public static String getFileContent(String filePath) {
		StringBuilder result = new StringBuilder("");
		String line = null;
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferReader = new BufferedReader(fileReader);
//			line = bufferReader.readLine();
			while((line = bufferReader.readLine()) != null) {
				result.append(line);
			}
			bufferReader.close();
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

}
