package ge.unicard.pos.ui.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ge.unicard.pos.R;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.model.ActionItem;
import ge.unicard.pos.ui.adapters.ActionsAdapter;
import ge.unicard.pos.ui.base.BaseActivity;
import ge.unicard.pos.ui.base.BaseFragment;
import ge.unicard.pos.utils.CollectionUtils;

/**
 * Created by Akaki on 10/30/18.
 */

public class ActionListActivity extends BaseActivity {

    static final String TAG = "ActionListActivity";

    static int optionForImage = 0;
    static final String EXTRA_ACTION_ITEMS = "action_items";
    static final String EXTRA_ACTION_ITEM = "action_item";
    static final String EXTRA_THEME = "theme";

    public static Intent buildIntent(@NonNull Context ctx,
                                     @NonNull List<ActionItem> actionItems,
                                     @StyleRes int themeResId) {
        return new Intent(ctx, ActionListActivity.class)
                .putParcelableArrayListExtra(EXTRA_ACTION_ITEMS, CollectionUtils.asArrayList(actionItems))
                .putExtra(EXTRA_THEME, themeResId);
    }

    public static Intent buidIntentDefaultPage(@NonNull Context ctx, @NonNull ActionItem actionItem, @StyleRes int themeResId){
        return new Intent(ctx, ActionListActivity.class)
                .putExtra(EXTRA_ACTION_ITEM, actionItem)
                .putExtra(EXTRA_THEME, themeResId);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().hasExtra(EXTRA_ACTION_ITEMS)) {
            Log.w(TAG, "Action items not found, finish");
            finish();
        } else {
            final int theme = getIntent().getIntExtra(EXTRA_THEME, R.style.PageTheme);
            optionForImage = getIntent().getIntExtra(EXTRA_THEME, R.style.PageTheme);
            setTheme(theme);
            loadFragment(new InternalFragment());
        }
    }

    @SuppressWarnings("unchecked")
    List<ActionItem> getItems() {
        try {
            return getIntent().getParcelableArrayListExtra(EXTRA_ACTION_ITEMS);
        } catch (Exception e) {
            Log.e(TAG, "Error while getting action items");
        }
        return Collections.emptyList();
    }

    void onItemClicked(ActionItem item) {
        if (item.getChildItems() != null) {
            startActivity(ActionListActivity.buildIntent(this, item.getChildItems(),
                    item.getThemeResId()));
           // Log.d("onItemClicked", "test");
        } else {
            try {
                startActivity(item.getIntent());
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Activity not found", e);
            } catch (Exception e) {
                Log.e(TAG, "Couldn't start activity", e);
            }
        }
    }

    public static class InternalFragment extends BaseFragment {

        @Nullable
        WeakReference<ActionListActivity> activityRef;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            if (context instanceof ActionListActivity) {
                activityRef = new WeakReference<>((ActionListActivity) context);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            if(optionForImage == R.style.PageTheme_DarkGreen) {

                return bindView(inflater, R.layout.action_list_internal_fragment, false,
                        new ToolbarInfo.Builder()
                                .setTitle(new ToolbarInfo.ActionTitle(getString(R.string.unicard) , new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }))
                                .setTitleGravity(Gravity.START)
                                .setActionButton1(new ToolbarInfo.ActionButton(
                                        R.drawable.ic_back,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (activityRef != null
                                                        && activityRef.get() != null
                                                        && !activityRef.get().isFinishing()) {
                                                    activityRef.get().finish();
                                                }
                                            }
                                        }))
                                .setActionButton2(new ToolbarInfo.ActionButton(R.mipmap.logo, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }))
                                .build());
            }
            else {
                return bindView(inflater, R.layout.action_list_internal_fragment, false,
                        new ToolbarInfo.Builder()
                                .setTitle(new ToolbarInfo.ActionTitle("Gift Card" , new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }))
                                .setTitleGravity(Gravity.START)
                                .setActionButton1(new ToolbarInfo.ActionButton(
                                        R.drawable.ic_back,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (activityRef != null
                                                        && activityRef.get() != null
                                                        && !activityRef.get().isFinishing()) {
                                                    activityRef.get().finish();
                                                }
                                            }
                                        }))
                                .setActionButton2(new ToolbarInfo.ActionButton(R.mipmap.tm, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }))
                                .build());
            }

        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            final Fragment fr = getChildFragmentManager()
                    .findFragmentById(R.id.action_list_fragment);
            if (fr instanceof ActionListFragment
                    && activityRef != null
                    && activityRef.get() != null) {
                ((ActionListFragment) fr).setAdapter(new ActionsAdapter(requireContext(),
                        activityRef.get().getItems()) {
                    @Override
                    protected void onItemClick(ActionItem item) {
                        if (activityRef != null && activityRef.get() != null) {
                            activityRef.get().onItemClicked(item);
                        }
                    }
                });
            }
        }
    }
}
