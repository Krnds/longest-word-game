package com.karinedias.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import org.decimal4j.util.DoubleRounder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public final class Draw {

	private Draw() {

	}

	/*
	 * Get a 10 letters random draw based on letters frequencies in EN/FR languages
	 */

	/*
	 * Get 10 letters draw based on 'Roulette Wheel Selection' algorithm
	 */
	public static char[] getDraw(Languages lang) throws IOException, CsvException {

		// calling getFrequencies method
		TreeMap<Character, Double> freq = getFrequencies(lang);

		Random generator = new Random();
		char[] drawArr = new char[10];

		Double[] cumProb = new Double[26];

		Double sum = 0.0;

		for (int i = 0; i <= freq.size() - 1; i++) {

			Double prevValue = (Double) freq.values().toArray()[i];
			if (i == 0) {
				sum = prevValue;
			} else {
				sum += prevValue;
			}
			cumProb[i] = DoubleRounder.round(sum, 3);
		}

		Object[] letters = freq.keySet().toArray();

		for (int i = 0; i < drawArr.length; i++) {
			double random = generator.nextDouble() * cumProb[cumProb.length - 1];
			int index = Arrays.binarySearch(cumProb, random);
			if (index < 0) {
				// Convert negative insertion point to array index.
				index = Math.abs(index + 1);
			}
			drawArr[i] = (char) letters[index];

		}
		return drawArr;

	}

	private static TreeMap<Character, Double> getFrequencies(Languages lang) throws IOException, CsvException {

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

	// TEST
	public static void main(String[] args) throws Throwable, IOException {

		// Select language
		Languages myLanguage = Languages.ENGLISH;

		// Add random generated set of 10 letters
		char[] myDraw = getDraw(myLanguage);

		for (char c : myDraw) {
			System.out.print(Character.toUpperCase(c));
		}
	}

}
