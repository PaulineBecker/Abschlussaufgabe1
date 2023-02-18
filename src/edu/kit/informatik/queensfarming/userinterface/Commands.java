package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.game.QueensFarmGame;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List of available commands with their command line interaction expressions.
 *
 * @author Thomas Weber
 * @author Moritz Gstuer
 * @author uyxib
 * @version 1.0
 */
public enum Commands {
    /**
     * Buys a vegetable if possible
     */
    BUY_VEGETABLE("^buy vegetable " + Commands.ALL_INPUT) {
        @Override public String execute(String input, QueensFarmGame game) {
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            Commands.checkOnlyOneVegetable(inputList);
            Commands.checksVegetableMatch(inputList[0]);
            return game.buyVegetable(inputList[0]);
        }
    },
    /**
     * buy a random land tile if possible
     */
    BUY_LAND("^buy land " + Commands.ALL_INPUT) {
        @Override public String execute(String input, QueensFarmGame game) {
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            final List<Integer> coordinates = new ArrayList<>();
            for (String inputNumber : inputList) {
                coordinates.add(GameInitialiser.checkNumeric(inputNumber));
            }
            if (coordinates.size() != 2) {
                throw new GameException("Error: The game board is two dimensional");
            }
            Commands.checkNumberOnGameBoard(coordinates.get(1));
            return game.buyLand(coordinates);
        }
    },
    /**
     * plants a new vegetable of an empty field if possible
     */
    PLANT("^plant " + Commands.ALL_INPUT) {
        @Override public String execute(String input, QueensFarmGame game) {
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            if (inputList.length != 3) {
                throw new GameException("Error: Exact three arguments expected.");
            }
            final List<Integer> coordinates = new ArrayList<>();
            for (int i = 0; i < TWO_DIMENSIONAL; i++) {
                coordinates.add(GameInitialiser.checkNumeric(inputList[i]));
            }
            Commands.checkNumberOnGameBoard(coordinates.get(1));
            Commands.checksVegetableMatch(inputList[2]);
            game.plant(inputList);
            return null;
        }
    },
    /**
     * harvest the whole or a part of a tile that is not the barn if possible
     */
    HARVEST("harvest -?[0-9]* [0-9]* [0-8]") {
        @Override public String execute(String input, QueensFarmGame game) {
            return null;
        }
    },
    /**
     * sell some or all vegetables from the barn if possible
     */
    SELL("^sell " + Commands.ALL_INPUT) {
        @Override public String execute(String input, QueensFarmGame game) {
            String[] vegetablesList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            if ((vegetablesList.length == 1) && vegetablesList[0].equals("all")) {
                return game.sellVegetables(vegetablesList);
            }
            for (String vegetables : vegetablesList) {
                Commands.checksVegetableMatch(vegetables);
            }
            return game.sellVegetables(vegetablesList);
        }
    },
    /**
     * shows the barn and the amount of gold
     */
    SHOW_BARN("show barn") {
        @Override public String execute(String input, QueensFarmGame game) {
            return game.showBarn();
        }
    },
    /**
     * shows the board of a player
     */
    SHOW_BOARD("show board") {
        @Override public String execute(String input, QueensFarmGame game) {
            return null;
        }
    },
    /**
     * shows the market with the current prizes
     */
    SHOW_MARKET("show market") {
        @Override public String execute(String input, QueensFarmGame game) {
            return game.showMarket();
        }
    },
    /**
     * ends directly a turn without any other new actions
     */
    END_TURN("end turn") {
        @Override public String execute(String input, QueensFarmGame game) {
            return null;
        }
    },

    /**
     * command to quit the game
     */
    QUIT("quit") {

        @Override public String execute(String input, QueensFarmGame game) {
            game.quit();
            return game.endGame();
        }
    };

    /**
     * Regex that every input is allowed after a specific input command
     */
    private static final String ALL_INPUT = ".*";

    /**
     * String constant containing an error message for the case that no command
     * could be found in this enum.
     */
    private static final String COMMAND_NOT_FOUND = Messages.ERROR.format() + "command not found!";

    private static final int TWO_DIMENSIONAL = 2;

    /**
     * The pattern of this command.
     */
    private final Pattern pattern;

    /**
     * one of the allowed uiCommands
     */
    private final String uiCommand;

    /**
     * Instantiates a new command with the given String. The given String must be a
     * compilable {@link Pattern}.
     *
     * @param pattern the pattern of this command
     */
    Commands(final String pattern) {
        this.uiCommand = pattern;
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Executes the command contained in the input if there is any, returns an error
     * message otherwise. If a command is found in the input, returns the result of
     * this input performed on the given playlist.
     *
     * @param input the line of input
     * @param game the {@link QueensFarmGame} the command is executed on
     *
     * @return the result of the command execution, may contain error messages or be
     *         null if there is no output
     */
    public static String executeCommand(final String input, final QueensFarmGame game) {
        for (final Commands command : Commands.values()) {
            final Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                return command.execute(input, game);
            }
        }
        return COMMAND_NOT_FOUND;
    }

    /**
     * Executes the given input on the given scrabble.
     *
     * @param input the line of input
     * @param game the game where the command is executed on
     *
     * @return the result of the command execution, may contain error messages or be
     *         null if there is no output
     */
    abstract String execute(String input, QueensFarmGame game);

    private static String replaceAllInput(Commands command, String input) {
        return input.replaceAll(command.uiCommand.replace(ALL_INPUT, Shell.EMPTY_STRING), Shell.EMPTY_STRING);
    }

    private static String[] getSplittedString(String input) {
        return input.split(Shell.COMMAND_SEPERATOR);
    }

    private static void checkNumberOnGameBoard(int yCoordinate) {
        if (yCoordinate < 0) {
            throw new GameException(ExceptionMessages.NEGATIVE_COORDINATE.format());
        }
    }

    private static void checksVegetableMatch(String vegetable) {
        if (!(vegetable.equals("mushroom") || vegetable.equals("carrot") || vegetable.equals("salad")
                || vegetable.equals("tomato"))) {
            throw new GameException("This vegetable is invalid.");
        }

    }

    private static void checkOnlyOneVegetable(String[] vegetables) {
        if (vegetables.length != 1) {
            throw new GameException("Error: You can only buy or plant one vegetable");
        }
    }
}
