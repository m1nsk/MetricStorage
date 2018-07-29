package com.minsk.metric;

import com.minsk.metric.metricStorage.Storage;
import com.minsk.metric.metricStorage.impl.MetricStorageImpl;

import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        /*
            Example of usage Metric storage lib
         */
        Storage storage = MetricStorageImpl.getInstance();
        Thread gcThread = new Thread(() -> {
            while (storage != null) {
                try {
                    storage.clearGarbage();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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
