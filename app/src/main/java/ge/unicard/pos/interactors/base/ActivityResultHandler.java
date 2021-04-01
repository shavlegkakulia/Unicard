package ge.unicard.pos.interactors.base;

import android.content.Intent;

import java.lang.ref.WeakReference;

import ge.unicard.pos.presentation.base.BasePresenter;

public abstract class ActivityResultHandler {

    private final WeakReference<BasePresenter> mPresenter;

    public ActivityResultHandler(BasePresenter presenter) {
        mPresenter = new WeakReference<>(presenter);
    }

    public abstract void onActivityResult(int resultCode,
                                          Intent intent);
}
