package ge.unicard.pos.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.base.BasePresenter;
import ge.unicard.pos.presentation.base.BaseView;
import ge.unicard.pos.ui.common.MessageDialogFragment;
import ge.unicard.pos.ui.common.ProgressDialogFragment;

/**
 * Created by Akaki on 10/23/18.
 */
public abstract class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>>
        extends BaseFragment
        implements BaseView {

    private P mPresenter;

    private final Queue<Runnable> mExecuteOnResumeQueue = new ArrayDeque<>();

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = Objects.requireNonNull(instantiatePresenter(),
                "Presenter must be non-null");
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onAttachView((V) this);
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        mExecuteOnResumeQueue.clear();
        mPresenter.onDetachView();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();

        while (!mExecuteOnResumeQueue.isEmpty()) {
            mExecuteOnResumeQueue.remove().run();
        }
    }

    @NonNull
    protected final P getPresenter() {
        final P presenter = Objects.requireNonNull(mPresenter,
                "Presenter not initialized");
        return presenter;
    }

    @NonNull
    @Override
    public Bundle getArgs() {
        final Bundle args = getArguments();
        if (args != null) {
            return args;
        } else {
            return Bundle.EMPTY;
        }
    }

    @NonNull
    protected abstract P instantiatePresenter();

    @Override
    public void onShowLoader() {
        executeOnResume(new Runnable() {
            @Override
            public void run() {
                // animation refresh dialog
                final DialogFragment fr = ProgressDialogFragment.newInstance();
                fr.show(requireFragmentManager(), null);
            }
        });
    }

    @Override
    public void onShowContent() {
        final FragmentManager fm = requireFragmentManager();
        final List<Fragment> fs = fm.getFragments();
        for (Fragment f : fs) {
            if (f instanceof DialogFragment) {
                ((DialogFragment) f).dismissAllowingStateLoss();
            }
        }
    }

    @Override
    public void onShowNoInternetConnectionMessage() {
        executeOnResume(new Runnable() {
            @Override
            public void run() {
                final DialogFragment fr = MessageDialogFragment.newErrorMessageInstance(
                        getString(R.string.no_internet_connection_msg));
                fr.show(requireFragmentManager(), null);
            }
        });
    }

    @Override
    public void onShowAnErrorOccurredMessage(final String errorMsg) {
        executeOnResume(new Runnable() {
            @Override
            public void run() {
                final DialogFragment fr = MessageDialogFragment.newErrorMessageInstance(errorMsg);
                fr.show(requireFragmentManager(), null);
            }
        });
    }

    @Override
    public final void executeOnResume(Runnable runnable) {
        if (isResumed()) {
            runnable.run();
        } else {
            mExecuteOnResumeQueue.add(runnable);
        }
    }
}
