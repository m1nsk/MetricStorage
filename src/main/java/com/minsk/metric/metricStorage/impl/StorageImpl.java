package com.minsk.metric.metricStorage.impl;

import com.minsk.metric.metricStorage.Storage;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class StorageImpl implements Storage {
    private final static int minute = 60;
    private final static int hour = minute * 60;
    private final static int fullDay = hour * 24;
    private ConcurrentSkipListSet<Item> storage = new ConcurrentSkipListSet<>();

    private static class SingletonHolder {
        static final Storage instance = new StorageImpl();
    }

    public static Storage getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void saveItem(Comparable timeStamp) {
        storage.add(new Item(timeStamp));

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
    public void clearAll() {
        storage.clear();
    }

    @Override
    public void clearGarbage() {
        storage.headSet(new Item(getTimeInterval(fullDay))).clear();
    }

    static class Item<T extends Comparable<T>> implements Comparable<Item<T>> {
        private static AtomicLong idAtomic = new AtomicLong(0);
        private Long id;
        T instant;

        Item(T instant) {
            this.instant = instant;
            this.id = idAtomic.incrementAndGet();
        }

        Item(T instant, Long id) {
            this.id = id;
            this.instant = instant;
        }

        @Override
        public int compareTo(Item<T> o) {
            int result = this.instant.compareTo(o.instant);
            return result == 0 ? id.compareTo(o.id) : result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item<?> item = (Item<?>) o;
            return Objects.equals(id, item.id) &&
                    Objects.equals(instant, item.instant);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, instant);
        }
    }
}
