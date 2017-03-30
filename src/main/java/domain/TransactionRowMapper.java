package domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Transaction transaction = new Transaction();
        transaction.setAmount(Double.parseDouble(rs.getString("amount")));
        transaction.setTimestamp(Long.parseLong(rs.getString("timestamp")));
        return transaction;
    }

}