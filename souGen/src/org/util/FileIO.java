package org.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {

	public FileIO() {

	}

	/**
	 * 
	 * @return Document amount
	 */
	public int countDoc(String fileName) {
		File file = new File(fileName);
		String files[];
		files = file.list();
		int num = files.length;
		return num;
	}

	/**
	 * read first line
	 * 
	 * @param fileName
	 * @return url
	 */
	public String readURL(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String tempString = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			tempString = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return tempString;
	}

	/**
	 * read except first line
	 * 
	 * @param fileName
	 * @return content
	 */
	public StringBuilder readContent(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String tempString = null;
		StringBuilder content = null;
		boolean first_line = true;
		try {
			reader = new BufferedReader(new FileReader(file));
			content = new StringBuilder();
			while ((tempString = reader.readLine()) != null) {
				if (first_line) {
					first_line = false;
					continue;
				}
				content.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}
}
