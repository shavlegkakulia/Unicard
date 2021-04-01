package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface BonusAccumulationInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onStatusReceived(String status);

        public abstract void onTransactionSuccess(TransactionResponse transactionResponse, String stan, String batchId);
    }

    void bonusAccumulation(String card,
                           double amount,
                           @NonNull final Callback callback);
}
