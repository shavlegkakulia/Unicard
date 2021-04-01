package ge.unicard.pos.presentation.report_action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.paying.PayingFragment;
import ge.unicard.pos.ui.base.BaseActivity;

public class ActionReportActivity extends BaseActivity {

    public static Intent buildIntent(@NonNull Context ctx) {
        return new Intent(ctx, ActionReportActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Transactions_Theme);
        loadFragment(ActionReportFragment.newInstance());
    }
}
