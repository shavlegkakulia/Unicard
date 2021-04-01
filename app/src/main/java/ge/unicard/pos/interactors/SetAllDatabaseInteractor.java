package ge.unicard.pos.interactors;

import android.support.annotation.NonNull;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.presentation.base.BasePresenter;

public interface SetAllDatabaseInteractor {

    abstract class Callback extends ActionHandler {

        public Callback(BasePresenter presenter) {
            super(presenter);
        }
    }

    void sendAllDatabase(String batchID,
                     String deviceID);
}
