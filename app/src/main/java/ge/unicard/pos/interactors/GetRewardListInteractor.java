package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.RewardResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface GetRewardListInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onStatusReceived(String status);

        public abstract void onTransactionSuccess(RewardResponse rewardResponse);
    }

    void getRewardList(String card,String Source,
                           @NonNull final Callback callback);


}
