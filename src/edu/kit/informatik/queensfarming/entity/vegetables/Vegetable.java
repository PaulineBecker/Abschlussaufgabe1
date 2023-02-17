package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * @author uyxib
 * @version 1.0
 */
public enum Vegetable {

    MUSHROOM("mushrooms"),
    CARROT("carrots"),
    TOMATO("tomatoes"),
    SALAT("salads");

    private final String vegetableName;

    private Vegetable(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public String format(Object... args) {
        return String.format(this.vegetableName, args);
    }
}
