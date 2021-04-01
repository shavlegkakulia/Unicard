package ge.unicard.pos.presentation.reward;

import android.os.Bundle;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.cards.RewardEnterFragment;
import ge.unicard.pos.ui.base.BaseActivity;

public class RewardEnterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setTheme(R.style.BonusAccumulationOrMakePayment_BonusAccumulationTheme);
        loadFragment(RewardEnterFragment.newInstance());
    }
}
