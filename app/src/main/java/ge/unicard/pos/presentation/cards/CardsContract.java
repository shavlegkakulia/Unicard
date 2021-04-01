package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

/**
 * Created by Akaki on 10/31/18.
 */
public final class CardsContract {

    public static final String EXTRA_MODE = "mode";

    public interface View extends BaseView {

        void openSendOtpPage(String cardNo);

        void showSwipeAgainMessage();

        void showTooManyCardsMessage();

        void showScannerTimeOutMessage();

        void showCustomerExitOutMessage();

        void showScanCardFailedMessage();

        void onFillCardInput(String data);

        void showPromptSwipeCardMessage();

        void onShowStatus(String status,
                          boolean withError);

        void onShowGetInfo(AccountInfoResponse accountInfoResponse,
                          boolean withError);

        void onShowAmountFieldIsEmptyMessage();

        void onShowCardFieldIsEmptyMessage();

        void onEnableSubmitButton(boolean enabled);

        void onCheckPhomeDialog(String codeRequest, String card);

        void onStartDialogRewards(String card);

        void onClickr4wardRealize(String name, String card, Context context);
    }

    public interface Presenter extends BasePresenter<View> {

        void onSubmitPhone(String phoneNumber, String card);
        void onCheckListReward(String card);

        void onScanCardButtonClicked();

        void onScanQrButtonClicked();

        void onSubmitButtonClicked(String cardStr,
                                   String amountStr);

        void onOtpReceived(String stan,
                           String otp,
                           String cardStr,
                           String amountStr);

        void stopScan();
    }

    private CardsContract() {
    }
}
