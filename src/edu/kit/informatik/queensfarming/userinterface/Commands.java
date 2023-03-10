package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.game.QueensFarm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List of available commands with their command line interaction expressions.
 * Copied from Thomas Weber and Moritz Gstuer and then modified from
 * @author uyxib
 * @version 1.0
 */
public enum Commands {
    /**
     * Buys a vegetable if possible
     */
    BUY_VEGETABLE("^buy vegetable " + Commands.ALL_INPUT) {
        @Override
        public String execute(String input, QueensFarm game) {
            Commands.checkSpaceAtEnd(input);
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
        @Override
        public String execute(String input, QueensFarm game) {
            Commands.checkSpaceAtEnd(input);
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            final List<Integer> coordinates = new ArrayList<>();
            for (String inputNumber : inputList) {
                coordinates.add(GameInitialiser.checkNumeric(inputNumber));
            }
            if (coordinates.size() != 2) {
                throw new GameException(ExceptionMessages.BOARD_2D.format());
            }
            Commands.checkNumberOnGameBoard(coordinates.get(1));
            return game.buyLand(coordinates);
        }
    },
    /**
     * plants a new vegetable of an empty field if possible
     */
    PLANT("^plant " + Commands.ALL_INPUT) {
        @Override
        public String execute(String input, QueensFarm game) {
            Commands.checkSpaceAtEnd(input);
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            Commands.checkArgumentsLength(inputList);
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
    HARVEST("^harvest " + Commands.ALL_INPUT) {
        @Override
        public String execute(String input, QueensFarm game) {
            Commands.checkSpaceAtEnd(input);
            String[] inputList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            Commands.checkArgumentsLength(inputList);
            final int[] harvestArguments = new int[3];
            for (int i = 0; i < inputList.length; i++) {
                harvestArguments[i] = GameInitialiser.checkNumeric(inputList[i]);
            }
            Commands.checkNumberOnGameBoard(harvestArguments[1]);
            Commands.checkNumberOnGameBoard(harvestArguments[2]);
            return game.harvest(harvestArguments);
        }
    },
    /**
     * sell some or all vegetables from the barn if possible
     */
    SELL("^sell " + Commands.ALL_INPUT) {
        @Override
        public String execute(String input, QueensFarm game) {
            Commands.checkSpaceAtEnd(input);
            String[] vegetablesList = Commands.getSplittedString(Commands.replaceAllInput(this, input));
            if (String.valueOf(input.charAt(input.length() - 1)).equals(Shell.COMMAND_SEPERATOR)) {
                throw new GameException(COMMAND_NOT_FOUND);
            }
            if ((vegetablesList.length == 1) && vegetablesList[0].equals(ALL_VEGETABLES)) {
                return game.sellVegetables(vegetablesList);
            }
            for (String vegetables : vegetablesList) {
                Commands.checksVegetableMatch(vegetables);
            }
            return game.sellVegetables(vegetablesList);
        }
    },
    /**
     * sell zero vegetables
     */
    SELL_NOTHING("sell") {
        @Override
        public String execute(String input, QueensFarm game) {
            String[] vegetablesList = new String[0];
            return game.sellVegetables(vegetablesList);
        }
    },
    /**
     * shows the barn and the amount of gold
     */
    SHOW_BARN("show barn") {
        @Override
        public String execute(String input, QueensFarm game) {
            return game.showBarn();
        }
    },
    /**
     * shows the board of a player
     */
    SHOW_BOARD("show board") {
        @Override
        public String execute(String input, QueensFarm game) {
            return game.showBoard();
        }
    },
    /**
     * shows the market with the current prizes
     */
    SHOW_MARKET("show market") {
        @Override public String execute(String input, QueensFarm game) {
            return game.showMarket();
        }
    },
    /**
     * ends directly a turn without any other new actions
     */
    END_TURN("end turn") {
        @Override
        public String execute(String input, QueensFarm game) {
            game.endTurn();
            return null;
        }
    },

    /**
     * command to quit the game
     */
    QUIT("quit") {

        @Override
        public String execute(String input, QueensFarm game) {
            game.quit();
            return null;
        }
    };

    /**
     * the command if all vegetables will be sold from the barn
     */
    public static final String ALL_VEGETABLES = "all";

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
     * @param game the {@link QueensFarm} the command is executed on
     *
     * @return the result of the command execution, may contain error messages or be
     *         null if there is no output
     */
    public static String executeCommand(final String input, final QueensFarm game) {
        for (final Commands command : Commands.values()) {
            final Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                return command.execute(input, game);
            }
        }
        return COMMAND_NOT_FOUND.concat(Shell.LINE_SEPARATOR);
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
    abstract String execute(String input, QueensFarm game);

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
            throw new GameException(ExceptionMessages.INVALID_VEGETABLE.format());
        }

    }

    private static void checkOnlyOneVegetable(String[] vegetables) {
        if (vegetables.length != 1) {
            throw new GameException(ExceptionMessages.ONE_VEGETABLE_ALLOWED.format());
        }
    }

    private static void checkArgumentsLength(String[] inputList) {
        if (inputList.length != 3) {
            throw new GameException(ExceptionMessages.THREE_EXPECTED_ARGUMENTS.format());
        }
    }

    private static void checkSpaceAtEnd(String input) {
        if (input.endsWith(Shell.COMMAND_SEPERATOR)) {
            throw new GameException(ExceptionMessages.ILLEGAL_SPACE.format());
        }
    }
}
