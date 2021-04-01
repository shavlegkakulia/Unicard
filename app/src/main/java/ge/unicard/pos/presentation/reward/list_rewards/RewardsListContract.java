package ge.unicard.pos.presentation.reward.list_rewards;

import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

public class RewardsListContract {

    interface View extends BaseView {
        void onResult(String stan,
                      String otp);
    }

    public interface Presenter extends BasePresenter<View> {
        void onSubmitButtonCLicked(String code);

        void onResendButtonCLicked();
    }

    private RewardsListContract() {
    }

}
