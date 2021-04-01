package ge.unicard.pos.presentation.cards;

import android.text.TextUtils;
import android.util.Log;

import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.presentation.base.BasePresenterImpl;

/**
 * Created by Akaki on 10/31/18.
 */

public abstract class CardsPresenter extends BasePresenterImpl<CardsContract.View>
        implements CardsContract.Presenter {

    private final CardSearchInteractor mCardSearchInteractor;
    private final QrScannerInteractor mQrScannerInteractor;
    private final SubmitPhoneInteractor mSubmitPhoneInteractor;

    public CardsPresenter(CardSearchInteractor cardSearchInteractor,
                          QrScannerInteractor qrScannerInteractor,
                          SubmitPhoneInteractor submitPhoneInteractor) {
        mCardSearchInteractor = cardSearchInteractor;
        mQrScannerInteractor = qrScannerInteractor;
        mSubmitPhoneInteractor = submitPhoneInteractor;
    }

    protected abstract void onCardFound(String card, String status);

    protected abstract void onSubmit(String cardNo,
                                     double amount);

    protected abstract void onRealize(String rewardCode);

    @Override
    public void onScanCardButtonClicked() {

        mCardSearchInteractor.starSearch(new CardSearchInteractor.Callback() {
            @Override
            public void onResult(String data) {
                if (getView() != null) {
                    getView().onFillCardInput(data);
                }
                onCardFound(data, "CardScanEvant");
            }

            @Override
            public void onSearchStarted() {
                if (getView() != null) {
                    getView().showPromptSwipeCardMessage();
                }
            }

            @Override
            public void onScannerTimeout() {
                if (getView() != null) {
                    getView().showCustomerExitOutMessage();
                }
            }

            @Override
            public void onCustomerExit() {
                if (getView() != null) {
                    getView().showCustomerExitOutMessage();
                }
            }

            @Override
            public void onFailed() {
                if (getView() != null) {
                    getView().showScanCardFailedMessage();
                }
            }

            @Override
            public void onSwipeAgain() {
                if (getView() != null) {
                    getView().showSwipeAgainMessage();
                }
            }

            @Override
            public void onTooManyCards() {
                if (getView() != null) {
                    getView().showTooManyCardsMessage();
                }
            }
        });
    }


    @Override
    public void onScanQrButtonClicked() {
        mQrScannerInteractor.starScan(new QrScannerInteractor.Callback() {
            @Override
            public void onResult(String data) {
                if (getView() != null) {
                    getView().onFillCardInput(data);
                }
                onCardFound(data, "test");
            }
        });
    }

    @Override
    public void onSubmitButtonClicked(String cardStr,
                                      String amountStr) {
        if (validateCard(cardStr) & validateAmount(amountStr)) {
            double amount;
            try {
                amount = Double.valueOf(amountStr);
            } catch (NumberFormatException e) {
                return;
            }
            onSubmit(cardStr, amount);
        }
    }

    @Override
    public void onSubmitPhone(String phoneNumber, String card){
        Log.d("phoneNumber", phoneNumber);
        if(!phoneNumber.equals("+") && !card.equals("+"))
        mSubmitPhoneInteractor.startSubmitPhone(card, phoneNumber, new SubmitPhoneInteractor.Callback(this) {
            @Override
            public void submitPhone(String code, boolean withError) {

            }
        });
    }

    @Override
    public void stopScan() {
        mCardSearchInteractor.finishSearch();
    }

    @Override
    public final void onOtpReceived(String stan,
                                    String otp,
                                    String cardStr,
                                    String amountStr) {

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {

            e.printStackTrace();
            return;
        }
        onOtpReceived(stan, otp, cardStr, amount);
    }


    protected void onOtpReceived(String stan,
                                 String otp,
                                 String cardNo,
                                 double amount) {
    }

    private boolean validateCard(String input) {
        if (TextUtils.isEmpty(input)) {
            if (getView() != null) {
                getView().onShowCardFieldIsEmptyMessage();
            }
            return false;
        } else return true;
    }

    private boolean validateAmount(String input) {
        if (TextUtils.isEmpty(input)) {
            if (getView() != null) {
                getView().onShowAmountFieldIsEmptyMessage();
            }
            return false;
        } else return true;
    }
}
