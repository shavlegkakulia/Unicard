package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ge.unicard.pos.interactors.GetBalanceInteractor;
import ge.unicard.pos.interactors.RewardRealizeInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.base.BaseResponse;
import ge.unicard.pos.networking.messaging.reward_realize.RewardRealizeMapper;
import ge.unicard.pos.networking.messaging.reward_realize.RewardRealizeResponse;
import ge.unicard.pos.networking.messaging.reward_realize.RewardRealizerequest;
import ge.unicard.pos.utils.DeviceInfo2;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class RewardRealizeInteractorImpl
        extends ApiServicesInteractor
        implements RewardRealizeInteractor
{
    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public RewardRealizeInteractorImpl(ApiServices services,
                              DeviceInfo2 deviceInfo){
        super(services);
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void RewardRealize(@NonNull String RewardCode, @NonNull Callback callback) {
        final RewardRealizerequest request = new RewardRealizerequest();

        if(!testDeviceID.isEmpty()) {
            request.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            request.deviceId = mDeviceInfo.getDeviceId();
        }
        request.reward_code = RewardCode;

        request.lang = ApiConstants.LANG_KA;
        request.appSource = ApiConstants.SOURCE_MOBAPP;

        getServices().RewardRealizeServ (request).enqueue(
                new GeneralApiCallback<RewardRealizeResponse, BaseResponse,
                        RewardRealizeMapper>(callback, new RewardRealizeMapper()) {

                    @Override
                    public void onMappingSuccess(BaseResponse result) {
                        callback.onRewardRealize(result.resultCode,
                                result.displayText,
                                result.errorMessage  );
                    }

                    @Override
                    public void onFailure(@Nullable RewardRealizeResponse result, int reason, @Nullable String description) {
                        callback.onMessageError(result.displayText, result.resultCode);
                        super.onFailure(result, reason, description);
                    }


                });
    }
}
