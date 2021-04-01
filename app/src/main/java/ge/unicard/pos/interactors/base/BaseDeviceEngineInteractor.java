package ge.unicard.pos.interactors.base;

import android.os.Handler;
import android.os.Looper;

import com.nexgo.oaf.apiv3.DeviceEngine;

import javax.inject.Inject;

public abstract class BaseDeviceEngineInteractor {

    private final DeviceEngine mDeviceEngine;

    private final Handler mUiThreadHandler =new Handler(Looper.getMainLooper());

    public BaseDeviceEngineInteractor(DeviceEngine deviceEngine) {
        mDeviceEngine = deviceEngine;
    }

    protected final DeviceEngine getDeviceEngine() {
        return mDeviceEngine;
    }

    protected final void runOnUiThread(Runnable runnable){
        mUiThreadHandler.post(runnable);
    }
}
