package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ge.unicard.pos.interactors.GetRewardListInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.get_reward_list.GetRewardListMapper;
import ge.unicard.pos.networking.messaging.get_reward_list.GetRewardListRequest;
import ge.unicard.pos.networking.messaging.get_reward_list.GetRewardListResponse;
import ge.unicard.pos.utils.DeviceInfo2;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class GetRewardListInteractorImpl  extends ApiServicesInteractor implements GetRewardListInteractor {

    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public GetRewardListInteractorImpl(ApiServices services,  DeviceInfo2 deviceInfo) {
        super(services);
        mDeviceInfo = deviceInfo;
    }


    @Override
    public void getRewardList(String card, String Source, @NonNull Callback callback) {
        final GetRewardListRequest req = new GetRewardListRequest();

        if(Source.equals("CardScanEvant")){
            if(!testDeviceID.isEmpty()) {
                req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
            }
            else {
                req.deviceId = mDeviceInfo.getDeviceId();
            }
        req.card = card;
        req.rewardBarCode = "";
        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;

        getServices().getRewardList(req).enqueue(
                new GeneralApiCallback<GetRewardListResponse, String,
                        GetRewardListMapper>(callback, new GetRewardListMapper()) {

                    @Override
                    public void onMappingSuccess(String result) {
                        callback.onStatusReceived(result);
                    }

                    @Override
                    public void onSuccess(@NonNull GetRewardListResponse result) {
                        super.onSuccess(result);
                        callback.onTransactionSuccess(result);
                    }
                });
    }
      else {
            if(!testDeviceID.isEmpty()) {
                req.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
            }
            else {
                req.deviceId = mDeviceInfo.getDeviceId();
            }
        req.card = "";
        req.rewardBarCode = card;
        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;

        getServices().getRewardList(req).enqueue(
                new GeneralApiCallback<GetRewardListResponse, String,
                        GetRewardListMapper>(callback, new GetRewardListMapper()) {

                    @Override
                    public void onMappingSuccess(String result) {
                        callback.onStatusReceived(result);
                    }

                    @Override
                    public void onSuccess(@NonNull GetRewardListResponse result) {
                        super.onSuccess(result);
                        callback.onTransactionSuccess(result);
                    }

                    @Override
                    public void onFailure(@Nullable GetRewardListResponse result, int reason, @Nullable String description) {
                        super.onFailure(result, reason, result.errorMessage);
                    }
                });
    }
    }

}
