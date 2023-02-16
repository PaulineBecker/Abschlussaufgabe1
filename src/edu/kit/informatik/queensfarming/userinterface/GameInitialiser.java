package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.exception.GameException;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the object to initialise the game
 * here are all settings saved that are necessary to know when children want to start playing
 *
 * @author uyxib
 * @version 1.0
 */
public class GameInitialiser {

    private int numberOfPlayers;
    private int goldToWin;
    private int goldToStart;
    private long seed;
    private List<String> playerNames;

    /**
     * instantiates all the variables to start the game: number of player, amount of gold to win, gold to start with,
     * and the seed to shuffle the cards
     *
     * @param numberOfPlayers the number of players in a game session
     * @param goldToWin the gold a player needs to win the game
     * @param goldToStart the amount of gold every player has at the beginning of the game
     * @param seed the seed to shuffle the cards
     */

    public GameInitialiser(int numberOfPlayers, int goldToWin, int goldToStart, int seed) {
        this.numberOfPlayers = numberOfPlayers;
        this.goldToWin = goldToWin;
        this.goldToStart = goldToStart;
        this.seed = seed;
        this.playerNames = new ArrayList<>();
    }

    /**
     * checks if the entered number of players matches, if not throws Game Exception
     * @param input the input one of the players make to initialize the game
     * @return integer value with the number of players in the game session
     */
    public int enterNumberOfPlayers(String input) {

        if (input.matches("[1-9][0-9]*")) {
            try {
                return Integer.parseInt(input);
            } catch (final NumberFormatException error) {
                throw new GameException(ExceptionMessages.TOO_HIGH_THAN_INT.format());
            }
        } else throw new GameException(ExceptionMessages.NUMBER_OF_PLAYERS_INVALID.format());
    }

    /**
     * checks if the entered amount of gold is allowed, if not throws Game Exception
     * @param input the input one of the players make to initialize the game
     * @return integer value with the amount of gold to win the game
     */
    public int enterGoldToWin(String input) {
        if (input.matches("[1-9][0-9]*")) {
            try {
                return Integer.parseInt(input);
            } catch (final NumberFormatException error) {
                throw new GameException(ExceptionMessages.GOLD_TOO_HIGH.format());
            }
        } else throw new GameException(ExceptionMessages.GOLDWIN_INVALID.format());
    }

    /**
     * checks if the entered amount of gold to start the game matches, if not throws Game Exception
     * @param input the input one of the players make to initialize the game
     * @return integer value with the amount of gold to start the game with
     */
    public int enterGoldToStart(String input) {
        if (input.matches("[0-9]+")) {
            try {
                return Integer.parseInt(input);
            } catch (final NumberFormatException error) {
                throw new GameException(ExceptionMessages.GOLD_TOO_HIGH.format());
            }
        } else throw new GameException(ExceptionMessages.GOLDSTART_INVALID.format());
    }

    /**
     * checks if the entered seed matches and is in the range of a long datatyp, if not throws Game Exception
     * @param input the input one of the players make to initialize the game
     * @return integer value with the seed to shuffle the cards
     */
    public long enterSeed(String input) {
        if (input.matches("[0-9]+")) {
            try {
                return Long.parseLong(input);
            } catch (final NumberFormatException error) {
                throw new GameException(ExceptionMessages.SEED_TO_HIGH.format());
            }
        } else throw new GameException(ExceptionMessages.SEED_INVALID.format());
    }

    /**
     * get number of players in a game
     * @return number of players in a game
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * sets the number of players in a game
     * @param numberOfPlayers number of players in a game
     */

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * get the gold a player need to win the game
     * @return gold a player need to win the game
     */

    public int getGoldToWin() {
        return goldToWin;
    }

    /**
     * sets the amount of gold to win a game
     * @param goldToWin
     */

    public void setGoldToWin(int goldToWin) {
        this.goldToWin = goldToWin;
    }

    /**
     * gets the amount of gold players start a game with
     * @return amount fo gold to start a game with
     */

    public int getGoldToStart() {
        return goldToStart;
    }

    /**
     * sets the amount of gold to start a game with
     * @param goldToStart gold to start a game with
     */

    public void setGoldToStart(int goldToStart) {
        this.goldToStart = goldToStart;
    }

    /**
     * get the seed to shuffle the tiles
     * @return seed to shuffle the tiles
     */
    public long getSeed() {
        return seed;
    }

    /**
     * gets the seed the shuffle the tiles
     * @param seed seed to shuffle the tiles
     */

    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * gets the playerNames of the current game
     * @return playerNames of the current game
     */
    public List<String> getPlayerNames() {
        return playerNames;
    }

}
