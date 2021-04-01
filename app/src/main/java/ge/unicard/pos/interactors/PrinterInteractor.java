package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.BindView;
import ge.unicard.pos.R;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.networking.messaging.base.TestResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.ui.widgets.CTextView;

public interface PrinterInteractor {

    abstract class Callback{
        public abstract void onSuccess();
        public abstract void onFailed();
    }

     void print(@NonNull TransactionResponse transactionResponse,
                      @NonNull Callback callback);

    void printMakePayment(@NonNull TransactionResponse transactionResponse,
                       @NonNull Callback callback);

    void printPurchase(@NonNull PurchaseResponse purchaseResponse,
              @NonNull Callback callback);

    void printReward(@NonNull TestResponse testResponse, @NonNull Callback callback, int resultCode);

    void printRewers(@NonNull RewersResponse rewersResponse, @NonNull Callback callback);

}
