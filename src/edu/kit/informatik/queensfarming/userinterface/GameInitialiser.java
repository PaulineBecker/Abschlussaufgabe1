package edu.kit.informatik.queensfarming.userinterface;

import edu.kit.informatik.queensfarming.exception.GameException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uyxib
 * @version 1.0
 */
public class GameInitialiser {

    private int numberOfPlayers;
    private int goldToWin;
    private int goldToStart;
    private long seed;
    private List<String> playerNames;

    public GameInitialiser(int numberOfPlayers, int goldToWin, int goldToStart, int seed) {
        this.numberOfPlayers = numberOfPlayers;
        this.goldToWin = goldToWin;
        this.goldToStart = goldToStart;
        this.seed = seed;
        this.playerNames = new ArrayList<String>();
    }

    public int enterNumberOfPlayers(String input) {

        if (input.matches("[1-9][0-9]*")) {
            try {
                return Integer.parseInt(input);
            } catch (final NumberFormatException error) {
                throw new GameException("Error: The number of players is too high. Please enter a lower number of players");
            }
        } else throw new GameException("Error: Please enter the right number of players");
    }

    public int enterGoldToWin(String input) {
        if (input.matches("[1-9][0-9]*")) {
            try {
                return Integer.parseInt(input);
            } catch (final NumberFormatException error) {
                throw new GameException("Error: The amount of gold is too high. Please enter a lower amount of gold");
            }
        } else throw new GameException("Error: Please enter the right number of gold that needs a player to win");
    }

    public int enterGoldToStart(String input) {
        if (input.matches("[0-9]+")) {
            try {
                return Integer.parseInt(input);
            } catch (final NumberFormatException error) {
                throw new GameException("Error: The amount of gold is too high. Please enter a lower amount of gold");
            }
        } else throw new GameException("Error: Please enter the right number of gold that has player to start");
    }



    public long enterSeed(String input) {
        if (input.matches("[0-9]+")) {
            try {
                return Long.parseLong(input);
            } catch (final NumberFormatException error) {
                throw new GameException("Error: The number of seed is too high. Please enter a lower number of seed");
            }
        } else throw new GameException("Error: Please enter the right number of seed to shuffle the cards");
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getGoldToWin() {
        return goldToWin;
    }

    public void setGoldToWin(int goldToWin) {
        this.goldToWin = goldToWin;
    }

    public int getGoldToStart() {
        return goldToStart;
    }

    public void setGoldToStart(int goldToStart) {
        this.goldToStart = goldToStart;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

}
