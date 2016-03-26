package framework;

/**
 * Encapsulation of a Scrabble Board.
 * The scrabble board is represented by a two dimensional array
 * 
 * There is only one scrabble board, the constant GAMEBOARD.
 */

/**
 * @author Mr. Peterson
 * For Advanced Placement Computer Science, May 2016 
 */

public class ScrabbleBoard {

    public static final ScrabbleBoard GAMEBOARD = new ScrabbleBoard();

    // The board encodes double and triple letter scores as 1*2 and 1*3
    // and encodes double and triple word scores as 2*2 and 2*3
    private final int[][] board = {
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

    // The constructor is only used to create the constant GAMEBOARD.
    private ScrabbleBoard() {
    }

    /**
     * Helper function to return x index value of array
     * @param s - Square to convert
     * @return - correct index between 0 - 14
     */
    private int getX(Square s) {
        return s.horizontalFromCenter + 7;
    }

    /**
     * Helper function to return y index value of array
     * @param s - Square to convert
     * @return - correct index between 0 - 14
     */
    private int getY(Square s) {
        return s.verticalFromCenter + 7;
    }

    /**
     * Return the letter multiplier for a square
     * @param s - the specified square
     * @return 1, 2, or 3
     */
    public int getLetterMultiplier(Square s) {
        int value = board[getY(s)][getX(s)];
        if (value < 4) {
            return value;
        }
        return 1;
    }

    /**
     * Return the letter multiplier for a square specified by its canonical name
     * @param s - A1 to O15
     * @return 1, 2, 3
     */
    public int getLetterMultiplier(String s) {
        return getLetterMultiplier(new Square(s));
    }

    /**
     * Return the word multiplier for a square
     * @param s - the specified square
     * @return 1, 2, or 3
     */
    public int getWordMultiplier(Square s) {
        int value = board[getY(s)][getX(s)];
        if (value > 3) {
            return value / 2;
        }
        return 1;
    }

    /**
     * Return the word multiplier for a square specified by its canonical name
     * @param s - A1 to O15
     * @return 1, 2, or 3
     */
    public int getWordMultiplier(String s) {
        return getWordMultiplier(new Square(s));
    }

    /**
     * Return the word multiplier for a row or column specified by its start and end squares
     * @param start - the initial square of a row or column of squares
     * @param end - the final square of a row or column of squares
     * @return 1, 2, 3, 4, 6, 8, 9, 12, 18, 27
     */
    public int getWordMultiplier(Square start, Square end) {
        int value = 1;
        int startX = getX(start);
        int startY = getY(start);
        int endX = getX(end);
        int endY = getY(end);
        if (startX - endX == 0) {
            if (endY < startY) {
                endY = startY;
                startY = getY(end);
            }
            for (int y = startY; y <= endY; y++) {
                value *= board[y][startX] < 4 ? 1 : board[y][getX(start)] / 2;
            }
        } else if (startY - endY == 0) {
            if (endX < startX) {
                endX = startX;
                startX = getX(end);
            }
            for (int x = startX; x <= endX; x++) {
                value *= board[startY][x] < 4 ? 1 : board[startY][x] / 2;
            }
        } else {
            throw new IndexOutOfBoundsException("" + start + " and " + end + " are not aligned");
        }
        return value;
    }

    /**
     * Return the word multiplier for a row or column specified by its start and end names
     * @param start - the initial square of a row or column of squares (A1 - O15)
     * @param end - the final square of a row or column of squares (A1 - O15)
     * @return 1, 2, 3, 4, 6, 8, 9, 12, 18, 27
     */
    public int getWordMultiplier(String start, String end) {
        return getWordMultiplier(new Square(start), new Square(end));
    }

    /**
     * Basic tests for classes Square and ScrabbleBoard
     * @param args - unused
     */
    static public void main(String[] args) {
        char c = 0x30;
        int i = 'A';
        Square a = new Square('a',1);
        Square b = new Square(7, -7);
        for (i = a.horizontalFromCenter; i <= b.horizontalFromCenter; i++) {
            Square t = new Square(i, a.verticalFromCenter);
            System.out.println("" + t + ":" +
                    GAMEBOARD.getLetterMultiplier(t) + ":" +
                    GAMEBOARD.getWordMultiplier(t));
        }
        System.out.println(a + " to " + b + " = " + GAMEBOARD.getWordMultiplier(a, b));
        System.out.println("O8 to A8 = " + GAMEBOARD.getWordMultiplier("O8", "A8"));
        for (LetterDirection d : LetterDirection.values()) {
            Square s = Square.CENTER.next(d);
            Square t = Square.CENTER.previous(d);
            System.out.print("(0,0)" + d + ":" + s + ":" + t);
            System.out.println("(" + new Square(s.toString()) + ":" + new Square(t.toString()) + ")");
        }
        System.out.println();

    }
}
