package edu.kit.informatik.queensfarming.userinterface;

/**
 * @author uyxib
 * @version 1.0
 */
public enum ExceptionMessages {
    /**
     * Error message in case of an illegal number of players.
     */
    ERROR_ILLEGAL_COMMANDO_LINE_ARGUMENTS("Error: Commando Line Arguments are not allowed"),
    /**
     * Error message in case of the number of players is a too high to parse to an int.
     */
    TOO_HIGH_THAN_INT("Error: The number of players is too high. Please enter a lower number of players"),

    /**
     * Error message if the number of players is not valid.
     */
    NUMBER_OF_PLAYERS_INVALID("Error: Please enter the right number of players"),
    /**
     * Error message in case of the amount of gold is a too high to parse to an int.
     */
    GOLD_TOO_HIGH("Error: The amount of gold is too high. Please enter a lower amount of gold"),
    /**
     * Error message in case of the amount of gold is not a valid number.
     */
    GOLDWIN_INVALID("Error: Please enter the right number of gold that needs a player to win"),
    /**
     * Error message in case of the amount of gold is not a valid number.
     */
    GOLDSTART_INVALID("Error: Please enter the right number of gold that a player can start with"),
    /**
     * Error message if the seed to shuffle the tiles is not valid.
     */
    SEED_INVALID("Error: Please enter the right number of seed to shuffle the cards"),
    /**
     * Error message if the seed to shuffle the tiles is not in the range of a long.
     */
    SEED_TO_HIGH("Error: The number of seed is too high. Please enter a lower number of seed"),
    /**
     * Error message if the vegetable on the specific tile is not allowed to plant.
     */
    UNALLOWED_VEGETABLE("Error: It is not allowed to plant a %s on this tile."),
    /**
     * Error message if the vegetable on the specific tile is not allowed to plant.
     */
    VEGETABLE_NOT_IN_BARN("Error: The %s you want to plant is not in your barn."),
    /**
     * Error message if the player try to buy or harvest on a tile that doesn't exist on his / her game board.
     */
    TILE_NOT_ON_BOARD("Error: You can not plant or harvest on a area in your board game that you haven't bought"),
    /**
     * Error message if the player tries to plant or harvest on a barn tile.
     */
    ILLEGAL_PLANT_ON_BARN("Error: Your barn is the tile for only storing your vegetables."),
    /**
     * Error message if the player tries to harvest more vegetables than they are planted.
     */
    TOO_MUCH_HARVESTING("Error: You can't harvest more vegetables than grown on the tile.");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    /**
     * Formats this message with the specified arguments.
     * <p>
     * Calls {@link String#format(String, Object...)} internally.
     *
     * @param args arguments referenced by the format specifiers in the format string
     * @return the formatted string
     */
    public String format(Object... args) {
        return String.format(this.message, args);
    }


}
