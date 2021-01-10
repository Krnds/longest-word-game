package com.karinedias.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadDictionary {
	
	/*
	 * Read files from dictionary_fr_en.tar.xz archive (FR/EN dictionaries)
	 */
	
	String file;

	public ReadDictionary(String file) {
		this.file = file;
	}
		

	public List<String> getInput() throws IOException {

		List<String> inputs = new ArrayList<String>();
		// The class loader that loaded the class
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(file);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				inputs.add(line);
			}
		}

		return inputs;
	}

	public static void main(String[] args) throws IOException {

		String file = "english.txt";
		List<String> test = new ArrayList<String>();
		ReadDictionary ri = new ReadDictionary(file);
		test.addAll(ri.getInput());

		for (String line : test) {
			System.out.println(line);
		}
	}


}
