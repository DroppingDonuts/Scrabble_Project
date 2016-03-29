package dictionary;

import java.util.HashMap;

public class LetterDictionary {
	
	private HashMap<Character, Integer> myMap;
	
	public LetterDictionary() {
		myMap = new HashMap<Character, Integer>(36);
		this.build();
	}
	
	private void build() {
		for(LetterValues letter: LetterValues.values()) {
			myMap.put(new Character(letter.letter()), new Integer(letter.value()));
		}
	}
	
	public int get(char c) {
		Object result = myMap.get(new Character(c));
		if(result instanceof Integer) {
			return ((Integer) result).intValue();
		} else {
			return 0;
		}
	}

}
