package ge.unicard.pos.presentation.paying;

import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

/**
 * Created by Akaki on 10/31/18.
 */
final class PayingContract {

    interface View extends BaseView {
        void openSmsVerificationPage();
    }

    interface Presenter extends BasePresenter<View> {
        void onSubmitButtonClicked();
    }

    private PayingContract() {
    }
}
