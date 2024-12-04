package com.example.demo.util;

public class Vector {
    public static final Vector ZERO = new Vector(0.0, 0.0);
    public static final Vector UP = new Vector(0.0, -1.0);
    public static final Vector DOWN = new Vector(0.0, 1.0);

    private double x, y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.getX(), y + vector.getY());
    }

    public Vector subtract(Vector vector) {
        return new Vector(x - vector.getX(), y - vector.getY());
    }

    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector divide(double scalar) {
        return new Vector(x / scalar, y / scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector normalized() {
        double magnitude = magnitude();

        if (magnitude != 0)
            return this.divide(magnitude);

        return Vector.ZERO;
    }

    public Vector clamped(Vector min, Vector max) {
        double clampedX = Math.max(min.getX(), Math.min(x, max.getX()));
        double clampedY = Math.max(min.getY(), Math.min(y, max.getY()));

        return new Vector(clampedX, clampedY);
    }

    public Vector directionTo(Vector vector) {
        return new Vector(vector.getX() - x, vector.getY() - y).normalized();
    }
}
