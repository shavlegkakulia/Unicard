package ge.unicard.pos.data;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import ge.unicard.pos.model.TransactionItem;

public class TransactionsLocalDataSource implements TransactionsDataSource{

    @NonNull
    @Override
    public List<TransactionItem> getTransactions() {
        //todo impelement db
        return Collections.emptyList();
    }
}
