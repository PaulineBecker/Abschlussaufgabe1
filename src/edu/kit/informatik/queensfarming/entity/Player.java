package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.entity.market.PriceRatio;
import edu.kit.informatik.queensfarming.entity.tiles.Barn;
import edu.kit.informatik.queensfarming.entity.tiles.Garden;
import edu.kit.informatik.queensfarming.entity.tiles.Tile;
import edu.kit.informatik.queensfarming.entity.tiles.Field;
import edu.kit.informatik.queensfarming.entity.vegetables.*;
import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.userinterface.ExceptionMessages;
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
    /**
     * the time it takes till vegetables in the barn spoil
     */

    public static final int VEGETABLE_SPOIL = 6;
    private static final String CREATE_FLUSH_RIGHT1 = "%-";
    private static final String CREATE_FLUSH_RIGHT2 = "s%";
    private static final String CREATE_FLUSH_RIGHT3 = "d";
    private static final int INDEX_OF_BARN = 0;
    private static final String NO_COUNTDOWN = "*";
    private static final String SLASH = "/";
    private static final String PIPE = "|";
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

    /**
     * calculates the size of the game board, then checks for every field if its bought or not
     * creates a String out of three lines for every tile row
     * additionally checks if the tile is the barn, the barn is printed with only the abbreviation and the countdown
     * @return the visual representation of the game board of a player
     */
    public String boardToString() {
        int maxiumHeight = getBoardHeight();
        int leftBoardCoordinate = getLeftBoardElement();
        int rightBoardCoordinate = getRightBoardElement();

        for (int y = maxiumHeight; y >= 0; y--) {
            stringBuilder.append(getLeftBoardSide(leftBoardCoordinate, y));
            for (int x = leftBoardCoordinate; x <= rightBoardCoordinate; x++) {
                if (x == 0 && y == 0) {
                    stringBuilder.append(Messages.SPACE.format().repeat(5)).append(PIPE);
                } else if (getCurrentTile(x, y) != null) {
                    stringBuilder.append(firstTileLine(getCurrentTile(x, y))).append(PIPE);
                } else {
                    stringBuilder.append(notBoardTileToString(x, y));
                }
            }
            stringBuilder.append(Shell.LINE_SEPARATOR).append(getLeftBoardSide(leftBoardCoordinate, y));
            for (int i = leftBoardCoordinate; i <= rightBoardCoordinate; i++) {
                if (i == 0 && y == 0) {
                    if (boardGame.get(i).getVegetablesList().isEmpty()) {
                        stringBuilder.append(Messages.SPACE.format()).append(boardGame.get(i).getAbbreviation())
                                .append(Messages.SPACE.format()).append(NO_COUNTDOWN)
                                .append(Messages.SPACE.format()).append(PIPE);
                    } else {
                        stringBuilder.append(Messages.SPACE.format()).append(boardGame.get(i).getAbbreviation())
                                .append(Messages.SPACE.format())
                                .append(VEGETABLE_SPOIL - boardGame.get(i).getCountdown())
                                .append(Messages.SPACE.format()).append(PIPE);
                    }
                } else if (getCurrentTile(i, y) != null) {
                    stringBuilder.append(secondTileLine(getCurrentTile(i, y))).append(PIPE);
                } else {
                stringBuilder.append(notBoardTileToString(i, y));
                }
            }
            stringBuilder.append(Shell.LINE_SEPARATOR).append(getLeftBoardSide(leftBoardCoordinate, y));
            for (int j = leftBoardCoordinate; j <= rightBoardCoordinate; j++) {
                if (j == 0 && y == 0) {
                    stringBuilder.append(Messages.SPACE.format().repeat(5)).append(PIPE);
                } else if (getCurrentTile(j, y) != null) {
                    stringBuilder.append(thirdTileLine(getCurrentTile(j, y))).append(PIPE);
                } else {
                stringBuilder.append(notBoardTileToString(j, y));
                }
            }
            stringBuilder.append(Shell.LINE_SEPARATOR);
        }
        String visualBoard = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return visualBoard;
    }


    public String barnToString() {
        List<PriceRatio> vegetablesInBarn = createVegetableList();
        Tile barn = boardGame.get(INDEX_OF_BARN);
        if (barn.getVegetablesList().size() == 0) {
            return (barn.getName().concat(Shell.LINE_SEPARATOR)
                    .concat(Messages.GOLD.format()).concat(String.valueOf(gold)));
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
        int goldLength = String.valueOf(gold).length();
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
        stringBuilder.append(String.format(formatBarn, Messages.GOLD.format(), gold));

        String barnToString = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return barnToString;
    }

    /*public void plant(String[] input) {
        int xCoordinate = Integer.parseInt(input[0]);
        int yCoordinate = Integer.parseInt(input[1]);
        int indexToPlantOn = isTileBought(xCoordinate, yCoordinate);
        String veggieToPlant = input[2];
        checksIllegalBarnMove(xCoordinate, yCoordinate);
        if (!currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().isEmpty()) {
            throw new GameException("Error: The tile you want to plant on is not empty.");
        }

        checkAllowedVeggies(veggieToPlant, indexToPlantOn);
        checkVeggiesInBarn(veggieToPlant);

        switch (veggieToPlant) {
            case (MUSHROOM) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Mushroom());
            case (CARROT) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Carrot());
            case (SALAD) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Salad());
            case (TOMATO) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Tomato());
        }

        for (int i = 0; i < currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size(); i++) {
            if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().
                    get(i).getName().equals(veggieToPlant)) {
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().remove(i);
                break;
            }
        }
    }*/

    private int countVegetables(int id) {
        int count = 0;
        for (Vegetables vegetable : boardGame.get(INDEX_OF_BARN).getVegetablesList()) {
            if (id == vegetable.getId()) {
                count++;
            }
        }
        return count;
    }

    private List<PriceRatio> createVegetableList() {
        List<PriceRatio> vegetablesInBarn = new ArrayList<>();
        vegetablesInBarn.add(new PriceRatio(Vegetable.CARROT.format()
                + Messages.COLON.format(), countVegetables(1)));
        vegetablesInBarn.add(new PriceRatio(Vegetable.MUSHROOM.format()
                + Messages.COLON.format(), countVegetables(0)));
        vegetablesInBarn.add(new PriceRatio(Vegetable.SALAT.format()
                + Messages.COLON.format(), countVegetables(3)));
        vegetablesInBarn.add(new PriceRatio(Vegetable.TOMATO.format()
                + Messages.COLON.format(), countVegetables(2)));
        vegetablesInBarn.removeIf(vegetablesCount -> vegetablesCount.getNumber() == 0);
        vegetablesInBarn.sort((o1, o2) -> Integer.compare(o1.getNumber(), o2.getNumber()));
        return vegetablesInBarn;
    }

    private int getBoardHeight() {
        int maximumHeight = 0;
        for (int i = 0; i < boardGame.size(); i++) {
            if (boardGame.get(i).getCoordinates().getyCoordinate() > maximumHeight) {
                maximumHeight = boardGame.get(i).getCoordinates().getyCoordinate();
            }
        }
        return maximumHeight;
    }

    private int getLeftBoardElement() {
        int leftestBoardCoordinate = 0;
        for (int i = 0; i < boardGame.size(); i++) {
            if (boardGame.get(i).getCoordinates().getxCoordinate() < leftestBoardCoordinate) {
                leftestBoardCoordinate = boardGame.get(i).getCoordinates().getxCoordinate();
            }
        }
        return leftestBoardCoordinate;
    }

    private int getRightBoardElement() {
        int rightestBoardCoordinate = 0;
        for (int i = 0; i < boardGame.size(); i++) {
            if (boardGame.get(i).getCoordinates().getxCoordinate() > rightestBoardCoordinate) {
                rightestBoardCoordinate = boardGame.get(i).getCoordinates().getxCoordinate();
            }
        }
        return rightestBoardCoordinate;
    }

    private String firstTileLine (Tile tile) {
        int lengthOfAbbreviation = tile.getAbbreviation().length();
        if (lengthOfAbbreviation == 1) {
            stringBuilder.append(Shell.COMMAND_SEPERATOR).append(tile.getAbbreviation())
                    .append(Shell.COMMAND_SEPERATOR).append(checkActiveCountdown(tile)).append(Shell.COMMAND_SEPERATOR);
        } else {
            if (lengthOfAbbreviation == 3) {
                stringBuilder.append(tile.getAbbreviation());
            } else if (lengthOfAbbreviation == 2) {
                stringBuilder.append(Shell.COMMAND_SEPERATOR).append(tile.getAbbreviation());
            }
            stringBuilder.append(Shell.COMMAND_SEPERATOR).append(checkActiveCountdown(tile));
        }
        String firstBoardLine = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return firstBoardLine;
    }

    /**
     * creates the second line of the representation of a tile, the abbreviation of the vegetable that is grown
     * of a tile,
     * @param tile the given tile of a game board
     * @return the representation string of the second Line of a tile if theres no vegetable on a string with spaces
     */
    private String secondTileLine(Tile tile) {
        if (tile.getVegetablesList().isEmpty()) {
            stringBuilder.append(Messages.SPACE.format().repeat(5));
        }
        else {
            stringBuilder.append(Messages.SPACE.format().repeat(2))
                    .append(tile.getVegetablesList().get(0).getAbbreviation())
                    .append(Messages.SPACE.format().repeat(2));
        }
        String secondTileLine = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return secondTileLine;
    }

    /**
     * creates the third line of the representation of a tile how many vegetables are on the tile and the capacity
     * @param tile the current tile
     * @return the representation string of the third Line of a tile
     */
    private String thirdTileLine(Tile tile) {
        stringBuilder.append(Messages.SPACE.format()).append(tile.getVegetablesList().size())
                .append(SLASH).append(tile.getCapacity()).append(Messages.SPACE.format());
        String thirdTileLine = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return thirdTileLine;
    }

    /**
     * get the current tile from the list of board tiles to represent the tile on the right position of the game board
     * @param xCoordinate x-Coordinate of the tile the method is looking for
     * @param yCoordinate y-Coordinate of the tile the mehtod is looking for
     * @return the tile the method is looking for with the given coordinates, if tile is not found return null
     */
    private Tile getCurrentTile(int xCoordinate, int yCoordinate) {
        for (Tile tile : boardGame) {
            if (tile.getCoordinates().getxCoordinate() == xCoordinate
                    && tile.getCoordinates().getyCoordinate() == yCoordinate) {
                return tile;
            }
        }
        return null;
    }

    /**
     * checks if the left side of the board starts with a pipe or not
     * @param xCoordinate leftest xCoordinate of the game board
     * @param yCoordinate current yCoordinate of the game board
     * @return if left field is bought: Pipe, otherwise empty string
     */
    private String getLeftBoardSide(int xCoordinate, int yCoordinate) {
        if (getCurrentTile(xCoordinate, yCoordinate) == null) {
            return Messages.SPACE.format();
        } else {
            return PIPE;
        }
    }

    /**
     * checks if there's a bought tile next to a not bought tile or not
     * create the String to show the not bought tile on the board
     * @param xCoordinate x Coordinate of the game board
     * @param yCoordinate y Coordinate of the game board
     * @return the string if a tile is not bought on the field
     */
    private String notBoardTileToString(int xCoordinate, int yCoordinate) {
        if (getCurrentTile(xCoordinate + 1, yCoordinate) != null) {
            stringBuilder.append(Messages.SPACE.format().repeat(5)).append(PIPE);
        } else {
            stringBuilder.append(Messages.SPACE.format().repeat(6));
        }
        String lineString = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return lineString;
    }

    private String checkActiveCountdown(Tile tile) {
        if (tile.getCountdown() == 0) {
            return NO_COUNTDOWN;
        } else {
            return String.valueOf(tile.getVegetablesList().get(0).getTimeToGrow() - tile.getCountdown());
        }
    }
}
