package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ge.unicard.pos.R;
import ge.unicard.pos.ui.base.BaseActivity;

/**
 * Created by Akaki on 10/31/18.
 */
public class CardsActivity extends BaseActivity {

    public static Intent buildIntent(Context ctx,
                                     @Mode int mode) {
        return new Intent(ctx, CardsActivity.class)
                .putExtra(CardsContract.EXTRA_MODE, mode);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final @Mode int mode = getIntent().getIntExtra(
                CardsContract.EXTRA_MODE, 0);
        if (mode > 0) {
            switch (mode) {
                case Mode.MODE_BONUS_ACCUMULATION: {
                    setTheme(R.style.BonusAccumulationOrMakePayment_BonusAccumulationTheme);
                    loadFragment(BonusAccumulationFragment.newInstance());
                    break;
                }
                case Mode.MODE_MAKE_PAYMENT:
                    setTheme(R.style.BonusAccumulationOrMakePayment_MakePaymentTheme);
                    loadFragment(MakePaymentFragment.newInstance());
                    break;
                case Mode.MODE_PURCHASE:
                    setTheme(R.style.BonusAccumulationOrMakePayment_Purchase);
                    loadFragment(PurchaseFragment.newInstance());
                    break;
                case Mode.MODE_BALANCE:
                    setTheme(R.style.BonusAccumulationOrMakePayment_Balance);
                    loadFragment(BalanceFragment.newInstance());
                    break;
            }
        }
        else {
                finish();
        }
    }
}
