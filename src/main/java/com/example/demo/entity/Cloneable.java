package com.example.demo.entity;

/**
 * Represents an object that can be cloned.
 */
public interface Cloneable {
    /**
     * Creates and returns a deep copy of the object.
     *
     * @return a new object that is a deep clone of the original object.
     */
    Object cloneObject();
}
