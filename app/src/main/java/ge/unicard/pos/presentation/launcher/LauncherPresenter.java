package ge.unicard.pos.presentation.launcher;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.interactors.CloseDayInteractor;
import ge.unicard.pos.interactors.GetDeviceInfoInteractor;
import ge.unicard.pos.interactors.PrintReportInteractor;
import ge.unicard.pos.interactors.TestRewersInteractor;
import ge.unicard.pos.interactors.base.impl.PrintReportInteractorImpl;
import ge.unicard.pos.interactors.base.impl.SetAllDatabaseInteractorImpl;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.model.CustomCallback;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationMapper;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationRequest;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationResponse;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoResponce;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.presentation.base.BasePresenterImpl;
import ge.unicard.pos.utils.DateTime;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.ARG_DEFAULT_PAGE;

/**
 * Created by Akaki on 10/23/18.
 */

public class LauncherPresenter
        extends BasePresenterImpl<LauncherContract.View>
        implements LauncherContract.Presenter {

    private final CloseDayInteractor mCloseDayInteractor;
    private final TestRewersInteractor mTestRewersInteractor;
    private final GetDeviceInfoInteractor mGetDeviceInfoInteractor;
    private final PrintReportInteractorImpl mPrintReportInteractor;
    private final SetAllDatabaseInteractorImpl mSetAllDatabaseInteractor;
    private final PosSession posSession;

    public static String merchNamePaymentFotPrint = "";

    @Inject
    public LauncherPresenter(CloseDayInteractor closeDayInteractor, TestRewersInteractor testRewersInteractor
    , GetDeviceInfoInteractor getDeviceInfoInteractor, PrintReportInteractorImpl printReportInteractor,
                             SetAllDatabaseInteractorImpl setAllDatabaseInteractor ,PosSession mPosSession) {
        mCloseDayInteractor = closeDayInteractor;
        mTestRewersInteractor = testRewersInteractor;
        mGetDeviceInfoInteractor = getDeviceInfoInteractor;
        mPrintReportInteractor = printReportInteractor;
        mSetAllDatabaseInteractor = setAllDatabaseInteractor;
        posSession = mPosSession;
    }

    @Override
    public void onGetDeviceInfo() {
        mGetDeviceInfoInteractor.getDeviceInfo("", new GetDeviceInfoInteractor.Callback(this) {
            @Override
            public void onGetDeviceInfoReceived(String info, boolean withError) {
                Log.d("getResourceDescription", info);
            }
            @Override
            public void onGetDeviceInfo(GetDeviceInfoResponce responce, boolean withError) {
                Log.d("getResourceDescription", responce.getAPIVersion());
                ARG_DEFAULT_PAGE = responce.getDefaultPage();
                if(getView() != null) {
                    getView().onDeviceInfoView(responce);
                    merchNamePaymentFotPrint = responce.getMerchantName();
                }

            }
        });
    }

    @Override
    public void onInfoButtonClicked() {
        if (getView() != null) {
            getView().onOpenConfigurationInfoPage();
        }
    }

    @Override
    public void onBonusAccumulationOrMakePaymentButtonClick() {
        if (getView() != null) {
            getView().onOpenBonusAccumulationOrMakePaymentPage();
        }
    }

    @Override
    public void onBuyingAndBalanceActionButtonClick() {
        if (getView() != null) {
            getView().onOpenBuyingAndBalancePage();
        }
    }

    @Override
    public void onCampaignManagementActionButtonClick() {
        if (getView() != null) {
            getView().onOpenCampaignManagementPage();
        }
    }

    @Override
    public void onCloseActionButtonClick() {
        mCloseDayInteractor.getDailyStatus(new CloseDayInteractor.DailyStatusCallback() {
            @Override
            public void onSuccess(int totalTransactions,
                                  int canceledTransactions,
                                  int successTransactions) {
                if (getView() != null) {
                    getView().onOpenCloseActionPage(totalTransactions, canceledTransactions,
                            successTransactions);
                }
            }
        });


        //Log.d("onCloseActionButtonClick", "yes");
    }

    @Override
    public void onCloseActionConfirmedButtonClick() {
        mCloseDayInteractor.closeDay(new CloseDayInteractor.CloseDayCallback(this) {
            @Override
            public void onSuccess() {

                mSetAllDatabaseInteractor.sendAllDatabase(posSession.getOrCreateBatchId(), testDeviceID);
                if (getView() != null) {
                    getView().onShowDayCloseSuccessMessage();
                    posSession.clearBatchID();
                    posSession.getOrCreateBatchId();
                }
            }
        });

        //GeneralModel.delete(GeneralModel.class);
    }


    @Override
    public void onReversActionMakePayment(RewersResponse transactionResponse) {

    }

    @Override
    public void onReversActionPurschase(RewersResponse purchaseResponse) {

    }

  /*  @Override
    public void onReversAction(int typeTransaction) {

        Log.d("onReversAction", String.valueOf(typeTransaction));
    } */

    @Override
    public void onReportsActionButtonClick() {
        if (getView() != null) {
            getView().onOpenReportsActionPage();
        }
    }

    @Override
    public void onPrintReport() {
        mPrintReportInteractor.printReport(new PrintReportInteractor.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed() {

            }
        });
    }
}
