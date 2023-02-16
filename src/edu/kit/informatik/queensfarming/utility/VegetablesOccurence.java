package edu.kit.informatik.queensfarming.utility;

/**
 * @author uyxib
 * @version 1.0
 */
public enum VegetablesOccurence {
    /**
     * plural of Vegetable
     */
    VEGETABLES("vegetables"),
    /**
     * singular of Vegetable
     */
    VEGETABLE("vegetable");

    private final String vegetable;

    VegetablesOccurence(String message) {
        this.vegetable = message;
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
        return String.format(this.vegetable, args);
    }

}
