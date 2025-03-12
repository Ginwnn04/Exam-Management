package Exceptions;

/**
 * Throws in Dialog Test if there is no questions provide in Test Structure
 */
public class EmptyQuestionsException extends Exception {
    public EmptyQuestionsException() {
        super("This test structure have no question");
    }
}
