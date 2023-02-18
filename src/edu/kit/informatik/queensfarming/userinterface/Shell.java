package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.game.QueensFarmGame;
import java.util.Scanner;

/**
 * represents the user Interface with the players and the game itself
 * @author Thomas Weber
 * @author Moritz Gstuer
 * @author uyxib
 * @version 1.0
 */
public class Shell {
    /**
     * The separator between lines of output.
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Error message in case of an illegal number of players.
     */
    public static final String ERROR_ILLEGAL_NUMBER_OF_PLAYERS = "number of players are not supported!";

    /**
     * The separator between a command and the parameters.
     */
    public static final String COMMAND_SEPERATOR = " ";

    /**
     * an empty string to initialize a string that is not null
     */
    public static final String EMPTY_STRING = "";
    private static final String PIXEL_ART = "                           _.-^-._    .--." + LINE_SEPARATOR
            + "                        .-'   _   '-. |__|" + LINE_SEPARATOR
            + "                       /     |_|     \\|  |" + LINE_SEPARATOR
            + "                      /               \\  |" + LINE_SEPARATOR
            + "                     /|     _____     |\\ |" + LINE_SEPARATOR
            + "                      |    |==|==|    |  |" + LINE_SEPARATOR
            + "  |---|---|---|---|---|    |--|--|    |  |" + LINE_SEPARATOR
            + "  |---|---|---|---|---|    |==|==|    |  |" + LINE_SEPARATOR
            + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + LINE_SEPARATOR
            + "^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^" + LINE_SEPARATOR
            + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + LINE_SEPARATOR;
    private boolean isReading = true;
    private boolean rightPlayerInput = true;
    private boolean namesCheck = true;
    private boolean goldCheck = true;
    private boolean goldCheckWin = true;
    private boolean quitGame = false;
    private static final int MAX_MOVES = 2;
    private GameInitialiser gameInitialiser = new GameInitialiser(-1, -1, -1, -1);




    /**
     * create and initializes the QueensFarmGame when the game is started
     * so children of the queen decided to play and started the main method.
     */
    public void startQueensFarmGame() {

        System.out.println(PIXEL_ART);
        System.out.println(Messages.NUMBER_OF_PLAYERS.format());
        instantiatesGame();
        if (quitGame) {
            return;
        }

        QueensFarmGame game = new QueensFarmGame(gameInitialiser.getGoldToWin(), gameInitialiser.getGoldToStart(),
                gameInitialiser.getNumberOfPlayers(),
                gameInitialiser.getSeed(), gameInitialiser.getPlayerNames());

        Scanner inputScanner = new Scanner(System.in);
        while (game.isActive()) {
            System.out.println(game.startTurn());
            while (game.playerIsTurning() && (game.getMovesInTurn() < MAX_MOVES) && game.isActive()) {
                String input = inputScanner.nextLine();
                try {
                    String output = Commands.executeCommand(input, game);
                    if (output != null) {
                        System.out.println(output);
                    }
                } catch (final GameException exception) {
                    System.err.println(exception.getMessage());
                }
            }
            game.endTurn();
            game.nextTurn();
        }
        System.out.println(game.endGame());
    }

    private String enterPlayerNames(String input) {
        if (input.matches("[A-Za-z]+")) {
            return input;
        } else throw new GameException("Please enter an valid name");
    }

    private void instantiatesGame() {
        int countPlayers = 1;

        Scanner inputScanner = new Scanner(System.in);

        while (namesCheck) {
            String input = inputScanner.nextLine();
            try {
                if (input.matches("quit")) {
                    quitGame = true;
                    return;
                }
                gameInitialiser.setNumberOfPlayers(gameInitialiser.enterNumberOfPlayers(input));
                namesCheck = false;
            } catch (final GameException exception) {
                System.err.println(exception.getMessage());
            }
        }

        while (countPlayers <= gameInitialiser.getNumberOfPlayers()) {
            if (rightPlayerInput) {
                System.out.println(Messages.NAME_OF_PLAYER.format(countPlayers));
            }
            String nameInput = inputScanner.nextLine();
            try {
                gameInitialiser.getPlayerNames().add(enterPlayerNames(nameInput));
                rightPlayerInput = true;
                countPlayers++;
            } catch (final GameException exception) {
                System.err.println(exception.getMessage());
                rightPlayerInput = false;
            }
        }

        System.out.println(Messages.GOLD_TO_START.format());
        while (isReading) {
            String input = inputScanner.nextLine();
            try {
                if (input.matches("quit")) {
                    quitGame = true;
                    return;
                }
                if (goldCheck) {
                    gameInitialiser.setGoldToStart(gameInitialiser.enterGoldToStart(input));
                    goldCheck = false;
                    System.out.println(Messages.GOLD_TO_WIN.format());
                } else if (goldCheckWin) {
                    gameInitialiser.setGoldToWin(gameInitialiser.enterGoldToWin(input));
                    goldCheckWin = false;
                    System.out.println(Messages.SEED_TO_SHUFFLE.format());
                } else {
                    gameInitialiser.setSeed(gameInitialiser.enterSeed(input));
                    isReading = false;
                }
            } catch (final GameException exception) {
                System.err.println(exception.getMessage());
            }
        }
    }

}