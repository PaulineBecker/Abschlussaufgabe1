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
    private static final int INDEX_OF_BARN = 0;
    private int gold;
    private final String name;
    private List<Tile> boardGame = new ArrayList<>();

    /**
     *
     * @param gold the amount of gold that a player have to buy land or vegetables
     * @param name the name of the player
     */

    public Player(int gold, String name) {
        this.gold = gold;
        this.name = name;
        addStartTiles();
    }

    private List<Tile> addStartTiles() {
        boardGame.add(new Barn());
        boardGame.add(new Garden(new Coordinates(-1, 0)));
        boardGame.add(new Garden(new Coordinates(1,0)));
        boardGame.add(new Field (new Coordinates(0,1)));
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Mushroom());
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Carrot());
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Tomato());
        boardGame.get(INDEX_OF_BARN).getVegetablesList().add(new Salad());

        return boardGame;
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


    public List<Tile> getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(List<Tile> boardGame) {
        this.boardGame = boardGame;
    }
}
