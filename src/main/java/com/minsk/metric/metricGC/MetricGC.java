package com.minsk.metric.metricGC;

import com.minsk.metric.metricStorage.Storage;

public class MetricGC implements Runnable {
    private Storage storage;
    private long delay;

    public MetricGC(Storage storage, long delay) {
        this.storage = storage;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (storage != null) {
            try {
                storage.clearGarbage();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
