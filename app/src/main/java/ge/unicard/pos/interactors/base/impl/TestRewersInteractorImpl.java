package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.inject.Inject;

import ge.unicard.pos.interactors.TestRewersInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationMapper;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationRequest;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationResponse;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentMapper;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentRequest;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseMapper;
import ge.unicard.pos.networking.messaging.purchase.PurchaseRequest;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.utils.DateTime;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;


public class TestRewersInteractorImpl extends ApiServicesInteractor implements TestRewersInteractor {

    private final PosSession mPosSession;
    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public TestRewersInteractorImpl(ApiServices services, PosSession mPosSession, DeviceInfo2 mDeviceInfo) {
        super(services);
        this.mPosSession = mPosSession;
        this.mDeviceInfo = mDeviceInfo;
    }

    @Override
    public void rewersTest(RewersResponse rewersResponse, @NonNull Callback callback) {


        // TYPE_BONUS_ACCUMULATION
        if (rewersResponse.typeTransactionIDw == 0) {

            final BonusAccumulationRequest req = new BonusAccumulationRequest();


            //05/12/2018 13:35:19
            Log.d("tran_dateAccomulat", rewersResponse.tran_date);

            if(!testDeviceID.isEmpty()) {
                req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
            }
            else {
                req.deviceId = mDeviceInfo.getDeviceId();
            }
            req.amount = Double.parseDouble(rewersResponse.amount);
            req.tranDate = new DateTime();

            req.batchId = rewersResponse.batchID;
            req.card = rewersResponse.card;
            req.respcode = "400";
            req.stan = rewersResponse.stan;
            req.lang = ApiConstants.LANG_KA;
            req.appSource = ApiConstants.SOURCE_MOBAPP;

            getServices().rewersTest(req).enqueue(new GeneralApiCallback<BonusAccumulationResponse, String, BonusAccumulationMapper>(callback, new BonusAccumulationMapper(), new BonusAccumulationMapper()) {
                @Override
                public void onMappingSuccess(String result) {
                    callback.onStatusReceived(result);
                }

                @Override
                public void onSuccess(@NonNull BonusAccumulationResponse result) {
                    super.onSuccess(result);

                    //callback.onTransactionSuccess();
                }

                @Override
                public void onErrorMappingSuccess(String result) {
                    callback.onStatusReceived(result);
                }
            });

        } else if (rewersResponse.typeTransactionIDw == 1) {
            // TYPE_MALE_PAYMENT

            final MakePaymentRequest req = new MakePaymentRequest();

            if(!testDeviceID.isEmpty()) {
                req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
            }
            else {
                req.deviceId = mDeviceInfo.getDeviceId();
            }
            req.amount = Double.parseDouble(rewersResponse.amount);
            req.tranDate = new DateTime();
            Log.d("tran_datePayment", rewersResponse.tran_date);
            req.batchId = rewersResponse.batchID;
            req.card = rewersResponse.card;
            req.respcode = "400";
            req.stan = rewersResponse.stan;
            req.otp = "0";

            req.lang = ApiConstants.LANG_KA;
            req.appSource = ApiConstants.SOURCE_MOBAPP;


            getServices().makePayment(req).enqueue(
                    new GeneralApiCallback<MakePaymentResponse, String,
                            MakePaymentMapper>(callback, new MakePaymentMapper()) {

                        @Override
                        public void onMappingSuccess(String result) {
                            callback.onStatusReceived(result);
                        }

                        @Override
                        public void onSuccess(@NonNull MakePaymentResponse result) {
                            super.onSuccess(result);

                        }
                    });
        } else if (rewersResponse.typeTransactionIDw == 2) {
            // TYPE_PURCHASE
            final PurchaseRequest purchaseRequest = new PurchaseRequest();

            if(!testDeviceID.isEmpty()) {
                purchaseRequest.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
            }
            else {
                purchaseRequest.deviceId = mDeviceInfo.getDeviceId();
            }
            purchaseRequest.amount = Double.parseDouble(rewersResponse.amount);
            purchaseRequest.tranDate = new DateTime();
            Log.d("tran_datePurchase", rewersResponse.tran_date);
            purchaseRequest.batchId = rewersResponse.batchID;
            purchaseRequest.card = rewersResponse.card;
            purchaseRequest.respcode = "400";
            purchaseRequest.stan = rewersResponse.stan; //mPosSession.createStan();

            purchaseRequest.lang = ApiConstants.LANG_KA;
            purchaseRequest.appSource = ApiConstants.SOURCE_MOBAPP;

            getServices().purchase(purchaseRequest).enqueue(
                    new GeneralApiCallback<PurchaseResponse, String,
                            PurchaseMapper>(callback, new PurchaseMapper()) {

                        @Override
                        public void onMappingSuccess(String result) {
                            callback.onStatusReceived(result);
                        }

                        @Override
                        public void onSuccess(@NonNull PurchaseResponse result) {
                            super.onSuccess(result);
                            //callback.onTransactionSuccess(result, purchaseRequest.stan, purchaseRequest.batchId);
                        }

                    });
        }

    }
}
