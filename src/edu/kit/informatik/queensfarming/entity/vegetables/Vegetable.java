package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * @author uyxib
 * @version 1.0
 */
public enum Vegetable {

    MUSHROOM("mushroom"),
    CARROT("carrot"),
    TOMATO("tomato"),
    SALAT("salad");

    private final String vegetableName;

    private Vegetable(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public String format(Object... args) {
        return String.format(this.vegetableName, args);
    }
}
