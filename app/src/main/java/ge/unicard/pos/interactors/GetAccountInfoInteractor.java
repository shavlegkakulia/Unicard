package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoResponse;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface GetAccountInfoInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onAccountInfoReceived(String info,
                                                   boolean withError);
        public abstract void onTransactionSuccess(AccountInfoResponse accountInfoResponse);
    }

    void getAccountInfo(@NonNull String card,
                        @NonNull Callback callback);
}
