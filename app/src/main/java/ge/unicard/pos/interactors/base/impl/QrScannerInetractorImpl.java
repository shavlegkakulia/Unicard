package ge.unicard.pos.interactors.base.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener;
import com.nexgo.oaf.apiv3.device.scanner.Scanner;
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity;

import javax.inject.Inject;

import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.base.BaseDeviceEngineInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;

public class QrScannerInetractorImpl
        extends BaseDeviceEngineInteractor
        implements QrScannerInteractor {

    private final Context mContext;

    @Inject
    public QrScannerInetractorImpl(@ApplicationContext Context context,
                                   DeviceEngine deviceEngine) {
        super(deviceEngine);
        mContext = context;
    }

    @Override
    public void starScan(@NonNull final Callback callback) {
        final Scanner scanner = getDeviceEngine().getScanner(mContext);
        final ScannerCfgEntity cfgEntity = new ScannerCfgEntity();
        cfgEntity.setAutoFocus(false);
        cfgEntity.setUsedFrontCcd(false);
        scanner.initScanner(cfgEntity, new OnScannerListener() {
            @Override
            public void onInitResult(int retCode) {
                if (retCode == SdkResult.Success) {
                    int result = scanner.startScan(60, new OnScannerListener() {
                        @Override
                        public void onInitResult(int retCode) {
                        }

                        @Override
                        public void onScannerResult(int retCode,
                                                    final String data) {
                            if (retCode == SdkResult.Success) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onResult(data);
                                    }
                                });
                            }
                        }
                    });
                    if (result != SdkResult.Success) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onStartFailed();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onInitializeFailed();
                        }
                    });
                }
            }

            @Override
            public void onScannerResult(int retCode,
                                        String data) {
            }
        });
    }
}
