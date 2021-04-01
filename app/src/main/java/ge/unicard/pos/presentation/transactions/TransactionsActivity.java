package ge.unicard.pos.presentation.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import ge.unicard.pos.R;
import ge.unicard.pos.ui.base.BaseActivity;

/**
 * Created by Akaki on 11/1/18.
 */
public class TransactionsActivity extends BaseActivity {

    public static Intent buildIntent(@NonNull Context ctx) {
        return new Intent(ctx, TransactionsActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Transactions_Theme);
        loadFragment(TransactionsFragment.newInstance());
    }
}
