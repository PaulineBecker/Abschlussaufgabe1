package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.game.QueensFarmGame;

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
    BUY_VEGETABLE("buy vegetable (tomato|salad|mushroom|carrot)") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },
    BUY_LAND("buy land [0-9]* [0-9]*") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },
    PLANT("plant") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },
    HARVEST("harvest -?[0-9]* [0-9]* [0-8]") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },
    SELL("^sell(?: (?:salad|tomato|mushroom|carrot))+$|sell all") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },
    SHOW_BARN("show barn") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return game.showBarn();
        }
    },
    SHOW_BOARD("show board") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },
    SHOW_MARKET("show market") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return game.showMarket();
        }
    },
    END_TURN("end turn") {
        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    },

    /**
     * command to quit the game
     */
    QUIT("quit") {

        @Override public String execute(Matcher input, QueensFarmGame game) {
            return null;
        }
    };

    /**
     * String constant containing an error message for the case that no command
     * could be found in this enum.
     */
    public static final String COMMAND_NOT_FOUND = Messages.ERROR.format() + "command not found!";

    /**
     * The pattern of this command.
     */
    private final Pattern pattern;

    /**
     * Instantiates a new command with the given String. The given String must be a
     * compilable {@link Pattern}.
     *
     * @param pattern the pattern of this command
     */
    Commands(final String pattern) {
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
                return command.execute(matcher, game);
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
    abstract String execute(Matcher input, QueensFarmGame game);
}
