package dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AllWords {
	
	//This class won't have static elements in the final
	//The main is just for testing
	public static ArrayList<Word> wordList;
	
	public AllWords() {
		wordList = new ArrayList<Word>(79339);
		
		File ospd = new File("src/dictionary/ospd.txt");
		//Making a scanner and just reading from the file
		Scanner file;
		
		//This try-catch is kind of sloppy and might want to be cleaned up
		try {
			//This code just build the wordList with all of the words
			file = new Scanner(ospd);
			while(file.hasNextLine()) {
				wordList.add(new Word(file.nextLine()));
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void build() {
		File ospd = new File("src/dictionary/ospd.txt");
		//Making a scanner and just reading from the file
		Scanner file;
		
		//This try-catch is kind of sloppy and might want to be cleaned up
		try {
			//This code just build the wordList with all of the words
			file = new Scanner(ospd);
			while(file.hasNextLine()) {
				wordList.add(new Word(file.nextLine()));
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Word> getWordList() {
		return wordList;
	}

}
