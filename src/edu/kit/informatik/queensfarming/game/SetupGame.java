package edu.kit.informatik.queensfarming.game;

/**
 * @author uyxib
 * @verion 1.0
 */
public class SetupGame {
    private final int numberOfPlayers;
    private final int goldToWin;
    private final int goldToStart;
    private final int seed;

    /**
     * Instantiates the setup of the game (player, gold to win, gold to start, seed to shuffle)
     *
     * @param numberOfPlayers number of Players that play the game
     * @param goldToWin amount of gold that needs a Player to win the game
     * @param goldToStart amnout of gold that each Player has from at the beinning of the game
     * @param seed number that is used to shuffle the Harvest Tiles
     */
    public SetupGame(int numberOfPlayers, int goldToWin, int goldToStart, int seed) {
        this.numberOfPlayers = numberOfPlayers;
        this.goldToWin = goldToWin;
        this.goldToStart = goldToStart;
        this.seed = seed;
    }


}
