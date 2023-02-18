package edu.kit.informatik.queensfarming.userinterface;

/**
 * the different states the game can have when its running
 *
 * @author uyxib
 * @verion 1.0
 */
public enum GameState {

    /**
     * It's the turn of the next player
     */
    START_TURN,
    /**
     * ends the turn of a player
     */
    END_TURN,
    /**
     * The game starts a new round.
     * The countdown for vegetable and the barn increases.
     * Vegetables can spoil in the barn.
     */
    NEXT_ROUND,
}
