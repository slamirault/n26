package transactionService.Helpers;

public class BucketHelper
{
    Bucket[] buckets = new Bucket[60];
    Object lock = new Object();

    public BucketHelper()
    {
        buckets = new Bucket[60];
        for (int i = 0; i < 60; i++)
        {
            buckets[i] = new Bucket();
        }
    }

    public Bucket[] getBuckets()
    {
        return buckets;
    }

    public void addToBuckets(double amount, long timestamp, int currentPosition)
    {
        synchronized(lock)
        {
            /*TODO REMOVE 60*/
            int secondDifference = (int) Math.floorDiv((System.currentTimeMillis() - timestamp), 1000);
            if (secondDifference > 60)
            {
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
