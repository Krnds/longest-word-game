package com.karinedias.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Draw {

	/*
	 * Get a 10 letters random draw based on letters frequencies in EN/FR languages
	 */

	
	// méthode pour le tirage
	public static void getDraw(TreeMap<Character, Double> freq) { 
		Random r = new Random();
		List<Character> draw = new ArrayList<>();
		
		for (int i = 1; i <= 10; i++) {
			
		}
		
		Random generator = new Random();
		Object[] values = freq.values().toArray();
		Object randomValue = values[generator.nextInt(values.length)];
		
		System.out.println(randomValue.toString());
		
	}
	
	public static TreeMap<Character, Double> getFrequencies (Languages lang) throws IOException, CsvException {
		
		ClassLoader classLoader = Draw.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("letter_frequencies.csv");

		// Create TreeMaps (null values not allowed, sorted, O(log n) complexity
		TreeMap<Character, Double> englishFrequencies = new TreeMap<>();
		TreeMap<Character, Double> frenchFrequencies = new TreeMap<>();

		List<String[]> list = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
			csvReader.skip(1);
			list = csvReader.readAll();

			csvReader.close();
		}
		for (String[] strings : list) {
			englishFrequencies.put(strings[1].charAt(0), Double.parseDouble(strings[2]));
			frenchFrequencies.put(strings[1].charAt(0), Double.parseDouble(strings[3]));
		}
		
		switch (lang) {
		case ENGLISH:
			return englishFrequencies;
		case FRENCH:
			return frenchFrequencies;
		default:
			return englishFrequencies;
		}
		
	}

	public static void main(String[] args) throws Throwable, IOException {


		Languages myLanguage = Languages.ENGLISH;
		
		getDraw(getFrequencies(myLanguage));
	}

}
