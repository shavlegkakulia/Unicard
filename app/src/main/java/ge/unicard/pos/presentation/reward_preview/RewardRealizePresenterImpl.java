package ge.unicard.pos.presentation.reward_preview;

import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.RewardRealizeInteractor;
import ge.unicard.pos.networking.messaging.base.TestResponse;
import ge.unicard.pos.presentation.base.BasePresenterImpl;
import ge.unicard.pos.presentation.cards.CardsPresenter;

public class RewardRealizePresenterImpl extends BasePresenterImpl<RewardRealizeContract.View>
        implements RewardRealizeContract.Presenter{

    private final RewardRealizeInteractor mRewardRealizeInteractor;
    private final PrinterInteractor mPrinterInteractor;

    @Inject
    public RewardRealizePresenterImpl(RewardRealizeInteractor rewardRealizeInteractor,
                                      PrinterInteractor printerInteractor) {
        mRewardRealizeInteractor = rewardRealizeInteractor;
        mPrinterInteractor = printerInteractor;
    }

    @Override
    public void onRewardRealizeButtonClicked(TestResponse response) {
        mRewardRealizeInteractor.RewardRealize(response.reward_code,
                new RewardRealizeInteractor.Callback(this) {
            @Override
            public void onRewardRealize(int ResultCode,
                                        String ErrorMessage,
                                        String DisplayText) {

                Log.d("qqqqqqqqqqqqqqqqqq", String.valueOf(ResultCode));
                getView().onSuccessGiveReward();
                mPrinterInteractor.printReward(response, new PrinterInteractor.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed() {

                    }
                }, ResultCode);
            }

                    @Override
                    public void onMessageError(String status, int code) {
                        getView().onSendErrorMessage(status, code);
                    }
                });
    }
}
