package com.example.demo.factory;

/**
 * Factory interface to define the contract for creating objects.
 */
public interface Factory {
    /**
     * Creates and returns a new instance of an object based on the factory's implementation.
     *
     * @return a newly created object of the expected type, as defined by the specific factory implementation.
     */
    Object create();
}
