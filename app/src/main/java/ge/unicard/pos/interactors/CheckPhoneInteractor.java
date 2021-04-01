package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface CheckPhoneInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void checkPhone(String code, boolean withError);
    }

    void startCheckPhone(@NonNull String card,
                        @NonNull Callback callback);
}
