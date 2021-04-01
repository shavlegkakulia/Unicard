package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.interactors.PurchaseInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseMapper;
import ge.unicard.pos.networking.messaging.purchase.PurchaseRequest;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.utils.DateTime;
import ge.unicard.pos.utils.DeviceInfo2;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class PurchaseInteractorImpl
        extends ApiServicesInteractor
        implements PurchaseInteractor {

    private final PosSession mPosSession;
    private final DeviceInfo2 mDeviceInfo;

    @Inject
    public PurchaseInteractorImpl(ApiServices services,
                                  PosSession posSession,
                                  DeviceInfo2 deviceInfo) {
        super(services);
        mPosSession = posSession;
        mDeviceInfo = deviceInfo;
    }

    @Override
    public void purchase(String card,
                         double amount,
                         @NonNull final Callback callback) {

        final PurchaseRequest req = new PurchaseRequest();

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

        getServices().purchase(req).enqueue(
                new GeneralApiCallback<PurchaseResponse, String,
                        PurchaseMapper>(callback, new PurchaseMapper()) {

                    @Override
                    public void onMappingSuccess(String result) {
                        callback.onStatusReceived(result);
                    }

                    @Override
                    public void onSuccess(@NonNull PurchaseResponse result) {
                        super.onSuccess(result);
                        callback.onTransactionSuccess(result, req.stan, req.batchId);
                    }

                });
    }
}