package com.minsk.metric.metricStorage.impl;

import com.minsk.metric.metricStorage.Storage;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetricStorageImplTest {

    @Test
    void saveItem() {
        Storage storage = new MetricStorageImpl();
        storage.saveItem(Instant.now());
        storage.saveItem(Instant.now().minusSeconds(61));
        storage.saveItem(Instant.now().minusSeconds(60).plusMillis(100));
        storage.saveItem(Instant.now().minusSeconds(60));
        storage.saveItem(Instant.now().minusSeconds(60).minusMillis(100));
        storage.saveItem(Instant.now().minusSeconds(60 * 60).minusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(60 * 30));
        storage.saveItem(Instant.now().minusSeconds(60 * 60).plusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24).plusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 23));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24).minusSeconds(1));
        assertEquals(2, storage.countMinute());
        assertEquals(7, storage.countHour());
        assertEquals(10, storage.count24Hour());
    }

    @Test
    void count24Hour() {
        Storage storage = new MetricStorageImpl();
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24).plusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 23));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24).minusSeconds(1));

        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24).plusMillis(100));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 24).minusMillis(100));
        assertEquals(3, storage.count24Hour());
    }

    @Test
    void countHour() {
        Storage storage = new MetricStorageImpl();
        storage.saveItem(Instant.now().minusSeconds(60 * 60).plusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(60 * 60));
        storage.saveItem(Instant.now().minusSeconds(60 * 60).minusSeconds(1));

        storage.saveItem(Instant.now().minusSeconds(60 * 60).plusMillis(100));
        storage.saveItem(Instant.now().minusSeconds(60 * 60));
        storage.saveItem(Instant.now().minusSeconds(60 * 60).minusMillis(100));
        assertEquals(2, storage.countHour());
    }

    @Test
    void countMinute() {
        Storage storage = new MetricStorageImpl();
        storage.saveItem(Instant.now().minusSeconds(60).plusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(60));
        storage.saveItem(Instant.now().minusSeconds(60).minusSeconds(1));

        storage.saveItem(Instant.now().minusSeconds(60).plusMillis(100));
        storage.saveItem(Instant.now().minusSeconds(60));
        storage.saveItem(Instant.now().minusSeconds(60).minusMillis(100));
        assertEquals(2, storage.countMinute());
    }

}