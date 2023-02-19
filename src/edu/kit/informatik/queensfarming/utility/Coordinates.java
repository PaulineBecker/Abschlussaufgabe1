package edu.kit.informatik.queensfarming.utility;

/**
 * represents the coordinates of tiles on the game board of each player
 *
 * @author uyxib
 * @version 1.0
 */
public class Coordinates implements Comparable<Coordinates> {

    private int xCoordinate;
    private int yCoordinate;

    /**
     * instantiates new coordinates
     * @param xCoordinate coordinate in x-Directions (left/right)
     * @param yCoordinate coordinate in y-direction (Up/down) only positive numbers are allowed
     */

    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * get the xCoordinate of a tile
     * @return xCoordinate of a tile
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * get the yCoordinate of a tile
     * @return yCoordinate of a tile
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public int compareTo(Coordinates coordinates) {
        return getDistances(coordinates);
    }

    private int getDistances(Coordinates coordinates) {
        return (Math.abs(this.xCoordinate - coordinates.getxCoordinate())
                + Math.abs(this.yCoordinate - coordinates.getyCoordinate()));
    }
}
