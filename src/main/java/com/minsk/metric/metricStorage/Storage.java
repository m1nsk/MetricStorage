package com.minsk.metric.metricStorage;

public interface Storage<T extends Comparable<T>> {
    void saveItem(T timeStamp);

    int count24Hour();

    int countHour();

    int countMinute();

    void clearGarbage();

    void clearAll();
}
