package ge.unicard.pos.presentation.paying;

import ge.unicard.pos.presentation.base.BasePresenterImpl;

/**
 * Created by Akaki on 10/31/18.
 */

public class PayingPresenter extends BasePresenterImpl<PayingContract.View>
        implements PayingContract.Presenter {

    @Override
    public void onSubmitButtonClicked() {
        if (getView() != null) {
            getView().openSmsVerificationPage();
        }
    }
}
