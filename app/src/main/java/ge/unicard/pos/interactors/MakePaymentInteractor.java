package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface MakePaymentInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onStatusReceived(String status);

        public abstract void onTransactionSuccess(TransactionResponse transactionResponse, String batchId);
    }

    void makePayment(String card,
                     double amount,
                     String stan,
                     String otp,
                     @NonNull Callback callback);
}
