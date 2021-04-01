package ge.unicard.pos.ui.base;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ge.unicard.pos.R;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.ui.widgets.CImageButton;
import ge.unicard.pos.ui.widgets.CTextView;

/**
 * Created by Akaki on 10/23/18.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;
    public  static String devID;



    @BindView(R.id.toolbar_title_view)
    @Nullable
    CTextView toolbarTitle;

    @BindView(R.id.toolbar_action_btn1)
    @Nullable
    CImageButton toolbarActionButton1;

    @BindView(R.id.toolbar_action_btn2)
    @Nullable
    CImageButton toolbarActionButton2;

    protected final View bindView(@NonNull LayoutInflater inflater,
                                  @LayoutRes int layoutResId,
                                  boolean scrollable,
                                  @Nullable ToolbarInfo toolbarInfo) {
        final View rootView = inflater.inflate(R.layout.base_fragment, null);

        if (toolbarInfo != null) {
            ((ViewStub) rootView.findViewById(R.id.toolbar_stub)).inflate();
        }

        final ViewStub contentStub = rootView.findViewById(R.id.content_stub);
        contentStub.setLayoutResource(scrollable
                ? R.layout.scrollable_content_layout
                : R.layout.content_layout);
        final ViewGroup contentView = contentStub.inflate().findViewById(R.id.content_view);
        inflater.inflate(layoutResId, contentView);

        mUnbinder = ButterKnife.bind(this, rootView);

        if (toolbarInfo != null) {
            toolbarTitle.setText(toolbarInfo.getTitle().title);
            toolbarTitle.setOnClickListener(toolbarInfo.getTitle().onClickListener);
            toolbarTitle.setGravity(toolbarInfo.getTitleGravity());
            final ToolbarInfo.ActionButton btn1 = toolbarInfo.getActionButton1();
            if (btn1 != null) {
                toolbarActionButton1.setImageResource(btn1.iconResId);
                toolbarActionButton1.setOnClickListener(btn1.onClickListener);
            } else {
                toolbarActionButton1.setVisibility(View.INVISIBLE);
            }

            final ToolbarInfo.ActionButton btn2 = toolbarInfo.getActionButton2();
            if (btn2 != null) {
                toolbarActionButton2.setImageResource(btn2.iconResId);
                toolbarActionButton2.setOnClickListener(btn2.onClickListener);
            } else {
                toolbarActionButton2.setVisibility(View.INVISIBLE);
            }
        }
        return rootView;
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


}
