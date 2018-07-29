package com.minsk.metric.metricStorage;

import java.time.Instant;

public interface Storage {
    /**
     * saves new Instant item to storage
     * @param timeStamp
     */
    void saveItem(Instant timeStamp);

    /**
     * counts all items in last 24 hours
     * @return count of intems in last 24 hours
     */
    int count24Hour();
    /**
     * counts all items in last hour
     * @return count of intems in last hour
     */
    int countHour();
    /**
     * counts all items in last minute
     * @return count of intems in last minute
     */
    int countMinute();

    /**
     * clears all useless data from storage
     */
    void clearGarbage();

}
