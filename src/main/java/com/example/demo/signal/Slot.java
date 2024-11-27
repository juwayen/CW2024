package com.example.demo.signal;

import java.lang.reflect.Method;

public class Slot {
    private final Object target;
    private final Method method;

    public Slot(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public void invoke(Object... args) {
        try {
            method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke method", e);
        }
    }
}