package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.entity.tiles.Tiles;
import edu.kit.informatik.queensfarming.game.QueensFarmBoard;

import java.util.List;

/**
 * Represents a player that plays the QueensFarm Game
 *
 * @author uyxib
 * @verion 1.0
 */
public class Player {

    private int gold;
    private String name;
    private List<Tiles> boardgame;

    /**
     *
     * @param gold the amount of gold that a player have to buy land or vegetables
     * @param name the name of the player
     */

    public Player(int gold, String name) {
        this.gold = gold;
        this.name = name;
    }

    private void addStartTIles() {

    }


    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
