package ge.unicard.pos.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Akaki on 11/2/18.
 */
public class TransactionItem implements Serializable{

    private final long id;
    private final Date date;
    private final double amount;
    private final String description;

    public TransactionItem(long id,
                           Date date,
                           double amount,
                           String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
