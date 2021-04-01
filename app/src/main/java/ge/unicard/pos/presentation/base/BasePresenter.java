package ge.unicard.pos.presentation.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Akaki on 10/23/18.
 */
public interface BasePresenter<V extends BaseView> {

    void onAttachView(@NonNull V view);

    void onDetachView();

    @Nullable
    V getView();
}
