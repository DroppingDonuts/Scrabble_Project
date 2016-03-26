package framework;

/**
 * The valid directions of letters on a Scrabble Board
 */

/**
 * @author Mr. Peterson
 * For Advanced Placement Computer Science, May 2016 
 */
public enum LetterDirection {
    LeftToRight, // Correct direction for a horizontal word
    TopToBottom, // Correct direction for a vertical word
    RightToLeft, // Reverse direction for a horizontal word
    BottomToTop, // Reverse direction for a vertical word
}
