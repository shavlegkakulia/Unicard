package ge.unicard.pos.presentation.send_mrz;

import ge.unicard.pos.model.RegisterUser;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

public class SendMRZContract {

    interface View extends BaseView {

        void onSendMRZSuccess();
        void  onSendErrorMessage(String message, int resultCode);
    }

    public interface Presenter extends BasePresenter<View> {

        void onSendDataMRZ(RegisterUser registerUser);
    }

    private SendMRZContract() {
    }
}
