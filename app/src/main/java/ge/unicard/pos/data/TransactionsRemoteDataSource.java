package ge.unicard.pos.data;

import android.support.annotation.NonNull;

import java.util.List;

import ge.unicard.pos.model.TransactionItem;

public class TransactionsRemoteDataSource implements TransactionsDataSource {

    @NonNull
    @Override
    public List<TransactionItem> getTransactions() {
        throw new RuntimeException("not yet implemented");
    }
}
