package ge.unicard.pos.data;

import android.support.annotation.NonNull;

import java.util.List;

import ge.unicard.pos.model.TransactionItem;

interface TransactionsDataSource {

    @NonNull
    List<TransactionItem> getTransactions();
}
