package com.example.demo.signal;

import java.util.ArrayList;
import java.util.List;

public class Signal {
    public final List<Runnable> slots = new ArrayList<>();

    public void connect(Runnable runnable) {
        slots.add(runnable);
    }

    public void clearConnections() {
        slots.clear();
    }

    public void emit() {
        for (Runnable slot : new ArrayList<>(slots))
            slot.run();
    }
}