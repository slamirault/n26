package transactionService.Helpers;

import java.util.Timer;
import java.util.TimerTask;

public class TimerHelper
{
    Timer timer;
    static int currentPosition = 0;

    public TimerHelper(Timer timer, BucketHelper bucketHelper)
    {
        this.timer = timer;
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                currentPosition = bucketHelper.reset(currentPosition);
            }
        };

        timer.schedule(task, 15000, 1000);
    }

    public static int getCurrentPosition()
    {
        return currentPosition;
    }
}
