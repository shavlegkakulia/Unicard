package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import ge.unicard.pos.bus.MessageReward;
import ge.unicard.pos.interactors.GetAccountInfoInteractor;
import ge.unicard.pos.interactors.GetRewardListInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.networking.messaging.base.RewardResponse;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoMapper;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoRequest;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoResponse;
import ge.unicard.pos.utils.DeviceInfo2;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class GetAccountInfoInteractorImpl
        extends ApiServicesInteractor
        implements GetAccountInfoInteractor {

    private final DeviceInfo2 mDeviceInfo;




    @Inject
    public GetAccountInfoInteractorImpl(ApiServices services,
                                        DeviceInfo2 deviceInfo) {
        super(services);
        mDeviceInfo = deviceInfo;

    }

    @Override
    public void getAccountInfo(@NonNull String card,
                               @NonNull final Callback callback) {
        final GetAccountInfoRequest req = new GetAccountInfoRequest();


        if(!testDeviceID.isEmpty()) {
            req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            req.deviceId = mDeviceInfo.getDeviceId();
        }
        req.card = card;
        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;

        getServices().getAccountInfo(req).enqueue(
                new GeneralApiCallback<GetAccountInfoResponse, String, GetAccountInfoMapper>(
                        callback,
                        new GetAccountInfoMapper()) {

                    @Override
                    public void onMappingSuccess(String result) {



                       callback.onAccountInfoReceived(result, false);
                       Log.d("GetAccountInfoInteractorImpl", result);
                    }



                    @Override
                    public void onErrorMappingSuccess(String result) {
                        callback.onAccountInfoReceived(result, true);
                    }
                });


    }
}