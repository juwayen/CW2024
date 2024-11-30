package com.example.demo.signal;

import java.util.ArrayList;
import java.util.List;

public class Signal {
    private final List<Runnable> slots = new ArrayList<>();

    public void connect(Runnable runnable) {
        slots.add(runnable);
    }

    public void emit() {
        for (Runnable slot : slots)
            slot.run();
    }
}