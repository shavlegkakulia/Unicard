package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface RewardRealizeInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onRewardRealize(int ResultCode ,
                                             String ErrorMessage,
                                             String DisplayText);

        public abstract void onMessageError(String status, int Code);
    }

    void RewardRealize(@NonNull String RewardCode,
                    @NonNull Callback callback);
}
