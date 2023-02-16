package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.game.QueensFarmGame;

import java.util.Scanner;

/**
 *
 * Javadoc inspired by Thomas Weber and Moritz Gstuer
 *
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
     * an empty string to initialize an string that is not null
     */
    public static final String EMPTY_STRING = "";

    private boolean isReading = true;
    private boolean rightPlayerInput = true;
    private boolean namesCheck = true;
    private boolean quitName = true;
    private boolean goldCheck = true;
    private boolean goldCheckWin = true;
    private GameInitialiser gameInitialiser = new GameInitialiser(-1, -1, -1, -1);


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

    public void startQueensFarmGame() {

        System.out.println(PIXEL_ART);
        System.out.println(Messages.NUMBER_OF_PLAYERS.format());
        instantiatesGame();

        QueensFarmGame game = new QueensFarmGame(gameInitialiser.getGoldToWin(), gameInitialiser.getGoldToStart(),
                        gameInitialiser.getNumberOfPlayers(), gameInitialiser.getSeed(), gameInitialiser.getPlayerNames());

        System.out.println(game.showMarket());
        System.out.println(game.showBarn());
        //System.out.println(game.buyVegetables("buy vegetable tomato"));
        System.out.println(game.showBarn());
        //System.out.println(game.sellVegetables("sell all"));
        System.out.println(game.showBarn());
        System.out.println(game.endGame());

    }

    private String enterPlayerNames (String input) {
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
                if (input.matches("quit") && quitName) {
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
                if (input.matches("quit") && quitName) {
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
        inputScanner.close();
    }

}