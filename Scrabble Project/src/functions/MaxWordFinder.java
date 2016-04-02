package functions;

import java.util.ArrayList;
import java.util.Arrays;

import dictionary.AllWords;
import dictionary.Word;

public class MaxWordFinder {
	
	protected static ArrayList<String> validWords;
	protected static ArrayList<Integer> maxPoints = new ArrayList<Integer>(4);
	protected static ArrayList<String> bestWords = new ArrayList<String>(4);
	
	private static void prepMaxHolders() {
		for(int i = 0; i < 3; i++) {
			maxPoints.add(null);
			bestWords.add(null);
		}
	}
	
	public static void main(String[] args) {
		prepMaxHolders();
		//Make our dictionary
		AllWords all = new AllWords();
		ArrayList<Word> wordList = all.getWordList();
		
		validWords = new ArrayList<String>();
		char given = 'e';
		char[] letters = {'q', 'c', 'u', 'e', 'a', 'g', 'h', ' '};
		letters[7] = given;
		Arrays.sort(letters);
		for (int i = 0; i < wordList.size(); i++) {
			//Make sure it includes the board letter
			if (wordList.get(i).containsLetter(given)) {
				//Get the necessary parts of the word
				Word current = wordList.get(i);
				char[] sorted = current.getSorted();
				//The word string will only become necessary when determining word position
				String thisWord = current.getWord();
				//Initialize pointers and such
				boolean containsLetters = true;
				int wordPos = 0;
				int arrPos = 0;
				char nxtLetter = sorted[0];
				
				//While it has what we need
				while(containsLetters && wordPos < sorted.length) {
					//Scroll past letters earlier in the alphabet that the word doesn't have
					while(arrPos < letters.length && letters[arrPos] < nxtLetter) {
						arrPos++;
					}
					//Stop if we can't find the letter we need
					if(arrPos >= letters.length || letters[arrPos] != nxtLetter) {
						containsLetters = false;
					}
					
					//This while is a bit long, but here's what I'm doing:
					//1st: Make sure we aren't at the end of the array of the word
					//2nd: Make sure we haven't switched to a different letter
					//3rd: Make sure there is another copy of the current letter on the board
					//4th: Check to see if we need another of that letter and keep going
					while(containsLetters && wordPos < sorted.length) {
						if(nxtLetter != sorted[wordPos]) {
							nxtLetter = sorted[wordPos];
							break;
						}
						//Do we have the letter we need?
						if(arrPos < letters.length && letters[arrPos] == sorted[wordPos]) {
							arrPos++;
							wordPos ++;
						} else {
							containsLetters = false;
						}
					}
				}
				//Yay! We found a word that meets our requirements!
				if (containsLetters) {
					//I kept in validWords for debugging just in case
					validWords.add(thisWord);
					//This uses a less efficient 'plain points' method - points without taking into account
					//the board set-up
					int points = current.getPlainPoints();
					
					//Break statements are used to not set all of the 'best words' to the top word
					for(int j = 0; j < 3; j++) {
						if(maxPoints.get(j) == null) {
							maxPoints.set(j, Integer.valueOf(points));
							bestWords.set(j, thisWord);
							break;
						} else if(maxPoints.get(j).intValue() < points) {
							//Insert the word in
							maxPoints.add(j, Integer.valueOf(points));
							bestWords.add(j, thisWord);
							//Get rid of the bottom words
							maxPoints.set(3, null);
							bestWords.set(3, null);
							break;
						}
					}
				}
			}
		}
		System.out.println(validWords);
		System.out.println(bestWords);
		System.out.println(maxPoints);
	}

}
