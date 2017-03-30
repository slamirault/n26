package transactionService.Helpers;

/**
 * Created by Stevie on 3/30/2017.
 */
public class Bucket
{
    double sum = 0.0;
    double avg = 0.0;
    double max = Double.MIN_VALUE;
    double min = Double.MAX_VALUE;
    long count = 0;

    public double getSum()
    {
        return sum;
    }

    public void setSum(double sum)
    {
        this.sum = sum;
    }

    public double getAvg()
    {
        return avg;
    }

    public void setAvg(double avg)
    {
        this.avg = avg;
    }

    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getMin()
    {
        return min;
    }

    public void setMin(double min)
    {
        this.min = min;
    }

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }

    public void increaseCount(long amount)
    {
        count += amount;
    }

    public void increaseSum(double amount)
    {
        sum += amount;
    }

    public void calculateAvg()
    {
        if (count == 0)
        {
            avg = 0.0;
        }
        else
        {
            avg = sum / count;
        }
    }
}
