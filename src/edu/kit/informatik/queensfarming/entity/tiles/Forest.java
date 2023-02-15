package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * @author uyxib
 * @verion 1.0
 */
public class Forest extends LandTiles {

    private static final int CAPACITY = 4;
    private static final int ID = 5;
    private static final String NAME = "Forest";
    private static final String ABBREVIATION = "Fo";
    public static final int COUNTDOWN = 0;
    public static final int STARTCOORDINATES = -1;


    public Forest(Coordinates coordinates){
        super(ID, NAME, CAPACITY, COUNTDOWN, new Coordinates(STARTCOORDINATES, STARTCOORDINATES), ABBREVIATION);
    }
}
