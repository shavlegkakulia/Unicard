package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.interactors.CheckPhoneInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.submit_phone.SubmitPhoneMapper;
import ge.unicard.pos.networking.messaging.submit_phone.SubmitPhoneRequest;
import ge.unicard.pos.networking.messaging.submit_phone.SubmitPhoneResponse;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class SubmitPhoneInteractorImpl  extends ApiServicesInteractor implements SubmitPhoneInteractor {

    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public SubmitPhoneInteractorImpl(ApiServices services, DeviceInfo2 deviceInfo) {
        super(services);
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void startSubmitPhone(@NonNull String card, String phone, @NonNull SubmitPhoneInteractor.Callback callback) {
        final SubmitPhoneRequest request = new SubmitPhoneRequest();
        request.card = card;
        request.lang = ApiConstants.LANG_KA;
        request.phone = phone;
        request.appSource = ApiConstants.SOURCE_MOBAPP;
        if(!testDeviceID.isEmpty()) {
            request.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            request.deviceId = mDeviceInfo.getDeviceId();
        }

        getServices().submitPhone(request).enqueue(new GeneralApiCallback<SubmitPhoneResponse, String,SubmitPhoneMapper>(
                callback,new SubmitPhoneMapper()) {

            @Override
            public void onMappingSuccess(String result) {
               callback.submitPhone(result,false);
               // Log.d("chechPhone", result);
            }

        });
    }


}
