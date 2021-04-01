package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.model.RegisterUser;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface SendMRZInteractor {
    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onSendMRZ(int ResultCode ,
                                             String ErrorMessage,
                                             String DisplayText);

        public abstract void onMessageError(String status, int Code);
    }

    void SendMRZ(@NonNull Callback callback, RegisterUser registerUser);
}
