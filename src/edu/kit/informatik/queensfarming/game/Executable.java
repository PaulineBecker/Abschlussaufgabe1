package edu.kit.informatik.queensfarming.game;

/**
 * Classes implementing this interface can be used for a command line interface.
 * Copied from Thomas Weber and Moritz Gstuer and then modified from
 * @author uyxib
 * @version 1.0
 */
public interface Executable {

    /**
     * Returns whether or not this structure is active.
     *
     * @return whether or not this structure is active
     */
    boolean isActive();

    /**
     * Quits this structure so {@link #isActive()} will return {@code false}.
     */
    void quit();

}
