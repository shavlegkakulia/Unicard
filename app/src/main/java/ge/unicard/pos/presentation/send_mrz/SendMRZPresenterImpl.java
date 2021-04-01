package ge.unicard.pos.presentation.send_mrz;

import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.interactors.SendMRZInteractor;
import ge.unicard.pos.model.RegisterUser;
import ge.unicard.pos.presentation.base.BasePresenterImpl;

public class SendMRZPresenterImpl extends BasePresenterImpl<SendMRZContract.View>
        implements SendMRZContract.Presenter{

    private final SendMRZInteractor mSendMRZInteractor;

    @Inject
    public SendMRZPresenterImpl(SendMRZInteractor sendMRZInteractor) {
        mSendMRZInteractor = sendMRZInteractor;
    }
    @Override
    public void onSendDataMRZ(RegisterUser registerUser) {
        mSendMRZInteractor.SendMRZ(new SendMRZInteractor.Callback(this) {
            @Override
            public void onSendMRZ(int ResultCode, String ErrorMessage, String DisplayText) {
                Log.d("UNICARDLOG",String.valueOf(ResultCode));
                getView().onSendMRZSuccess();
            }

            @Override
            public void onMessageError(String status, int Code) {

            }
        }, registerUser);
    }
}
