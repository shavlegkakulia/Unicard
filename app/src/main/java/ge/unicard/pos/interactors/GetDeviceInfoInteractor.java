package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoResponce;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface GetDeviceInfoInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onGetDeviceInfoReceived(String info,
                                                   boolean withError);

        public abstract void onGetDeviceInfo(GetDeviceInfoResponce status,
                                               boolean withError);

    }

    void getDeviceInfo(@NonNull String card,
                    @NonNull Callback callback);
}
