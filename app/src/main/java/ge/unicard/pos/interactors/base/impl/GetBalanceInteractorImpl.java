package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ge.unicard.pos.interactors.GetBalanceInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.interactors.GetBalanceInteractor;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.get_balance.Balance;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceMapper;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceRequest;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceResponse;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class GetBalanceInteractorImpl
        extends ApiServicesInteractor
        implements GetBalanceInteractor {

    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public GetBalanceInteractorImpl(ApiServices services,
                                    DeviceInfo2 deviceInfo) {
        super(services);
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void getBalance(@NonNull String card,
                           @NonNull final Callback callback) {
        final GetBalanceRequest req = new GetBalanceRequest();

        if(!testDeviceID.isEmpty()) {
            req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            req.deviceId = mDeviceInfo.getDeviceId();
        }
        req.card = card;
        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;

        getServices().getBalance(req).enqueue(
                new GeneralApiCallback<GetBalanceResponse, Balance, GetBalanceMapper>(
                        callback,
                        new GetBalanceMapper()) {

                    @Override
                    public void onMappingSuccess(Balance result) {
                        callback.onBalanceReceived( String.valueOf(result.getAmount()),
                                String.valueOf(result.getAmount()),
                                false);


                    }
                });
    }
}