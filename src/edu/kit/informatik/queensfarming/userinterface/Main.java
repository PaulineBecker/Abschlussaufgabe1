package edu.kit.informatik.queensfarming.userinterface;

/**
 * Main class for the first task of the fifth assignment. Contains the entry point.
 *
 * @author uyxib
 * @version 1.0
 */
public final class Main {

    /**
     * Starts the Queens farm game.
     * @param args commando line arguments that are not allowed
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            System.err.println(ExceptionMessages.ERROR_ILLEGAL_COMMANDO_LINE_ARGUMENTS.format());
            return;
        }

        Shell shell = new Shell();
        shell.startQueensFarmGame();
    }
}