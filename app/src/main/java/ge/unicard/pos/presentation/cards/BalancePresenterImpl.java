package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.R;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.GetBalanceInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceResponse;

public class BalancePresenterImpl extends CardsPresenter
        implements BalancePresenter {

    private final GetBalanceInteractor mGetBalanceInteractor;
    private final Context mContext;

    @Inject
    public BalancePresenterImpl(CardSearchInteractor cardSearchInteractor,
                                QrScannerInteractor qrScannerInteractor,
                                SubmitPhoneInteractor submitPhoneInteractor,
                                GetBalanceInteractor getBalanceInteractor,
                                @ApplicationContext Context context) {
        super(cardSearchInteractor, qrScannerInteractor, submitPhoneInteractor);
        mGetBalanceInteractor = getBalanceInteractor;
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
    protected void onCardFound(String card, String test) {


        mGetBalanceInteractor.getBalance(  card, new GetBalanceInteractor.Callback(BalancePresenterImpl.this) {
            @Override
            public void onBalanceReceived(String balance, String status, boolean withError) {
                String str = status; //+ "\n" +
                        //mContext.getString(R.string.action_balance) + ": " + String.valueOf(balance);

                if (getView() != null) {
                    getView().onShowStatus(str, false);
                    //getView().onEnableSubmitButton(!withError);
                    //getView().
                }
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