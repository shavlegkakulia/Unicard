package ge.unicard.pos.presentation.send_otp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import ge.unicard.pos.R;
import ge.unicard.pos.ui.base.BaseActivity;

/**
 * Created by Akaki on 11/1/18.
 */
public class SendOtpActivity extends BaseActivity {

    public static Intent buildIntent(@NonNull Context ctx,
                                     @NonNull String amount,
                                     @NonNull String cardNo) {
        return new Intent(ctx, SendOtpActivity.class)
                .putExtra(SendOtpContract.CARD_NO_EXTRA, cardNo)
                .putExtra(SendOtpContract.AMOUNT_EXTRA, amount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.SmsVerification_Theme);

        final SendOtpFragment fr = SendOtpFragment.newInstance();
        fr.setArguments(getIntent().getExtras());
        loadFragment(fr);
    }
}
