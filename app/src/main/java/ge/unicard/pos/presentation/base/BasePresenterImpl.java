package ge.unicard.pos.presentation.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Akaki on 10/23/18.
 */
public class BasePresenterImpl<V extends BaseView>
        implements BasePresenter<V> {

    private V mView;

    @Override
    public void onAttachView(@NonNull V view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Nullable
    @Override
    public V getView() {
        return mView;
    }
}
