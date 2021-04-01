package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import ge.unicard.pos.bus.MessageEvent;
import ge.unicard.pos.db.CodeTransactions;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.BonusAccumulationInteractor;
import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.CheckPhoneInteractor;
import ge.unicard.pos.interactors.GetAccountInfoInteractor;
import ge.unicard.pos.interactors.GetRewardListInteractor;
import ge.unicard.pos.interactors.MakePaymentInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.networking.messaging.base.RewardResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoResponse;
import ge.unicard.pos.presentation.reward.list_rewards.RewardsListActivity;

import static ge.unicard.pos.db.CodeTransactions.TYPE_MALE_PAYMENT;
import static ge.unicard.pos.presentation.cards.RewardEnterPresenterImpl.rewardList;
import static ge.unicard.pos.presentation.launcher.LauncherActivity.activity;

public class MakePaymentPresenterImpl extends CardsPresenter
        implements MakePaymentPresenter {

    private final GetAccountInfoInteractor mGetAccountInfoInteractor;
    private final MakePaymentInteractor mMakePaymentInteractor;
    private final PrinterInteractor mPrinterInteractor;

    private GeneralModel generalModelAccumulationBonus;

    // request get list Rewards
    private final GetRewardListInteractor mGetRewardListInteractor;

    private final CheckPhoneInteractor mCheckPhoneInteractor;
    private final Context mContext;



    @Inject
    public MakePaymentPresenterImpl(CardSearchInteractor cardSearchInteractor,
                                    QrScannerInteractor qrScannerInteractor,
                                    SubmitPhoneInteractor submitPhoneInteractor,
                                    GetAccountInfoInteractor getAccountInfoInteractorr,
                                    MakePaymentInteractor makePaymentInteractor,
                                    PrinterInteractor printerInteractor,
                                    CheckPhoneInteractor checkPhoneInteractor,
                                    GetRewardListInteractor rewardListInteractor,
                                    @ApplicationContext Context context) {
        super(cardSearchInteractor, qrScannerInteractor,submitPhoneInteractor);
        mGetAccountInfoInteractor = getAccountInfoInteractorr;
        mMakePaymentInteractor = makePaymentInteractor;
        mPrinterInteractor = printerInteractor;
        mCheckPhoneInteractor = checkPhoneInteractor;
        mGetRewardListInteractor = rewardListInteractor;
        mContext= context;
    }

    @Override
    protected void onCardFound(String card, String status) {
        mGetAccountInfoInteractor.getAccountInfo(
                card,
                new GetAccountInfoInteractor.Callback(this) {
                    @Override
                    public void onAccountInfoReceived(String info,
                                                      boolean withError) {
                        if (getView() != null) {
                            getView().onShowStatus(info, withError);
                            getView().onEnableSubmitButton(!withError);
                        }
                    }

                    @Override
                    public void onTransactionSuccess(AccountInfoResponse accountInfoResponse) {

                    }
                });

        mCheckPhoneInteractor.startCheckPhone(card, new CheckPhoneInteractor.Callback(this) {
            @Override
            public void checkPhone(String code, boolean withError) {
                Log.d("mCheckPhoneInteractor", code);
                int codeInt = Integer.parseInt(code);
                if(codeInt != 200) {
                    if (getView() != null) {
                        getView().onCheckPhomeDialog(code, card);
                    }
                }
                else{
                    getView().onStartDialogRewards(card);
                    //startCheckRewards(card);
                }

            }
        });
    }

    @Override
    protected void onSubmit(String cardNo,
                            double amount) {
        if (getView() != null) {
            getView().openSendOtpPage(cardNo);
        }
    }

    @Override
    protected void onRealize(String rewardCode) {

    }


    @Override
    protected void onOtpReceived(String stan,
                                 String otp,
                                 String cardNo,
                                 double amount) {
        mMakePaymentInteractor.makePayment(cardNo, amount, stan, otp,
                new MakePaymentInteractor.Callback(this) {
                    @Override
                    public void onStatusReceived(String status) {
                        if (getView() != null) {
                            getView().onShowStatus(status, false);
                        }
                    }

                    @Override
                    public void onTransactionSuccess(TransactionResponse transactionResponse, String batchId) {

                        generalModelAccumulationBonus = new GeneralModel(transactionResponse.amount,
                                transactionResponse.tran_date, transactionResponse.card, transactionResponse.bonus,
                                transactionResponse.status,  TYPE_MALE_PAYMENT,
                                stan, transactionResponse.respCode, batchId, transactionResponse.receiptId);
                        generalModelAccumulationBonus.save();



                        EventBus.getDefault().post(new MessageEvent(mPrinterInteractor, transactionResponse,TYPE_MALE_PAYMENT ));

                        mPrinterInteractor.printMakePayment(transactionResponse, new PrinterInteractor.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailed() {

                            }
                        });
                    }

                });
    }

    @Override
    public void onCheckListReward(String card) {
        startCheckRewards(card);
    }

    private void startCheckRewards(String card){
        mGetRewardListInteractor.getRewardList(card, "CardScanEvant", new GetRewardListInteractor.Callback(this) {
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

                if(rewardList.size()!=0) {
                    // open AlerDialog for confrim to Reward List screen
                    getView().onClickr4wardRealize(rewardResponse.fullName + "", rewardResponse.card, mContext);
                }



            }
        });
    }

}