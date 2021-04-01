package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface SendOtpInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onSuccess(String stan);
    }

    void sendOtp(String card,
                 @NonNull Callback callback);
}
