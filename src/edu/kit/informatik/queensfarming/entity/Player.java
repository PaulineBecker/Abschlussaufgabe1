package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.entity.tiles.Barn;
import edu.kit.informatik.queensfarming.entity.tiles.Garden;
import edu.kit.informatik.queensfarming.entity.tiles.Tile;
import edu.kit.informatik.queensfarming.entity.tiles.Field;
import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Mushroom;
import edu.kit.informatik.queensfarming.entity.vegetables.Salad;
import edu.kit.informatik.queensfarming.entity.vegetables.Tomato;
import edu.kit.informatik.queensfarming.utility.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player that plays the QueensFarm Game
 *
 * @author uyxib
 * @version 1.0
 */
public class Player {
    /**
     * the number when no vegetables have grown since the last turn
     */

    public static final int NO_GROWN_VEGETABLES = 0;
    private static final int INDEX_OF_BARN = 0;
    private int gold;
    private final String name;
    private List<Tile> boardGame = new ArrayList<>();
    private int grownVegetables = 0;

    /**
     * Instantiates a new player who is playing the Queens Farm Game
     * @param gold the amount of gold that a player have to buy land or vegetables
     * @param name the name of the player
     */

    public Player(int gold, String name) {
        this.gold = gold;
        this.name = name;
        addStartTiles();
    }

    private void addStartTiles() {
        boardGame.add(new Barn());
        boardGame.add(new Garden(new Coordinates(-1, 0)));
        boardGame.add(new Garden(new Coordinates(1, 0)));
        boardGame.add(new Field(new Coordinates(0, 1)));
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Mushroom());
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Carrot());
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Tomato());
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Salad());
    }

    /**
     * the current amount of gold
     * @return current amount of gold
     */

    public int getGold() {
        return gold;
    }

    /**
     * sets the amount of gold for a player
     * @param gold amount of gold a player has
     */

    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * gets the name of a player
     * @return the name of a player
     */

    public String getName() {
        return name;
    }

    /**
     * get the boardGarme of a player with all its tiles
     * @return borad Game of a player
     */


    public List<Tile> getBoardGame() {
        return boardGame;
    }

    /**
     * sets the boards game with all its tiles
     * @param boardGame boardgame list with tiles
     */

    public void setBoardGame(List<Tile> boardGame) {
        this.boardGame = boardGame;
    }

    public int getGrownVegetables() {
        return grownVegetables;
    }

    public void setGrownVegetables(int grownVegetables) {
        this.grownVegetables = grownVegetables;
    }
}
