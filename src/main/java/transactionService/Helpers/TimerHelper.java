package transactionService.Helpers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimerHelper
{
    Timer timer;
    static int currentPosition = 0;
    Logger logger = Logger.getLogger(TimerHelper.class.getName());

    public TimerHelper(Timer timer, BucketHelper bucketHelper)
    {
        this.timer = timer;
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                currentPosition = bucketHelper.reset(currentPosition);
                logger.log(Level.FINEST,"Bucket has been reset");
            }
        };

        timer.schedule(task, 15000, 1000);
    }

    public static int getCurrentPosition()
    {
        return currentPosition;
    }
}
