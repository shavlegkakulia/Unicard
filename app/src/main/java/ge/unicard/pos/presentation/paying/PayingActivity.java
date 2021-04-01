package ge.unicard.pos.presentation.paying;

import android.os.Bundle;

import ge.unicard.pos.R;
import ge.unicard.pos.ui.base.BaseActivity;

/**
 * Created by Akaki on 10/31/18.
 */
public class PayingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.PageTheme_DarkGreen);
        loadFragment(PayingFragment.newInstance());
    }
}
