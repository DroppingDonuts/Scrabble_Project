package dictionary;

import java.util.Arrays;

public class Word {
	
	private String word;
	private char[] sortedLetters;
	private int plainPoints;
	
	public static final LetterDictionary DICTIONARY = new LetterDictionary();
	
	public Word(String w) {
		word = w;
		sortedLetters = w.toCharArray();
		Arrays.sort(sortedLetters);
		calculatePlainPoints();
	}
	
	public String getWord() {
		return word;
	}
	
	public char[] getSorted() {
		return sortedLetters;
	}
	
	public int getPlainPoints() {
		return plainPoints;
	}
	
	private void calculatePlainPoints() {
		for(char c: sortedLetters) {
			plainPoints += DICTIONARY.get(c);
		}
	}
	
	public String printSorted() {
		return Arrays.toString(sortedLetters);
	}
	
	/**
	 * For the board letter, determines if it contains that single letter
	 * @param letter
	 * @return
	 */
	public boolean containsLetter(char letter) {
		//Establish the "markers" for binary search
		int start = 0;
		int end = sortedLetters.length - 1;
		
		//Perform a binary search
		while(end >= start) {
			int mid = (end + start) / 2;
			char test = sortedLetters[mid];
			if(test == letter) {
				return true;
			} else if(test < letter) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		
		//Didn't find it
		return false;
	}
	
	/**
	 * A binary search method used by the class to see if the word contains any of a letter
	 * @param letter
	 * @return
	 */
	private int findLetter(char letter) {
		//Establish the "markers" for binary search
		int start = 0;
		int end = sortedLetters.length - 1;
		int mid = (end - start) / 2;
		
		//Perform a binary search
		while(end - start != 0) {
			char test = sortedLetters[mid];
			if(test == letter) {
				return mid;
			} else if(test > letter) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
			mid = (end - start) / 2;
		}
		
		//Didn't find it
		return -1;
	}
	
	/**
	 * Used to find the number of a letter in a word to determine if you can make it
	 * @param letter
	 * @return
	 */
	public int numOfLetter(char letter) {
		//Find one instance of the letter
		int pos = findLetter(letter);
		if(pos == -1) {
			return 0;
		}
		
		//Count the found instance as one
		int instances = 1;
		
		//Find all instances to the left of the found one
		int move = 1;
		while(pos - move >= 0 && sortedLetters[pos - move] == letter) {
			instances++;
			move++;
		}
		
		//Find all instances to the right of the found one
		move = 1;
		while(pos + move < sortedLetters.length && sortedLetters[pos + move] == letter) {
			instances++;
			move++;
		}
		
		return instances;
	}

}
