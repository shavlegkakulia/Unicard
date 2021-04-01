package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface CloseDayInteractor {

    abstract class CloseDayCallback extends ActionHandler {

        public CloseDayCallback(BasePresenter presenter) {
            super(presenter);
        }

        public abstract void onSuccess();
    }

    abstract class DailyStatusCallback  {
        public abstract void onSuccess(int totalTransactions,
                                       int canceledTransactions,
                                       int successTransactions);
    }

    void getDailyStatus(@NonNull final DailyStatusCallback callback);

    void closeDay(@NonNull CloseDayCallback callback);
}
