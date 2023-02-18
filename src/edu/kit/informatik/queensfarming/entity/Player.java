package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.entity.market.PriceRatio;
import edu.kit.informatik.queensfarming.entity.tiles.Barn;
import edu.kit.informatik.queensfarming.entity.tiles.Garden;
import edu.kit.informatik.queensfarming.entity.tiles.Tile;
import edu.kit.informatik.queensfarming.entity.tiles.Field;
import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Mushroom;
import edu.kit.informatik.queensfarming.entity.vegetables.Salad;
import edu.kit.informatik.queensfarming.entity.vegetables.Tomato;
import edu.kit.informatik.queensfarming.userinterface.Messages;
import edu.kit.informatik.queensfarming.userinterface.Shell;
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
    private StringBuilder stringBuilder = new StringBuilder();

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


    /*private String barnToString(List<PriceRatio> vegetablesInBarn) {
        Tile barn = boardGame.get(INDEX_OF_BARN);
        if (barn.getVegetablesList().size() == 0) {
            return (barn.getName().concat(Shell.LINE_SEPARATOR)
                    .concat(Messages.GOLD.format()).concat(String.valueOf(currentPlayer.getGold())));
        } else {
            if (VEGETABLE_SPOIL - barn.getCountdown() == 1) {
                stringBuilder.append(Messages.BARN_SPOILS_TOMORROW.format()).append(Shell.LINE_SEPARATOR);
            } else if (VEGETABLE_SPOIL - barn.getCountdown() > 1) {
                stringBuilder.append((Messages.BARN_SPOILS.format(VEGETABLE_SPOIL - barn.getCountdown())))
                        .append(Shell.LINE_SEPARATOR);
            }
        }

        int sum = barn.getVegetablesList().size();
        int sumLength = String.valueOf(sum).length();
        int goldLength = String.valueOf(currentPlayer.getGold()).length();
        int lengthOfStringMax = 0;
        int lengthOfIntMax = 0;

        for (PriceRatio listValue : vegetablesInBarn) {
            if (listValue.getVegetable().length() > lengthOfStringMax) {
                lengthOfStringMax = listValue.getVegetable().length();
            }
            int lengthOfPrize = String.valueOf(listValue.getNumber()).length();
            if (lengthOfPrize > lengthOfIntMax) {
                lengthOfIntMax = lengthOfPrize;
            }
        }

        if (sumLength > lengthOfIntMax) { lengthOfIntMax = sumLength; }
        if (goldLength > lengthOfIntMax) { lengthOfIntMax = goldLength; }

        String formatBarn = CREATE_FLUSH_RIGHT1 + lengthOfStringMax + CREATE_FLUSH_RIGHT2 + lengthOfIntMax
                + CREATE_FLUSH_RIGHT3 + Shell.LINE_SEPARATOR;
        for (PriceRatio listValue : vegetablesInBarn) {
            stringBuilder.append(String.format(formatBarn, listValue.getVegetable(), listValue.getNumber()));
        }

        stringBuilder.append(Messages.HYPHEN.format().repeat(lengthOfIntMax + lengthOfStringMax))
                .append(Shell.LINE_SEPARATOR);
        stringBuilder.append(String.format(formatBarn, Messages.SUM.format(), sum)).append(Shell.LINE_SEPARATOR);
        stringBuilder.append(String.format(formatBarn, Messages.GOLD.format(), currentPlayer.getGold()));


        String barnToString = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return barnToString;
    }*/
}
