package ge.unicard.pos.presentation.transactions;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.model.CustomCallback;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

public class TransactionsContract {

    interface View extends BaseView {
    }

    public interface Presenter extends BasePresenter<View> {
         void onSubmitPrinterReport();

        void onRevers(RewersResponse transactionResponse, CustomCallback customCallback);

        void onPrintAgain(GeneralModel generalModel);
    }

    private TransactionsContract() {
    }
}
