package com.example.demo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Signal {
    private final List<Slot> slots = new ArrayList<>();

    public void connect(Object target, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            slots.add(new Slot(target, method));
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No such method: " + methodName, e);
        }
    }

    public void emit(Object... args) {
        for (Slot slot : slots) {
            slot.invoke(args);
        }
    }
}