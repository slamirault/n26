package transactionService.Helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BucketHelper
{
    Bucket[] buckets = new Bucket[60];
    Object lock = new Object();
    Logger logger = Logger.getLogger(BucketHelper.class.getName());

    public BucketHelper()
    {
        /*
        TODO: In case the server goes down, the values should be pulled from the db initially
         */
        buckets = new Bucket[60];
        for (int i = 0; i < 60; i++)
        {
            buckets[i] = new Bucket();
        }
        logger.log(Level.INFO, "Buckets initialized");
    }

    public Bucket[] getBuckets()
    {
        return buckets;
    }

    public void addToBuckets(double amount, long timestamp, int currentPosition)
    {
        synchronized(lock)
        {
            int secondDifference = (int) Math.floorDiv((System.currentTimeMillis() - timestamp), 1000);
            if (secondDifference > 60)
            {
                logger.log(Level.INFO, "Value added too far in the past");
                return;
            }

            int bucketToAdd = currentPosition + 60 - secondDifference;

            Bucket currentBucket = buckets[bucketToAdd];

            if (currentBucket.getMax() < amount)
            {
                currentBucket.setMax(amount);
            }

            if (currentBucket.getMin() > amount)
            {
                currentBucket.setMin(amount);
            }

            currentBucket.increaseCount(1);
            currentBucket.increaseSum(amount);
            currentBucket.calculateAvg();
            logger.log(Level.INFO, "Value was added to bucket");
        }
    }

    public Bucket calculateStatistics()
    {
        Bucket overallStats = new Bucket();

        for (int i = 0; i < 60; i++)
        {
            Bucket currentBucket = buckets[i];
            if (currentBucket.count != 0)
            {
                if (overallStats.getMax() < currentBucket.getMax())
                {
                    overallStats.setMax(currentBucket.getMax());
                }

                if (overallStats.getMin() > currentBucket.getMin())
                {
                    overallStats.setMin(currentBucket.getMin());
                }

                overallStats.increaseCount(currentBucket.getCount());
                overallStats.increaseSum(currentBucket.getSum());
            }
        }

        if (overallStats.count == 0)
        {
            overallStats.setMax(0);
            overallStats.setMin(0);
        }

        overallStats.calculateAvg();

        return overallStats;
    }

    public int reset(int currentPosition)
    {
        synchronized (lock)
        {
            buckets[currentPosition] = new Bucket();
            currentPosition = (currentPosition + 1) % 60;
        }
        return currentPosition;
    }
}
