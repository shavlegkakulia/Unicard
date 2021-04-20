package ge.unicard.pos.interactors.base;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import ge.unicard.pos.interactors.GetDeviceInfoInteractor;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoMapper;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoRequest;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoResponce;


import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class GetDeviceInfoInteractorImpl extends ApiServicesInteractor implements GetDeviceInfoInteractor{

    @Inject
    public GetDeviceInfoInteractorImpl(ApiServices services) {
        super(services);
    }

    @Override
    public void getDeviceInfo(@NonNull String card, @NonNull Callback callback) {
        final GetDeviceInfoRequest request = new GetDeviceInfoRequest();
        request.deviceId = testDeviceID;

        getServices().getDeviceInfo(request)
                .enqueue(new GeneralApiCallback<GetDeviceInfoResponce,  String, GetDeviceInfoMapper>() {
            @Override
            public void onMappingSuccess(String result) {
                //callback.onStatusReceived(result);
                callback.onGetDeviceInfoReceived("200", false);
            }

            @Override
            public void onSuccess(@NonNull GetDeviceInfoResponce result) {
                super.onSuccess(result);

                    callback.onGetDeviceInfo(result, true);
                Log.d("ivanLOG", String.valueOf(result.resultCode));

            }
        });
    }
}
