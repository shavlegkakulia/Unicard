package ge.unicard.pos.presentation.send_otp;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import ge.unicard.pos.bus.OTPReceviedMessage;
import ge.unicard.pos.interactors.SendOtpInteractor;
import ge.unicard.pos.presentation.base.BasePresenterImpl;

/**
 * Created by Akaki on 11/1/18.
 */
public class SendOtpPresenter extends BasePresenterImpl<SendOtpContract.View>
        implements SendOtpContract.Presenter {

    private final SendOtpInteractor mSendOtpInteractor;

    private String mStan;

    @Inject
    public SendOtpPresenter(SendOtpInteractor sendOtpInteractor) {
        mSendOtpInteractor = sendOtpInteractor;
    }

    @Override
    public void onAttachView(@NonNull SendOtpContract.View view) {
        super.onAttachView(view);

        makeOtpRequest();
    }

    @Override
    public void onSubmitButtonCLicked(String code) {
        if (getView() != null) {
            getView().onResult(mStan, code,getView().getArgs().getString(SendOtpContract.AMOUNT_EXTRA));
        }
    }

    @Override
    public void onResendButtonCLicked() {
        makeOtpRequest();
    }

    private void makeOtpRequest() {
        if (getView() != null) {
            final String cardNo = getView().getArgs().getString(SendOtpContract.CARD_NO_EXTRA);
            final String amount = getView().getArgs().getString(SendOtpContract.AMOUNT_EXTRA);
            mStan = null;
            mSendOtpInteractor.sendOtp(cardNo, new SendOtpInteractor.Callback(this) {
                @Override
                public void onSuccess(String stan) {
                    EventBus.getDefault().post(new OTPReceviedMessage());
                    mStan = stan;
                }
            });
        }
    }
}

