package com.example.demo.util;

/**
 * Represents a 2D vector with basic operations and utility methods.
 */
public class Vector {
    private double x, y;

    /**
     * Getter method for the x-coordinate.
     *
     * @return The x-coordinate value.
     */
    public double getX() {
        return x;
    }

    /**
     * Setter for the x-coordinate.
     *
     * @param x The new x-coordinate value.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Getter method for the y-coordinate.
     *
     * @return The y-coordinate value.
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for the y-coordinate.
     *
     * @param y The new y-coordinate value.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Default constructor that initializes the {@link Vector} to {@code (0, 0)}.
     */
    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructs a {@link Vector} with the specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Generates a random {@link Vector} with each coordinate bounded by the given minimum and maximum bounds.
     *
     * @param min The {@link Vector} representing the minimum bounds.
     * @param max The {@link Vector} representing the maximum bounds.
     * @return A new {@link Vector} with its x and y coordinates randomly generated between the bounds.
     */
    public static Vector random(Vector min, Vector max) {
        double randomX = Math.random() * (max.getX() - min.getX()) + min.getX();
        double randomY = Math.random() * (max.getY() - min.getY()) + min.getY();

        return new Vector(randomX, randomY);
    }

    /**
     * Adds the given {@link Vector} to this.
     *
     * @param vector The {@link Vector} to add to this.
     * @return A new {@link Vector} that represents the sum.
     */
    public Vector add(Vector vector) {
        return new Vector(x + vector.getX(), y + vector.getY());
    }

    /**
     * Subtracts the given {@link Vector} from this.
     *
     * @param vector The {@link Vector} to subtract from this.
     * @return A new {@link Vector} resulting from the subtraction.
     */
    public Vector subtract(Vector vector) {
        return new Vector(x - vector.getX(), y - vector.getY());
    }

    /**
     * Multiplies the x and y coordinates by the given scalar value.
     *
     * @param scalar The scalar value by which to multiply the coordinates.
     * @return A new {@link Vector} with its x and y coordinates scaled by the given scalar.
     */
    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    /**
     * Divides the x and y coordinates by the given scalar value.
     *
     * @param scalar The scalar value by which to divide the coordinates.
     * @return A new {@link Vector} with its x and y coordinates divided by the given scalar.
     */
    public Vector divide(double scalar) {
        return new Vector(x / scalar, y / scalar);
    }

    /**
     * Calculates and returns the magnitude (length) of this {@link Vector}.
     *
     * @return The magnitude of the {@link Vector}.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns a {@link Vector} with the same direction, but a magnitude of 1.
     * If the {@link Vector} is {@code (0, 0)}, it returns {@code (0, 0)}.
     *
     * @return A new {@link Vector} normalized version of this.
     */
    public Vector normalized() {
        double magnitude = magnitude();

        if (magnitude != 0)
            return this.divide(magnitude);

        return new Vector();
    }

    /**
     * Returns a {@link Vector} where the x and y coordinates are within the specified minimum and maximum bounds.
     *
     * @param min The {@link Vector} representing the minimum bounds.
     * @param max The {@link Vector} representing the maximum bounds.
     * @return A new {@link Vector} with its x and y coordinates clamped within the specified bounds.
     */
    public Vector clamped(Vector min, Vector max) {
        double clampedX = Math.max(min.getX(), Math.min(x, max.getX()));
        double clampedY = Math.max(min.getY(), Math.min(y, max.getY()));

        return new Vector(clampedX, clampedY);
    }

    /**
     * Calculates the normalized direction vector pointing from this to the specified {@link Vector}.
     *
     * @param vector The target {@link Vector} to which the direction is calculated.
     * @return A new {@link Vector} representing the normalized direction from this to the specified {@link Vector}.
     */
    public Vector directionTo(Vector vector) {
        return new Vector(vector.getX() - x, vector.getY() - y).normalized();
    }

    /**
     * Calculates the distance between this and the given {@link Vector}.
     *
     * @param vector The {@link Vector} to which the distance is calculated.
     * @return The distance between this and the specified {@link Vector}.
     */
    public double distanceTo(Vector vector) {
        return vector.subtract(this).magnitude();
    }
}
