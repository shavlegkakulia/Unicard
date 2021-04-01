package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceResponse;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface GetBalanceInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onBalanceReceived(String balance,
                                               String status,
                                               boolean withError);
    }

    void getBalance(@NonNull String card,
                    @NonNull Callback callback);
}
