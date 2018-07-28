package com.minsk.metric.metricStorage.impl;

import com.minsk.metric.metricStorage.Storage;
import org.junit.jupiter.api.BeforeEach;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageImplTest {
    private static final Storage storage = new StorageImpl();

    @BeforeEach
    public void clearAll() {
        storage.clearAll();
    }

    @org.junit.jupiter.api.Test
    void saveItem() {
        storage.saveItem(Instant.now());
        storage.saveItem(Instant.now().minusSeconds(61));
        storage.saveItem(Instant.now().minusSeconds(60 * 61));
        assertEquals(1, storage.countMinute());
        assertEquals(2, storage.countHour());
        assertEquals(3, storage.count24Hour());
    }

    @org.junit.jupiter.api.Test
    void count24Hour() {
        storage.saveItem(Instant.now().minusSeconds(60 * 61));
        assertEquals(1, storage.count24Hour());
    }

    @org.junit.jupiter.api.Test
    void countHour() {
        storage.saveItem(Instant.now().minusSeconds(60 * 59));
        assertEquals(1, storage.countHour());
    }

    @org.junit.jupiter.api.Test
    void countMinute() {
        storage.saveItem(Instant.now().minusSeconds(1));
        assertEquals(1, storage.countMinute());
    }

}