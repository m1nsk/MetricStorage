package com.minsk.metric.metricStorage.impl;

import com.minsk.metric.metricStorage.MetricStorage;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class MetricStorageImpl implements MetricStorage {
    private final static int minute = 60;
    private final static int hour = minute * 60;
    private final static int fullDay = hour * 24;
    private ConcurrentSkipListSet<Item> storage = new ConcurrentSkipListSet<>();

    private static class SingletonHolder {
        static final MetricStorage instance = new MetricStorageImpl();
    }

    public static MetricStorage getInstance() {
        return SingletonHolder.instance;
    }

    private MetricStorageImpl() {
    }

    @Override
    public void saveItem(Instant item) {
        storage.add(new Item(item));

    }

    @Override
    public int count24Hour() {
        return storage.tailSet(getToElement(fullDay), true).size();
    }

    @Override
    public int countHour() {
        return storage.tailSet(getToElement(hour), true).size();
    }

    @Override
    public int countMinute() {
        return storage.tailSet(getToElement(minute), true).size();
    }

    private Item getToElement(int interval) {
        return new Item(getTimeInterval(interval), Long.MAX_VALUE);
    }

    private Instant getTimeInterval(int interval) {
        return Instant.now().minusSeconds(interval);
    }

    @Override
    public void clearGarbage() {
        storage.headSet(new Item(getTimeInterval(fullDay))).clear();
    }

    static class Item implements Comparable<Item> {
        private static AtomicLong idAtomic = new AtomicLong(Long.MIN_VALUE);
        private Long id;
        Instant instant;

        Item(Instant instant) {
            this.instant = instant;
            this.id = idAtomic.incrementAndGet();
        }

        Item(Instant instant, Long id) {
            this.id = id;
            this.instant = instant;
        }

        @Override
        public int compareTo(Item o) {
            int result = this.instant.compareTo(o.instant);
            return result == 0 ? id.compareTo(o.id) : result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Objects.equals(id, item.id) &&
                    Objects.equals(instant, item.instant);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, instant);
        }
    }
}
