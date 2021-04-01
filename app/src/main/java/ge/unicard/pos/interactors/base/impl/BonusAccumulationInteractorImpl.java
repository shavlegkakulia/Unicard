package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.interactors.BonusAccumulationInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationMapper;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationRequest;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationResponse;
import ge.unicard.pos.utils.DateTime;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class BonusAccumulationInteractorImpl
        extends ApiServicesInteractor
        implements BonusAccumulationInteractor {

    private final PosSession mPosSession;
    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public BonusAccumulationInteractorImpl(ApiServices services,
                                           PosSession posSession,
                                           DeviceInfo2 deviceInfo) {
        super(services);
        mPosSession = posSession;
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void bonusAccumulation(String card,
                                  double amount,
                                  @NonNull final Callback callback) {

        final BonusAccumulationRequest req = new BonusAccumulationRequest();



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
        req.stan = mPosSession.createStan();

        req.lang = ApiConstants.LANG_KA;
        req.appSource = ApiConstants.SOURCE_MOBAPP;



        getServices().bonusAccumulation(req).enqueue(
                new GeneralApiCallback<BonusAccumulationResponse, String,
                        BonusAccumulationMapper>(callback, new BonusAccumulationMapper()) {

                    @Override
                    public void onMappingSuccess(String result) {
                        callback.onStatusReceived(result);
                    }

                    @Override
                    public void onSuccess(@NonNull BonusAccumulationResponse result) {
                        super.onSuccess(result);
                        callback.onTransactionSuccess(result, req.stan, req.batchId);
                    }
                });
    }
}