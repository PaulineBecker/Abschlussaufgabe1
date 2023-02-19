package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * the plural representation of vegetables
 *
 * @author uyxib
 * @version 1.0
 */
public enum Vegetable {
    /**
     * the plural representation fo the object Mushroom
     */
    MUSHROOM("mushrooms"),
    /**
     * the plural representation fo the object Carrot
     */
    CARROT("carrots"),
    /**
     * the plural representation fo the object Tomato
     */
    TOMATO("tomatoes"),
    /**
     * the plural representation fo the object Salad
     */
    SALAT("salads");

    private final String vegetableName;

    /**
     * instantiates a new {@link Vegetable} with the plural form of it
     * @param vegetableName plural form of a vegetable
     */

    Vegetable(String vegetableName) {
        this.vegetableName = vegetableName;
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
        return String.format(this.vegetableName, args);
    }
}
