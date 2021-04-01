package ge.unicard.pos.presentation.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Akaki on 10/23/18.
 */

public interface BaseView {

    void onShowContent();

    void onShowLoader();

    @NonNull
    Bundle getArgs();

    void executeOnResume(Runnable runnable);

    void onShowNoInternetConnectionMessage();

    void onShowAnErrorOccurredMessage(String errorMsg);
}
