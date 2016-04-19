package functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import dictionary.AllWords;
import dictionary.Word;

public class MaxWordFinder {
	
	public static final int BOARD_SIZE = 15;
	
	// The board encodes double and triple letter scores as 1*2 and 1*3
    // and encodes double and triple word scores as 2*2 and 2*3
    private static final int[][] BOARD = {
        { 6, 1, 1, 2, 1, 1, 1, 6, 1, 1, 1, 2, 1, 1, 6},
        { 1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
        { 1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
        { 2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
        { 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
        { 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
        { 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
        { 6, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 6},
        { 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
        { 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
        { 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
        { 2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
        { 1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
        { 1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
        { 6, 1, 1, 2, 1, 1, 1, 6, 1, 1, 1, 2, 1, 1, 6},
    };
	
	protected static final AllWords ALL = new AllWords();
	
    protected static ArrayList<String> validWords;
    protected static ArrayList<Word> wordList;
	protected static ArrayList<Integer> maxPoints = new ArrayList<Integer>(4);
	protected static ArrayList<String> bestWords = new ArrayList<String>(4);
	
	private static boolean hasDups = false;
	
	private static void initialize() {
		prepMaxHolders();
		//Make our dictionary
		wordList = ALL.getWordList();
		validWords = new ArrayList<String>();
	}
	
	private static void prepMaxHolders() {
		for(int i = 0; i < 3; i++) {
			maxPoints.add(null);
			bestWords.add(null);
		}
	}
	
	public static void main(String[] args) {
		initialize();
		//Time for console interaction
		//I didn't put in safety precautions because the program doesn't need to respond
		//It'll just cause exceptions or bad output
		Scanner input = new Scanner(System.in);
		while(true) {
			//Establishing the first tile on the board
			System.out.print("Please enter the letter on the board: ");
			char given = input.nextLine().toLowerCase().charAt(0);
			System.out.print("Please enter the x coordinate of the letter: ");
			int x = input.nextInt();
			input.nextLine();
			System.out.print("Please enter the y coordinate of the letter: ");
			int y = input.nextInt();
			input.nextLine();
			if(x >= BOARD.length || y >= BOARD[0].length) {
				System.out.println("Invalid coordinates. Please try again.");
				continue;
			}
			//Get the other tiles we have to work with
			System.out.println("First letter has been placed. Please enter the seven player tiles, without spaces. (ex: abcdefg)");
			String allPlayer = input.nextLine();
			char[] letters = new char[8];
			letters[0] = given;
			for(int i = 1; i < 8; i++) {
				letters[i] = allPlayer.charAt(i - 1);
			}
			Arrays.sort(letters);
			//Let's do that search!
			System.out.println("Now searching for the best word...");
			findAllMaxWords(given, x, y, letters);
		}
	}
		
	private static void findAllMaxWords(char given, int x, int y, char[] letters) {
		//quickly scan letters to see if we need to account for multiple board letters in a word
		int pointer = 0;
		while(pointer < letters.length && !hasDups) {
			hasDups = letters[pointer] == given;
			pointer++;
		}
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
					int points = findBestPlacement(given, x, y, thisWord);
					
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
	
	private static int findBestPlacement(char c, int charX, int charY, String word) {
		int location = word.indexOf(c);
		boolean hasNextLoc = hasDups;
		int max = 0;
		while(hasNextLoc) {
			//Seemingly complex logic that just asks: can this word fit on the board?
			boolean hor = false;
			boolean ver = false;
			if(location <= charY && word.length() - 1 - location + charY < BOARD_SIZE) {
				ver = true;
			}
			if(location <= charX && word.length() - 1 - location + charX < BOARD_SIZE) {
				hor = true;
			}
			//Send off the data to another method to calculate the points
			//Which is best, horizontal or vertical?
			if(hor && ver) {
				max = Math.max(max, Math.max(findPointsOnBoard(word, charX - location, charY, true), findPointsOnBoard(word, charX, charY - location, false)));
			} else if(hor) {
				max = Math.max(max, findPointsOnBoard(word, charX - location, charY, true));
			} else if(ver) {
				max = Math.max(max, findPointsOnBoard(word, charX, charY - location, false));
			}
			//For words with letter repeats, like banana!
			if(location + 1 < word.length() && word.indexOf(c, location + 1) != -1) {
				location = word.indexOf(c, location + 1);
			} else {
				hasNextLoc = false;
			}
		}
		return max;
	}
	
	private static int findPointsOnBoard(String word, int stX, int stY, boolean hor) {
		int points = 0;
		int wordMul = 1;
		for(int i = 0; i < word.length(); i++) {
			int letMul = 1;
			int val;
			if(hor) {
				val = BOARD[stY][stX + i];
			} else {
				val = BOARD[stY + i][stX];
			}
			switch (val) {
				case 1:
					break;
				case 2:
					letMul = 2;
					break;
				case 3:
					letMul = 3;
					break;
				case 4:
					wordMul *= 2;
					break;
				case 5:
					wordMul *= 3;
					break;
			}
			points += Word.DICTIONARY.get(word.charAt(i)) * letMul;
		}
		points *= wordMul;
		//Add in the 50 point max word length bonus
		if(word.length() == 8) {
			points += 50;
		}
		return points;
	}

}
