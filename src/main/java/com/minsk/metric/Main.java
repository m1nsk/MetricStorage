package com.minsk.metric;

import com.minsk.metric.metricGC.MetricGC;
import com.minsk.metric.metricStorage.Storage;
import com.minsk.metric.metricStorage.impl.StorageImpl;

import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        Storage storage = StorageImpl.getInstance();
        Thread gcThread = new Thread(new MetricGC(storage, 1_000_000));
        gcThread.setDaemon(true);
        gcThread.start();
        storage.saveItem(Instant.now().minusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(1));
        storage.saveItem(Instant.now().minusSeconds(70));
        storage.saveItem(Instant.now().minusSeconds(70));
        storage.saveItem(Instant.now().minusSeconds(60 * 61));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 23));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 25));
        storage.saveItem(Instant.now().minusSeconds(60 * 60 * 25));
        System.out.println(storage.countMinute());
        System.out.println(storage.countHour());
        System.out.println(storage.count24Hour());
    }
}
