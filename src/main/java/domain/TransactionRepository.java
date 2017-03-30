package domain;

import domain.Interfaces.ITransactionRepository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Stevie on 3/30/2017.
 */
public class TransactionRepository implements ITransactionRepository
{
    JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createTransaction(double amount, long timestamp)
    {
        String sql = "INSERT INTO sys.transactions (idtransactions,amount,timestamp) VALUES (null,?,?);";
        int rowsUpdated = 0;

        try
        {
            rowsUpdated = jdbcTemplate.update(sql,new Object[]{amount,timestamp});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return rowsUpdated == 1;
    }

}
