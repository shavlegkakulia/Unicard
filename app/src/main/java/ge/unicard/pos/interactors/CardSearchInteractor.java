package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

public interface CardSearchInteractor {

    void starSearch(@NonNull Callback callback);

    void finishSearch();

    abstract class Callback {

        public abstract void onResult(String data) ;

        public void onSearchStarted() {
        }

        public void onScannerTimeout() {
        }

        public void onCustomerExit() {
        }

        public void onFailed() {
        }

        public void onSwipeAgain() {
        }

        public void onTooManyCards() {
        }
    }
}
