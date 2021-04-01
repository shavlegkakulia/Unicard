package ge.unicard.pos.presentation.send_otp;

import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

/**
 * Created by Akaki on 11/1/18.
 */
public final class SendOtpContract {

    public static final String CARD_NO_EXTRA = "card_no";

    public static final String STAN_EXTRA = "stan";
    public static final String OTP_EXTRA = "otp";
    public static final String AMOUNT_EXTRA = "amount";

    interface View extends BaseView {
        void onResult(String stan,
                      String otp, String amount);
    }

    public interface Presenter extends BasePresenter<View> {
        void onSubmitButtonCLicked(String code);

        void onResendButtonCLicked();
    }

    private SendOtpContract() {
    }
}
