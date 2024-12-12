package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an implementation of the Observer pattern using the signal-slot mechanism.
 */
public class Signal {
    private final List<Runnable> slots = new ArrayList<>();

    /**
     * Registers a {@link Runnable} instance as a slot to be executed when this is emitted.
     *
     * @param runnable The {@link Runnable} instance to be connected to the {@link Signal}. This slot will be executed when this is emitted.
     */
    public void connect(Runnable runnable) {
        slots.add(runnable);
    }

    /**
     * Removes all the connected slots from this {@link Signal}.
     */
    public void clearConnections() {
        slots.clear();
    }

    /**
     * Emits the {@link Signal} by executing all connected slots.
     */
    public void emit() {
        for (Runnable slot : new ArrayList<>(slots))
            slot.run();
    }
}