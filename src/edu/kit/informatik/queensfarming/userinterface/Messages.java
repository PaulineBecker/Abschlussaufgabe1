package edu.kit.informatik.queensfarming.userinterface;

/**
 * Messages enum that contains all input/output constants.
 *
 * @author uyxib
 * @verion 1.0
 */
public enum Messages {

    /**
     * The message printed at the beginning to ask how many players are playing the game
     */
    NUMBER_OF_PLAYERS("How many players?"),

    /**
     * The message printed to find out the names of the players
     * <p>
     * Expects one format argument:
     * The name of the player (string).
     */
    NAME_OF_PLAYER("Enter the name of Player %s"),

    /**
     * The message printed if a runa gets an ability
     * <p>
     * Expects one format argument:
     * The name of the player (string).
     */
    GOLD_TO_START("With how much gold should each player start?"),

    /**
     * The message printed if a runa gets an ability
     * <p>
     * Expects one format argument:
     * The name of the player (string).
     */
    GOLD_TO_WIN("With how much gold should a player win?"),

    /**
     * The message printed at the beginning to get the seed to shuffle the tiles.
     *
     */
    SEED_TO_SHUFFLE("Please enter the seed used to shuffle the tiles:"),

    /**
     * The message printed at the beginning of a new player move.
     * <p>
     * Expects one format argument:
     * The name of the player (string).
     */
    TURN_OF_PLAYER("It is %s turn!"),

    /**
     * The message printed at the beginning of a new player move after the turn.
     * <p>
     * Expects the format arguments in the following order:
     * The amount of vegetables (int).
     * Plural or singular of vegetable and has or have depending on singular or plural of vegetable (String enum)
     */
    GROWN_VEGETABLES("%d %s grown sice your last turn."),

    /**
     * The message printed in case that vegetables are spoiled.
     *
     */
    SPOILED_VEGETABLES("The vegetables in your barn are spoiled."),

    /**
     * The message printed when vegetables are bought at the market.
     * <p>
     * Expects the format arguments in the following order:
     * The name of a vegetable or  land (String in an enum).
     * amount of gold to buy vegetable or land (int)
     */
    BOUGHT_VEGETABLES("You have bought a %s for %d gold."),

    /**
     * The message printed when a player has harvested vegetables.
     * <p>
     * Expects the format arguments in the following order:
     * amount of harvested vegetables (int).
     * name of vegetable/s (String)
     */
    HARVESTED_LAND("You have harvested %d %s."),

    /**
     * The message printed when the game is over.
     * <p>
     * Expects the format arguments in the following order:
     * The number of the player (int).
     * name of the player (String)
     * amount of gold after the game
     */
    GOLD_AFTER_GAME("Player %d (%s): %d"),

    /**
     * The message printed the player who has won after the game has end.
     * <p>
     * Expects one format argument:
     * The name of the player (string).
     */
    PLAYER_WON("%s has won!");



    //TODO show market
    //TODO show barn
    //TODO show board

    private final String message;
    private Messages(String message) {
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
