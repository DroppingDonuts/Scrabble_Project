package framework;

/**
 * Encapsulation of letter location on a Scrabble Board.
 * A letter's location is an ordered pair of positive or negative
 * integers, with the x-axis being the first, and the y-axis the second.
 * The center of the board is 0,0, and has denoted by the constant
 * Location.CENTER
 * 
 * NOTE: The locations do NOT equal any underlying array indices.
 */

/**
 * @author Mr. Peterson
 * For Advanced Placement Computer Science, May 2016 
 */

public class Square {

    public static final Square CENTER = new Square();

    //number of horizontal spaces from center
    public final int horizontalFromCenter;

    // number of vertical spaces from center
    public final int verticalFromCenter;

    // canonical row identifiers
    private final static String ROW_IDENTIFIERS = "ABCDEFGHIJKLMNO";
    /**
     * Valid constructor based on offset from CENTER
     * @param x - number of horizontal spaces from center
     * @param y - number of vertical spaces from center
     */
    public Square(int x, int y) {
        if (x > 7 || x < -7) throw new IndexOutOfBoundsException("" + x + " exceeds x");
        if (y > 7 || y < -7) throw new IndexOutOfBoundsException("" + y + " exceeds y");
        horizontalFromCenter = x;
        verticalFromCenter = y;
    }

    /**
     * Valid constructor based on standard notation A-O, 1-15
     * @param x - number of horizontal spaces from center
     * @param x - number of vertical spaces from center
     */
    public Square(char letter, int x) {
        if (x > 15 || x < 1) throw new IndexOutOfBoundsException("" + x + " exceeds y");
        int y;
        switch (letter) {
        case 'a' :
        case 'A' : y = -7; break;
        case 'b' :
        case 'B' : y = -6; break;
        case 'c' :
        case 'C' : y = -5; break;
        case 'd' :
        case 'D' : y = -4; break;
        case 'e' :
        case 'E' : y = -3; break;
        case 'f' :
        case 'F' : y = -2; break;
        case 'g' :
        case 'G' : y = -1; break;
        case 'h' :
        case 'H' : y = 0; break;
        case 'i' :
        case 'I' : y = 1; break;
        case 'j' :
        case 'J' : y = 2; break;
        case 'k' :
        case 'K' : y = 3; break;
        case 'l' :
        case 'L' : y = 4; break;
        case 'm' :
        case 'M' : y = 5; break;
        case 'n' :
        case 'N' : y = 6; break;
        case 'o' :
        case 'O' : y = 7; break;
        default: throw new IndexOutOfBoundsException(letter + " exceeds x");
        }

        horizontalFromCenter = x - 8;
        verticalFromCenter = y;
    }
    
    /**
     * Constructor for canononical string that is concatenated from
     * A-O and 1-15
     * @param iden - A1 to O15
     */
    public Square(String iden) {
        this(iden.charAt(0), Integer.decode(iden.substring(1)));
    }

    /*
     * Private constructor to block default constructions.
     */
    private Square() {
        this(0,0);
    }

    public Square next(LetterDirection d) {
        int x = horizontalFromCenter;
        int y = verticalFromCenter;
        switch (d) {
        case RightToLeft:
            x -= 1;
            break;
        case LeftToRight:
            x += 1;
            break;
        case BottomToTop:
            y -= 1;
            break;
        case TopToBottom:
            y += 1;
            break;
        }
        return new Square(x, y);
    }

    public Square previous(LetterDirection d) {
        int x = horizontalFromCenter;
        int y = verticalFromCenter;
        switch (d) {
        case RightToLeft:
            x += 1;
            break;
        case LeftToRight:
            x -= 1;
            break;
        case BottomToTop:
            y += 1;
            break;
        case TopToBottom:
            y -= 1;
            break;
        }
        return new Square(x, y);
    }

    /**
     * Override Object's toString method.
     */
    public String toString() {
        return "" + ROW_IDENTIFIERS.charAt(verticalFromCenter+7) + (horizontalFromCenter+8);
    }
    /**
     * Override Object's equals method.
     */
    public boolean equals(Object o) {
        if (o instanceof Square) {
            Square other = (Square)o;
            return equals(other);
        }
        return false;
    }

    /**
     * Basic method for determining equality.
     * NOTE: This is the normal method used in determining equality,
     * and it does not have the overhead of instanceof
     * @param l - the location the test
     * @return whether both x and y are equal
     */
    public boolean equals(Square l) {
        return this.horizontalFromCenter == l.horizontalFromCenter &&
                this.verticalFromCenter == l.verticalFromCenter;    
    }
}

