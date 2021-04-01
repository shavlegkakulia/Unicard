package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface SubmitPhoneInteractor {
    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void submitPhone(String code, boolean withError);


    }

    void startSubmitPhone(@NonNull String card, String phone,
                         @NonNull SubmitPhoneInteractor.Callback callback);
}
