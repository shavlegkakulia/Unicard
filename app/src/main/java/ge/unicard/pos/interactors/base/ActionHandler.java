package ge.unicard.pos.interactors.base;

import android.util.Log;

import java.lang.ref.WeakReference;

import ge.unicard.pos.presentation.base.BasePresenter;

public class ActionHandler {

    private final WeakReference<BasePresenter> mPresenter;

    public ActionHandler(BasePresenter presenter) {
        mPresenter = new WeakReference<>(presenter);
        onStarted();
    }

    public void onStarted() {
        final BasePresenter p = mPresenter.get();
        if (p != null && p.getView() != null) {
            p.getView().onShowLoader();
        }
    }

    public void onFinished() {
        final BasePresenter p = mPresenter.get();
        if (p != null && p.getView() != null) {
            p.getView().onShowContent();
        }
    }

    public void onNoInternetConnection() {
        final BasePresenter p = mPresenter.get();
        if (p != null && p.getView() != null) {
            p.getView().onShowNoInternetConnectionMessage();
        }
    }

    public void onAnErrorOccurred(String errorMgs) {
        final BasePresenter p = mPresenter.get();
        if (p != null && p.getView() != null) {
            p.getView().onShowAnErrorOccurredMessage(errorMgs);
        }
    }
}
