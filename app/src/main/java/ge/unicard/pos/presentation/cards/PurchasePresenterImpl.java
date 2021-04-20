package ge.unicard.pos.presentation.cards;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import ge.unicard.pos.bus.MessageEvent;
import ge.unicard.pos.bus.MessageEventPurchase;
import ge.unicard.pos.db.CodeTransactions;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.GetBalanceInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.interactors.PurchaseInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;

public class PurchasePresenterImpl extends CardsPresenter
        implements PurchasePresenter {

    private final GetBalanceInteractor mGetBalanceInteractor;
    private final PurchaseInteractor mPurchaseInteractor;
    private final PrinterInteractor mPrinterInteractor;

    private GeneralModel generalModelAccumulationBonus;

    @Inject
    public PurchasePresenterImpl(CardSearchInteractor cardSearchInteractor,
                                 QrScannerInteractor qrScannerInteractor,
                                 SubmitPhoneInteractor  submitPhoneInteractor,
                                 GetBalanceInteractor getBalanceInteractor,
                                 PurchaseInteractor purchaseInteractor,
                                 PrinterInteractor printerInteractor) {
        super(cardSearchInteractor, qrScannerInteractor, submitPhoneInteractor);
        mGetBalanceInteractor = getBalanceInteractor;
        mPurchaseInteractor = purchaseInteractor;
        mPrinterInteractor = printerInteractor;
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
            getView().onEnableSubmitButton(false);}
    }

    @Override
    protected void onCardFound(String card, String status) {
        /*mGetBalanceInteractor.getBalance(card,
                new GetBalanceInteractor.Callback(this) {
                    @Override
                    public void onBalanceReceived(int balance, GetBalanceResponse status, boolean withError) {
                        if (getView() != null) {
                            getView().onShowStatus(status, withError);
                            getView().onEnableSubmitButton(!withError);
                        }
                    }
                });*/

        mGetBalanceInteractor.getBalance(card,
                new GetBalanceInteractor.Callback(this) {
                    @Override
                    public void onBalanceReceived(String balance, String status, boolean withError) {
                        if (getView() != null) {
                            getView().onShowStatus(status, withError);
                            getView().onEnableSubmitButton(!withError);
                        }
                    }
                });
    }


    @Override
    protected void onSubmit(String cardNo,
                            double amount) {
        mPurchaseInteractor.purchase(
                cardNo,
                amount,
                new PurchaseInteractor.Callback(this) {
                    @Override
                    public void onStatusReceived(String status) {
                        if (getView() != null) {
                            getView().onShowStatus(status, false);
                        }
                    }

                    @Override
                    public void onTransactionSuccess(PurchaseResponse transactionResponse, String stan, String batchId) {

                        generalModelAccumulationBonus = new GeneralModel(transactionResponse.amount,
                                transactionResponse.tranDate, transactionResponse.card, "0",
                                transactionResponse.status,  CodeTransactions.TYPE_PURCHASE,
                                stan, transactionResponse.respCode, batchId, transactionResponse.receiptId);

                        generalModelAccumulationBonus.save();

                      /*  Log.d("MakePaymentTestdb", "Amount: "+transactionResponse.amount + " Date: " +
                                transactionResponse.tranDate +" Card: "+  transactionResponse.card  +" Bonus: 0" +
                                " Status: " + transactionResponse.status + " Type: "+  String.valueOf(CodeTransactions.TYPE_MALE_PAYMENT) +" Stan "+
                                stan+" RespCode "+ transactionResponse.respCode+" BatchID "+ batchId); */

                        EventBus.getDefault().post(new MessageEventPurchase(mPrinterInteractor, transactionResponse));


                        mPrinterInteractor.printPurchase(transactionResponse,
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

}