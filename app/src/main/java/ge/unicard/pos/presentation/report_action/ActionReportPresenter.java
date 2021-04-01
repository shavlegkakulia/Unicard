package ge.unicard.pos.presentation.report_action;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.interactors.PrintReportInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.interactors.TestRewersInteractor;
import ge.unicard.pos.interactors.base.impl.PrintReportInteractorImpl;
import ge.unicard.pos.model.CustomCallback;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.presentation.base.BasePresenterImpl;

import static ge.unicard.pos.db.CodeTransactions.TYPE_MALE_PAYMENT;
import static ge.unicard.pos.db.CodeTransactions.TYPE_PURCHASE;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.addressReward;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchName;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.terminalID;

public class ActionReportPresenter extends BasePresenterImpl<ActionReportContract.View>
        implements ActionReportContract.Presenter {

    private final PrintReportInteractorImpl mPrintReportInteractor;
    private final TestRewersInteractor mTestRewersInteractor;
    private final PrinterInteractor mPrinterInteractor;

    @Inject
    public ActionReportPresenter(PrintReportInteractorImpl printerInteractor,
                                 TestRewersInteractor testRewersInteractor,
                                 PrinterInteractor PrinterInteractor){
        mPrintReportInteractor = printerInteractor;
        mTestRewersInteractor = testRewersInteractor;
        mPrinterInteractor = PrinterInteractor;
    }


    @Override
    public void onSubmitPrinter() {
        mPrintReportInteractor.printReport(new PrintReportInteractor.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    public void onRevers(RewersResponse rewersResponse, CustomCallback customCallback) {
        mTestRewersInteractor.rewersTest(rewersResponse, new TestRewersInteractor.Callback(this) {
            @Override
            public void onStatusReceived(String status) {
                //customCallback.callThis(status);
                customCallback.callThis("ტრანზაქცია წარმატებით გაუქმდა ");
                mPrinterInteractor.printRewers(rewersResponse, new PrinterInteractor.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed() {

                    }
                });
            }

            @Override
            public void onTransactionSuccess() {

            }

            @Override
            public void onAnErrorOccurred(String errorMgs) {
                customCallback.callThis(errorMgs);
            }
        });
    }

    @Override
    public void onPrintAgain(GeneralModel generalModel) {
        // print for revers transaction
        if(generalModel.getStatus().equals("Reversed")){
            RewersResponse rewersResponse = new RewersResponse();

            rewersResponse.stan = generalModel.getStan();
            rewersResponse.respCode = generalModel.getResponseCode();
            rewersResponse.card = generalModel.getCard();
            rewersResponse.bonus = generalModel.getBonus();
            rewersResponse.batchID = generalModel.getBatchID();
            rewersResponse.amount = generalModel.getAmount();
            rewersResponse.typeTransactionIDw = generalModel.getType();
            rewersResponse.tran_date = generalModel.getTranDate();
            rewersResponse.status = generalModel.getStatus();
            rewersResponse.receiptId = generalModel.getReceiptId();

            mPrinterInteractor.printRewers(rewersResponse, new PrinterInteractor.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailed() {

                }
            });

            // Log.d("onPrintAgainonPrint",String.valueOf(generalModel.type));
        }
        else {
            if(generalModel.type == TYPE_PURCHASE){
                PurchaseResponse purchaseResponse = new PurchaseResponse();

                purchaseResponse.stan = generalModel.getStan();
                purchaseResponse.respCode = generalModel.getResponseCode();
                purchaseResponse.card = generalModel.getCard();
                purchaseResponse.address = addressReward;
                purchaseResponse.merchId = merchID;
                purchaseResponse.merchName = merchName;
                purchaseResponse.terminalId = terminalID;
                purchaseResponse.amount = generalModel.getAmount();
                purchaseResponse.tran_type = "Purchase";
                purchaseResponse.tranDate = generalModel.getTranDate();
                purchaseResponse.status = generalModel.getStatus();
                purchaseResponse.receiptId = generalModel.getReceiptId();

                mPrinterInteractor.printPurchase(purchaseResponse, new PrinterInteractor.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed() {

                    }
                });
            }
            else if(generalModel.type == TYPE_MALE_PAYMENT) {
                // MAKE PAYMENT
                TransactionResponse transactionMakePayment = new TransactionResponse();

                transactionMakePayment.stan = generalModel.getStan();
                transactionMakePayment.respCode = generalModel.getResponseCode();
                transactionMakePayment.card = generalModel.getCard();
                transactionMakePayment.address = addressReward;
                transactionMakePayment.merchId = merchID;
                transactionMakePayment.merchName = merchName;
                transactionMakePayment.terminalId = terminalID;
                transactionMakePayment.amount = generalModel.getAmount();
                transactionMakePayment.tran_type = "Purchase";
                transactionMakePayment.tran_date = generalModel.getTranDate();
                transactionMakePayment.bonus = generalModel.bonus;
                transactionMakePayment.status = generalModel.getStatus();
                transactionMakePayment.receiptId = generalModel.getReceiptId();

                mPrinterInteractor.printMakePayment(transactionMakePayment, new PrinterInteractor.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed() {

                    }
                });
            }
            else {
                // BONUS ACCOMULATED
                TransactionResponse transactionBonusAccomulated = new TransactionResponse();


                transactionBonusAccomulated.stan = generalModel.getStan();
                transactionBonusAccomulated.respCode = generalModel.getResponseCode();
                transactionBonusAccomulated.card = generalModel.getCard();
                transactionBonusAccomulated.address = addressReward;
                transactionBonusAccomulated.merchId = merchID;
                transactionBonusAccomulated.merchName = merchName;
                transactionBonusAccomulated.terminalId = terminalID;
                transactionBonusAccomulated.amount = generalModel.getAmount();
                transactionBonusAccomulated.tran_type = "Bonus Accomulated";
                transactionBonusAccomulated.tran_date = generalModel.getTranDate();
                transactionBonusAccomulated.AccumulatedBonus = generalModel.bonus;
                transactionBonusAccomulated.status = generalModel.getStatus();
                transactionBonusAccomulated.receiptId = generalModel.getReceiptId();

                mPrinterInteractor.print(transactionBonusAccomulated, new PrinterInteractor.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed() {

                    }
                });
            }
            Log.d("onPrintAgainonPrint",String.valueOf(generalModel.type));
        }

    }
}
