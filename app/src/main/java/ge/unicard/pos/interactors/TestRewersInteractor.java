package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface TestRewersInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onStatusReceived(String status);

        public abstract void onTransactionSuccess();
    }

    void rewersTest(
            RewersResponse rewersResponse,
                           @NonNull final Callback callback);

}
