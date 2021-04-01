package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ge.unicard.pos.bus.MessageReward;
import ge.unicard.pos.bus.ResrdsItem;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.GetRewardListInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.networking.messaging.base.RewardResponse;
import ge.unicard.pos.networking.messaging.get_reward_list.Reward;
import ge.unicard.pos.presentation.launcher.LauncherActivity;
import ge.unicard.pos.presentation.reward.RewardEnterActivity;
import ge.unicard.pos.presentation.reward.list_rewards.RewardsListActivity;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.activity;

public class   RewardEnterPresenterImpl extends CardsPresenter
        implements RewardEnterPresenter {

    private final GetRewardListInteractor mGetRewardListInteractor;
    private final Context mContext;

    static public List<Reward> rewardList;

    @Inject
    public RewardEnterPresenterImpl(CardSearchInteractor cardSearchInteractor,
                                    QrScannerInteractor qrScannerInteractor,
                                    SubmitPhoneInteractor submitPhoneInteractor,
                                    GetRewardListInteractor getRewardListInteractor,
                                    @ApplicationContext Context context) {
        super(cardSearchInteractor, qrScannerInteractor, submitPhoneInteractor);
        mGetRewardListInteractor = getRewardListInteractor;
        mContext= context;
    }

    @Override
    public void onCheckListReward(String card) {

    }

    @Override
    public void onScanCardButtonClicked() {
        super.onScanCardButtonClicked();
        if (getView() != null) {
            getView().onEnableSubmitButton(false);
        }
    }

    @Override
    public void onScanQrButtonClicked() {
        super.onScanQrButtonClicked();
        if (getView() != null) {
             getView().onEnableSubmitButton(false);
        }
    }

    @Override
    protected void onCardFound(String card, String status) {


        mGetRewardListInteractor.getRewardList(card, status, new GetRewardListInteractor.Callback(this) {
            @Override
            public void onStatusReceived(String status) {
                if (getView() != null) {
                    getView().onShowStatus(status, false);
                    //getView().on

                }
            }


            @Override
            public void onTransactionSuccess(RewardResponse rewardResponse) {
                rewardList = rewardResponse.rewardList;

                Intent intent = new Intent(mContext, RewardsListActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", rewardResponse.fullName);
                intent.putExtra("card", rewardResponse.card);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    protected void onSubmit(String cardNo, double amount) {
    }

    @Override
    protected void onRealize(String rewardCode) {

    }
}
