package ge.unicard.pos.presentation.report_action;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.model.CustomCallback;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

public class ActionReportContract {
    interface View extends BaseView {
        //void onResult();
    }

    public interface Presenter extends BasePresenter<View> {
      void onSubmitPrinter();

      void onRevers(RewersResponse transactionResponse, CustomCallback customCallback);

        void onPrintAgain(GeneralModel generalModel);
    }

    private ActionReportContract() {

    }
}
