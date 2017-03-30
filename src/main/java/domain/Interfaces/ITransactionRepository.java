package domain.Interfaces;

import domain.Transaction;
import org.springframework.data.repository.*;

public interface ITransactionRepository extends Repository<Transaction, Long>
{

    boolean createTransaction(double amount, long timestamp);

}
