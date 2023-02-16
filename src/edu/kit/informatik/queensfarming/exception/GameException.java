package edu.kit.informatik.queensfarming.exception;

/**
 * @author uyxib
 * @version 1.0
 */
public class GameException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Game Exception with the give message.
     *
     * @param message the message of the exception
     */
    public GameException(String message) {
        super(message);
    }
}
