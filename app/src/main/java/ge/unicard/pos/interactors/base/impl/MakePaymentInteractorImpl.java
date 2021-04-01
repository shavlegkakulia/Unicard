package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import ge.unicard.pos.interactors.MakePaymentInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentMapper;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentRequest;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentResponse;
import ge.unicard.pos.utils.DateTime;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class MakePaymentInteractorImpl
        extends ApiServicesInteractor
        implements MakePaymentInteractor {

    private final PosSession mPosSession;
    private final DeviceInfo2 mDeviceInfo;



    @Inject
    public MakePaymentInteractorImpl(ApiServices services,
                                     PosSession posSession,
                                     DeviceInfo2 deviceInfo) {
        super(services);
        mPosSession = posSession;
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void makePayment(String card,
                            double amount,
                            String stan,
                            String otp,
                            @NonNull final Callback callback) {

        final MakePaymentRequest req = new MakePaymentRequest();

        if(!testDeviceID.isEmpty()) {
            req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            req.deviceId = mDeviceInfo.getDeviceId();
        }
        req.amount = amount;
        req.tranDate = new DateTime();
        req.batchId = mPosSession.getOrCreateBatchId();
        req.card = card;
        req.respcode = "000";
        req.stan = stan;
        req.otp  = otp;

        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;


        Gson gson = new Gson();

        Log.d("request_request",gson.toJson(req));
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
                        callback.onTransactionSuccess(result, req.batchId);
                    }

                    @Override
                    public void onFailure(@Nullable MakePaymentResponse result, int reason, @Nullable String description) {
                        callback.onStatusReceived(result.status);
                        super.onFailure(result, reason, description);
                    }
                });
    }
}