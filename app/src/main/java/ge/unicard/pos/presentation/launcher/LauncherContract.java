package ge.unicard.pos.presentation.launcher;

import ge.unicard.pos.model.CustomCallback;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationRequest;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoResponce;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;

/**
 * Created by Akaki on 10/23/18.
 */

public final class LauncherContract {

    public interface View extends BaseView {

        void onDeviceInfoView(GetDeviceInfoResponce responce);

        void onOpenConfigurationInfoPage();

        void onOpenBonusAccumulationOrMakePaymentPage();

        void onOpenBuyingAndBalancePage();

        void onOpenCampaignManagementPage();

        void onOpenCloseActionPage(int totalTransactions,
                                   int canceledTransactions,
                                   int successTransactions);

        void onOpenReportsActionPage();

        void onShowDayCloseSuccessMessage();
    }

    public interface Presenter extends BasePresenter<View> {

        void onGetDeviceInfo();

        void onInfoButtonClicked();

        void onBonusAccumulationOrMakePaymentButtonClick();

        void onBuyingAndBalanceActionButtonClick();

        void onCampaignManagementActionButtonClick();

        void onCloseActionButtonClick();

        void onCloseActionConfirmedButtonClick();


        void onReversActionMakePayment(RewersResponse transactionResponse);

        void onReversActionPurschase(RewersResponse purchaseResponse);

        void onReportsActionButtonClick();

        void onPrintReport();


    }

    private LauncherContract() {
    }
}
