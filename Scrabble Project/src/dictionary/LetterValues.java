package dictionary;

public enum LetterValues {
	
	A('a', 1),
	B('b', 3),
	C('c', 3),
	D('d', 2),
	E('e', 1),
	F('f', 4),
	G('g', 2),
	H('h', 4),
	I('i', 1),
	J('j', 8),
	K('k', 5),
	L('l', 1),
	M('m', 3),
	N('n', 1),
	O('o', 1),
	P('p', 3),
	Q('q', 10),
	R('r', 1),
	S('s', 1),
	T('t', 1),
	U('u', 1),
	V('v', 4),
	W('w', 4),
	X('x', 8),
	Y('y', 4),
	Z('z', 10)
	;
	
	private final int value;
	private final char letter;
	LetterValues(char l, int v) {
		this.value = v;
		this.letter = l;
	}
	
	public int value() { return value; }
    public char letter() { return letter; }

}
