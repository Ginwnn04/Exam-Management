package Exceptions;

/**
 * Throws by Dialog Test if there is duplicate topic in test structure list
 */
public class DuplicateTopicException extends Exception {
    public DuplicateTopicException(){
        super("This test structure contain duplicate topic");
    }
}
