package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.interactors.CheckPhoneInteractor;
import ge.unicard.pos.interactors.GetAccountInfoInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.check_phone.CheckPhoneMapper;
import ge.unicard.pos.networking.messaging.check_phone.CheckPhoneRequest;
import ge.unicard.pos.networking.messaging.check_phone.CheckPhoneResponse;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class CheckPhoneInteractorImpl  extends ApiServicesInteractor implements CheckPhoneInteractor {


    @Inject
    public CheckPhoneInteractorImpl(ApiServices services) {
        super(services);
    }

    @Override
    public void startCheckPhone(@NonNull String card,
                                @NonNull Callback callback) {

        final CheckPhoneRequest request = new CheckPhoneRequest();

        request.card = card;
        request.lang = ApiConstants.LANG_KA;
        request.appSource = ApiConstants.SOURCE_MOBAPP;
        request.deviceId = testDeviceID;

        getServices().checkPhone(request).enqueue(new GeneralApiCallback<CheckPhoneResponse, String, CheckPhoneMapper>(
                callback, new CheckPhoneMapper()) {

            @Override
            public void onMappingSuccess(String result) {
                callback.checkPhone(result, false);
                Log.d("chechPhone", result);
            }

            @Override
            public void onErrorMappingSuccess(String result) {
                callback.checkPhone(result, false);
                Log.d("chechPhone", result);
            }

            @Override
            public void onFailure(@Nullable CheckPhoneResponse result, int reason, @Nullable String description) {
               // callback.onStatusReceived(result.status);
                callback.checkPhone(String.valueOf(result.resultCode), false);
                super.onFailure(result, -111, description);
            }

        });

    }
}
