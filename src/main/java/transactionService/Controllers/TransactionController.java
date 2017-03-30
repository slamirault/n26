package transactionService.Controllers;

import domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import transactionService.Helpers.Bucket;
import transactionService.Helpers.BucketHelper;
import transactionService.Helpers.TimerHelper;
import transactionService.Helpers.TransactionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class TransactionController
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    TimerHelper timerHelper;
    BucketHelper bucketHelper;
    Timer timer = new Timer();

    Logger logger = Logger.getLogger(TransactionController.class.getName());


    public TransactionController()
    {
        bucketHelper = new BucketHelper();
        timerHelper = new TimerHelper(timer, bucketHelper);
        logger.log(Level.INFO, "Initializing helpers");
    }

    /*
     * Just a get method to poke the server
     */
    @RequestMapping(value={"/", "/health"}, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String health()
    {
        logger.log(Level.INFO, "Health page was called");
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Health</title>\n" +
                "</head>\n" +
                "<body style='background-color:#D8BFD8'>\n" +
                "    <p style='color:purple; font-weight: bold;'>My service is up and running! :)</p>\n" +
                "</body>\n" +
                "</html>";
    }

    @RequestMapping(value="/transactions", method = RequestMethod.POST)
    public String transactions(@RequestParam double amount, @RequestParam long timestamp,
                         HttpServletRequest request, HttpServletResponse response)
    {
        logger.log(Level.INFO, "transaction API was called");
        TransactionHelper helper = new TransactionHelper(jdbcTemplate);
        bucketHelper.addToBuckets(amount, timestamp, TimerHelper.getCurrentPosition());
        boolean transactionAdded = helper.addTransaction(amount, timestamp);

        if (transactionAdded)
        {
            logger.log(Level.WARNING, "Transaction was added");
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        else
        {
            logger.log(Level.WARNING, "Transaction was not added");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

        return "{}";
    }

    @RequestMapping(value="/statistics", method = RequestMethod.GET)
    public Bucket statistics()
    {
        logger.log(Level.INFO, "statistics API was called");
        return bucketHelper.calculateStatistics();
    }

}