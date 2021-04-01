package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.interactors.CloseDayInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.close_day.CloseDayMapper;
import ge.unicard.pos.networking.messaging.close_day.CloseDayRequest;
import ge.unicard.pos.networking.messaging.close_day.CloseDayResponse;
import ge.unicard.pos.utils.DateTime;
import ge.unicard.pos.utils.DeviceInfo2;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class CloseDayInteractorImpl  extends ApiServicesInteractor implements CloseDayInteractor {

    private final PosSession mPosSession;
    private final DeviceInfo2 mDeviceInfo;
    String date;
    int countTransactionBonusAccomulation = 0, countRewersBonusAccomulation =0;
    int countTransactionPurchase = 0, countRewersPurchase =0;
    int countTransactionMakePayment = 0, countRewersMakePayment =0;

    double amountBonusAccomulation = 0, reverstBonusAccomulation = 0;
    double amountPurchase = 0, reverstPurchase = 0;
    double amountMakePayment = 0, reverstMakePayment = 0;

    @Inject
    public CloseDayInteractorImpl(ApiServices services,
                                  PosSession posSession,
                                  DeviceInfo2 deviceInfo) {
        super(services);
        mPosSession = posSession;
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void getDailyStatus(@NonNull DailyStatusCallback callback) {
        //todo getdata from db
        callback.onSuccess(10,4,6);
    }

    @Override
    public void closeDay(@NonNull final CloseDayCallback callback) {

        List<GeneralModel> listCloseDay = GeneralModel.listAll(GeneralModel.class);
        Log.d("CloseDaygeneralModels", String.valueOf(listCloseDay.size()));

        for(int i =0;i< listCloseDay.size();i++){
            if(listCloseDay.get(i).type == 0) {
                // TYPE_BONUS_ACCUMULATION
                if (!listCloseDay.get(i).status.equals("Reversed")) {
                    countTransactionBonusAccomulation++;
                    amountBonusAccomulation += Double.parseDouble(listCloseDay.get(i).amount);
                } else if (listCloseDay.get(i).status.equals("Reversed")) {
                    countRewersBonusAccomulation++;
                    reverstBonusAccomulation += Double.parseDouble(listCloseDay.get(i).amount);
                }
            }
            else  if(listCloseDay.get(i).type == 1){
                // TYPE_MALE_PAYMENT
                if (!listCloseDay.get(i).status.equals("Reversed")) {
                    countTransactionMakePayment++;
                    amountMakePayment +=Double.parseDouble(listCloseDay.get(i).amount);
                }
                else if (listCloseDay.get(i).status.equals("Reversed")) {
                    countRewersMakePayment++;
                    reverstMakePayment +=Double.parseDouble(listCloseDay.get(i).amount);
                }
            }
            else  if(listCloseDay.get(i).type == 2 ){
                // TYPE_PURCHASE
                if (!listCloseDay.get(i).status.equals("Reversed")) {
                    countTransactionPurchase++;
                    amountPurchase +=Double.parseDouble(listCloseDay.get(i).amount);
                }
                else if (listCloseDay.get(i).status.equals("Reversed")) {
                    countRewersPurchase++;
                    reverstPurchase +=Double.parseDouble(listCloseDay.get(i).amount);
                }
            }
            else {

            }
        }

        final CloseDayRequest closeDayRequest = new CloseDayRequest();

         date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

       //closeDayRequest.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        if(!testDeviceID.isEmpty()) {
            closeDayRequest.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            closeDayRequest.deviceId = mDeviceInfo.getDeviceId();
        }

        closeDayRequest.batchDate = date;
        closeDayRequest.batchId = mPosSession.getOrCreateBatchId();

        closeDayRequest.accumulateAmount = amountBonusAccomulation;
        closeDayRequest.accumulateTrancount = countTransactionBonusAccomulation;
        closeDayRequest.accumulateAmountRevers = reverstBonusAccomulation;
        closeDayRequest.accumulateReverseCount = countRewersBonusAccomulation;

        closeDayRequest.payTranCount = countTransactionMakePayment;
        closeDayRequest.payAmount = amountMakePayment;
        closeDayRequest.payRevers = reverstMakePayment;
        closeDayRequest.payReverseCount = countRewersMakePayment;
        //closeDayRequest.appSource =  ApiConstants.SOURCE_MOBAPP;

        // for Purchase
         closeDayRequest.giftcardPayAmount = amountPurchase;
        closeDayRequest.giftcarPayRevers = reverstPurchase;
        closeDayRequest.giftcardPayReverseCount = countRewersPurchase;
        closeDayRequest.giftcardPayTranCount = countTransactionPurchase;

        closeDayRequest.lang = ApiConstants.LANG_KA;
        closeDayRequest.appSource = ApiConstants.SOURCE_MOBAPP;

        getServices().closeDay(closeDayRequest).enqueue(
                new GeneralApiCallback<CloseDayResponse, Boolean,
                                        CloseDayMapper>(callback, new CloseDayMapper()) {

                    @Override
                    public void onMappingSuccess(Boolean result) {
                        callback.onSuccess();
                    }
                });


        amountBonusAccomulation = 0;
        countTransactionBonusAccomulation = 0;
        reverstBonusAccomulation = 0;
        countRewersBonusAccomulation = 0;

        countTransactionMakePayment = 0;
        amountMakePayment = 0;
        reverstMakePayment = 0;
        countRewersMakePayment = 0;

        amountPurchase = 0;
        reverstPurchase = 0;
        countRewersPurchase = 0;
        countTransactionPurchase = 0;
    }
}
