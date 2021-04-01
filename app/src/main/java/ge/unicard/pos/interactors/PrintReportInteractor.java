package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

public interface PrintReportInteractor {

    abstract class Callback{
        public abstract void onSuccess();
        public abstract void onFailed();
    }



    void printReport(@NonNull Callback callback);
}
