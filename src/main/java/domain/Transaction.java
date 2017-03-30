package domain;

/**
 * Created by Stevie on 3/30/2017.
 */
public class Transaction
{
    public double amount;
    public long timestamp;

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
}
