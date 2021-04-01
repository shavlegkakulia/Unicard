package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

public interface QrScannerInteractor {

    void starScan(@NonNull Callback callback);

    abstract class Callback {

        public abstract void onResult(String data);

        public void onStartFailed() {
        }

        public void onInitializeFailed() {
        }
    }
}
