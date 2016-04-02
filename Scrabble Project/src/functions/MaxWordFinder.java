package functions;

import java.util.ArrayList;
import java.util.Arrays;

import dictionary.AllWords;
import dictionary.Word;

public class MaxWordFinder {
	
	protected static ArrayList<String> validWords;
	protected static int[] maxPoints = new int[3];
	protected static String[] bestWords = new String[3];
	
	public static void main(String[] args) {
		//Make our dictionary
		AllWords all = new AllWords();
		ArrayList<Word> wordList = all.getWordList();
		
		validWords = new ArrayList<String>();
		char given = 'l';
		char[] letters = {'b', 'c', 'd', 'e', 'a', 'g', 'h', ' '};
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
					validWords.add(thisWord);
					//calculate point value of this word and add to points array
				}
			}
		}
		System.out.println(validWords);
	}

}
