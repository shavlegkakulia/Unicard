package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ge.unicard.pos.bus.MessageEvent;
import ge.unicard.pos.bus.MessageReward;
import ge.unicard.pos.db.CodeTransactions;
import ge.unicard.pos.db.CountStatusTransactionDB;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.db.TransactionPrintDB;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.BonusAccumulationInteractor;
import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.CheckPhoneInteractor;
import ge.unicard.pos.interactors.GetAccountInfoInteractor;
import ge.unicard.pos.interactors.GetRewardListInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.RewardRealizeInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.networking.messaging.base.RewardResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.get_reward_list.Reward;
import ge.unicard.pos.presentation.reward.list_rewards.RewardsListActivity;
import static ge.unicard.pos.presentation.cards.RewardEnterPresenterImpl.rewardList;


import static ge.unicard.pos.db.CodeTransactions.TYPE_BONUS_ACCUMULATION;
import static ge.unicard.pos.presentation.launcher.LauncherActivity.activity;

/**
 * Created by Akaki on 10/31/18.
 */

public class BonusAccumulationPresenterImpl extends CardsPresenter
        implements BonusAccumulationPresenter {

    private final GetAccountInfoInteractor mGetAccountInfoInteractor;
    private final BonusAccumulationInteractor mBonusAccumulationInteractor;
    private final PrinterInteractor mPrinterInteractor;


    private final CheckPhoneInteractor mCheckPhoneInteractor;

    private final Context mContext;

    List<CountStatusTransactionDB> dataCountTransaction;
    // request get list Rewards
    private final GetRewardListInteractor mGetRewardListInteractor;
    // for local DB
    public GeneralModel generalModelAccumulationBonus;
    //public CountStatusTransactionDB countStatusTransactionDB;

    EventBus eventBus;


    @Inject
    public BonusAccumulationPresenterImpl(CardSearchInteractor cardSearchInteractor,
                                          QrScannerInteractor qrScannerInteractor,
                                          SubmitPhoneInteractor submitPhoneInteractor,
                                          GetAccountInfoInteractor getAccountInfoInteractor,
                                          GetRewardListInteractor getRewardListInteractor,
                                          BonusAccumulationInteractor bonusAccumulationInteractor,
                                          PrinterInteractor printerInteractor,
                                          CheckPhoneInteractor checkPhoneInteractor,
                                          @ApplicationContext Context context) {
        super(cardSearchInteractor, qrScannerInteractor,submitPhoneInteractor);
        mGetAccountInfoInteractor = getAccountInfoInteractor;
        mBonusAccumulationInteractor = bonusAccumulationInteractor;
        mPrinterInteractor = printerInteractor;
        mCheckPhoneInteractor = checkPhoneInteractor;
        mGetRewardListInteractor = getRewardListInteractor;
        mContext= context;
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
        mGetAccountInfoInteractor.getAccountInfo(
                card,
                new GetAccountInfoInteractor.Callback(this) {
                    @Override
                    public void onAccountInfoReceived(String info,
                                                      boolean withError) {
                        if (getView() != null) {
                            getView().onShowStatus(info, withError);
                            getView().onEnableSubmitButton(!withError);
                            Log.d("101010101010101", info);
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
          eventBus = EventBus.getDefault();

    }

    @Override
    protected void onSubmit(String cardNo,
                            double amount) {


        mBonusAccumulationInteractor.bonusAccumulation(
                cardNo,
                amount,
                new BonusAccumulationInteractor.Callback(this) {
                    @Override
                    public void onStatusReceived(String status) {
                        if (getView() != null) {
                            getView().onShowStatus(status, false);
                        }
                    }

                    @Override
                    public void onTransactionSuccess(final TransactionResponse transactionResponse, String stan, String batchId) {
                        generalModelAccumulationBonus = new GeneralModel(transactionResponse.amount,
                                transactionResponse.tran_date, transactionResponse.card,
                                transactionResponse.AccumulatedBonus, transactionResponse.status,
                                TYPE_BONUS_ACCUMULATION, stan, transactionResponse.respCode, batchId, transactionResponse.receiptId);
                        generalModelAccumulationBonus.save();

                       // dataCountTransaction = CountStatusTransactionDB.listAll(CountStatusTransactionDB.class);
                      /* if(countStatusTransactionDB != null ){
                           for(int i=0;i<dataCountTransaction.size();i++){
                               if(dataCountTransaction.get(i).typeTransactionID == TYPE_BONUS_ACCUMULATION){

                               }
                           }
                           Log.d("countStatusTransactionDB", String.valueOf(dataCountTransaction));
                        }
                        else {
                            //countStatusTransactionDB = new CountStatusTransactionDB(TYPE_BONUS_ACCUMULATION,Double.parseDouble(transactionResponse.amount),
                             //       0, 1,0);
                            //countStatusTransactionDB.save();
                           Log.d("countStatusTransactionDB", "null");
                        } */



                        EventBus.getDefault().post(new MessageEvent(mPrinterInteractor, transactionResponse, TYPE_BONUS_ACCUMULATION));

                        mPrinterInteractor.print(transactionResponse,
                                new PrinterInteractor.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailed() {
                                    }
                                }
                        );
                    }
                });
    }

    @Override
    protected void onRealize(String rewardCode) {

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
