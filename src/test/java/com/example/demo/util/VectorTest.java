package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testDefaultConstructor() {
        Vector vector = new Vector();
        assertEquals(0, vector.getX(), "Default x should be 0");
        assertEquals(0, vector.getY(), "Default y should be 0");
    }

    @Test
    void testParameterizedConstructor() {
        Vector vector = new Vector(3.5, -2.5);
        assertEquals(3.5, vector.getX(), "x should be 3.5");
        assertEquals(-2.5, vector.getY(), "y should be -2.5");
    }

    @Test
    void testGettersAndSetters() {
        Vector vector = new Vector();
        vector.setX(4.2);
        vector.setY(-1.8);

        assertEquals(4.2, vector.getX(), "x should be 4.2");
        assertEquals(-1.8, vector.getY(), "y should be -1.8");
    }

    @Test
    void testAdd() {
        Vector v1 = new Vector(1.0, 2.0);
        Vector v2 = new Vector(3.0, 4.0);

        Vector result = v1.add(v2);

        assertEquals(4.0, result.getX(), "x of result should be 4.0");
        assertEquals(6.0, result.getY(), "y of result should be 6.0");
    }

    @Test
    void testSubtract() {
        Vector v1 = new Vector(5.0, 7.0);
        Vector v2 = new Vector(2.0, 3.0);

        Vector result = v1.subtract(v2);

        assertEquals(3.0, result.getX(), "x of result should be 3.0");
        assertEquals(4.0, result.getY(), "y of result should be 4.0");
    }

    @Test
    void testMultiply() {
        Vector vector = new Vector(2.0, -3.0);

        Vector result = vector.multiply(2.5);

        assertEquals(5.0, result.getX(), "x of result should be 5.0");
        assertEquals(-7.5, result.getY(), "y of result should be -7.5");
    }

    @Test
    void testDivide() {
        Vector vector = new Vector(9.0, -6.0);

        Vector result = vector.divide(3.0);

        assertEquals(3.0, result.getX(), "x of result should be 3.0");
        assertEquals(-2.0, result.getY(), "y of result should be -2.0");
    }

    @Test
    void testDivideByZero() {
        Vector vector = new Vector(9.0, -6.0);

        assertThrows(ArithmeticException.class, () -> vector.divide(0), "Division by zero should throw ArithmeticException");
    }

    @Test
    void testMagnitude() {
        Vector vector = new Vector(3.0, 4.0);

        assertEquals(5.0, vector.magnitude(), "Magnitude should be 5.0");
    }

    @Test
    void testNormalized() {
        Vector vector = new Vector(3.0, 4.0);
        Vector result = vector.normalized();

        assertEquals(0.6, result.getX(), "x of normalized vector should be 0.6");
        assertEquals(0.8, result.getY(), "y of normalized vector should be 0.8");
    }

    @Test
    void testNormalizedZeroVector() {
        Vector vector = new Vector(0, 0);
        Vector result = vector.normalized();

        assertEquals(0, result.getX(), "x of normalized zero vector should be 0");
        assertEquals(0, result.getY(), "y of normalized zero vector should be 0");
    }

    @Test
    void testClamped() {
        Vector vector = new Vector(5.0, -5.0);
        Vector min = new Vector(0, 0);
        Vector max = new Vector(3, 3);

        Vector result = vector.clamped(min, max);

        assertEquals(3.0, result.getX(), "x of clamped vector should be 3.0");
        assertEquals(0.0, result.getY(), "y of clamped vector should be 0.0");
    }

    @Test
    void testRandom() {
        Vector min = new Vector(1, 1);
        Vector max = new Vector(5, 5);

        Vector randomVector = Vector.random(min, max);

        assertTrue(randomVector.getX() >= 1 && randomVector.getX() <= 5, "x of random vector should be within bounds");
        assertTrue(randomVector.getY() >= 1 && randomVector.getY() <= 5, "y of random vector should be within bounds");
    }

    @Test
    void testDirectionTo() {
        Vector v1 = new Vector(1, 1);
        Vector v2 = new Vector(4, 5);

        Vector result = v1.directionTo(v2);

        assertEquals(0.6, result.getX(), "x of direction vector should be 0.6");
        assertEquals(0.8, result.getY(), "y of direction vector should be 0.8");
    }

    @Test
    void testDistanceTo() {
        Vector v1 = new Vector(1, 1);
        Vector v2 = new Vector(4, 5);

        assertEquals(5.0, v1.distanceTo(v2), "Distance between vectors should be 5.0");
    }
}
