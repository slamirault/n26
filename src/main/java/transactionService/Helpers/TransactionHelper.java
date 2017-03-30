package transactionService.Helpers;

import domain.TransactionRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionHelper
{
    JdbcTemplate jdbcTemplate;

    public TransactionHelper(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addTransaction(double amount, long timestamp)
    {
        TransactionRepository repo = new TransactionRepository(jdbcTemplate);
        boolean entryAdded = repo.createTransaction(amount, timestamp);

        if (entryAdded)
        {
            return true;
        }
        return false;
    }





}
