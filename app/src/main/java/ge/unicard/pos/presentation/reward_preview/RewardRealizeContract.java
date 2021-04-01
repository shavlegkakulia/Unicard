package ge.unicard.pos.presentation.reward_preview;

import ge.unicard.pos.networking.messaging.base.TestResponse;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;
import ge.unicard.pos.presentation.reward.list_rewards.RewardsListContract;

public class RewardRealizeContract {

    interface View extends BaseView {

        void onSuccessGiveReward();
        void  onSendErrorMessage(String message, int resultCode);
    }

    public interface Presenter extends BasePresenter<View> {

        void onRewardRealizeButtonClicked(TestResponse rewardCode);
    }

    private RewardRealizeContract() {
    }
}
