package ge.unicard.pos.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ge.unicard.pos.R;

/**
 * Created by Akaki on 11/5/18.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private Unbinder mUnbinder;

    protected final View bindView(@NonNull LayoutInflater inflater,
                                  @LayoutRes int layoutResId) {
        final View rootView = inflater.inflate(R.layout.base_dialog_fragment, null);
        final ViewGroup contentView = rootView.findViewById(R.id.content_view);
        inflater.inflate(layoutResId, contentView);

        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(requireContext(), R.style.Dialog) {
            @Override
            public void onBackPressed() {
                onResult(Activity.RESULT_CANCELED, null);
            }
        };
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected final void onResult(int resultCode,
                                  @Nullable Intent data) {
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
        }
        dismissAllowingStateLoss();
    }
}
