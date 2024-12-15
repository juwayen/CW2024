package com.example.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

class SignalTest {

    private Signal signal;

    @BeforeEach
    void setUp() {
        signal = new Signal();
    }

    @Test
    void testConnectAndEmitSingleSlot() {
        AtomicInteger counter = new AtomicInteger(0);

        signal.connect(counter::incrementAndGet);
        signal.emit();

        assertEquals(1, counter.get(), "Counter should be incremented to 1");
    }

    @Test
    void testConnectAndEmitMultipleSlots() {
        AtomicInteger counter1 = new AtomicInteger(0);
        AtomicInteger counter2 = new AtomicInteger(0);

        signal.connect(counter1::incrementAndGet);
        signal.connect(counter2::incrementAndGet);
        signal.emit();

        assertEquals(1, counter1.get(), "Counter1 should be incremented to 1");
        assertEquals(1, counter2.get(), "Counter2 should be incremented to 1");
    }

    @Test
    void testEmitMultipleTimes() {
        AtomicInteger counter = new AtomicInteger(0);

        signal.connect(counter::incrementAndGet);
        signal.emit();
        signal.emit();

        assertEquals(2, counter.get(), "Counter should be incremented to 2 after two emits");
    }

    @Test
    void testClearConnections() {
        AtomicInteger counter = new AtomicInteger(0);

        signal.connect(counter::incrementAndGet);
        signal.clearConnections();
        signal.emit();

        assertEquals(0, counter.get(), "Counter should remain 0 after clearing connections");
    }

    @Test
    void testEmitWithNoSlots() {
        assertDoesNotThrow(signal::emit, "Emitting with no slots connected should not throw an exception");
    }

    @Test
    void testConnectSameSlotMultipleTimes() {
        AtomicInteger counter = new AtomicInteger(0);
        Runnable slot = counter::incrementAndGet;

        signal.connect(slot);
        signal.connect(slot);
        signal.emit();

        assertEquals(2, counter.get(), "Counter should be incremented twice as the same slot was connected twice");
    }
}
