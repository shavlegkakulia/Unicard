package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import ge.unicard.pos.interactors.SendOtpInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.send_otp.SendOtpMapper;
import ge.unicard.pos.networking.messaging.send_otp.SendOtpRequest;
import ge.unicard.pos.networking.messaging.send_otp.SendOtpResponse;
import ge.unicard.pos.utils.DeviceInfo2;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class SendOtpInteractorImpl
        extends ApiServicesInteractor
        implements SendOtpInteractor {

    private final PosSession mPosSession;
    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public SendOtpInteractorImpl(ApiServices services,
                                 PosSession posSession,
                                 DeviceInfo2 deviceInfo) {
        super(services);
        mPosSession = posSession;
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void sendOtp(final String card,
                        @NonNull final Callback callback) {

        final String stan = mPosSession.createStan();

        final SendOtpRequest req = new SendOtpRequest();
        if(!testDeviceID.isEmpty()) {
            req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            req.deviceId = mDeviceInfo.getDeviceId();
        }
        req.card = card;
        req.stan = stan;
        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;

        Gson gson = new Gson();

        Log.d("request_otp",gson.toJson(req));

        getServices().sendOtp(req).enqueue(
                new GeneralApiCallback<SendOtpResponse, String,
                        SendOtpMapper>(callback, new SendOtpMapper()) {

                    @Override
                    public void onMappingSuccess(String result) {

                        callback.onSuccess(stan);
                    }
                });
    }
}