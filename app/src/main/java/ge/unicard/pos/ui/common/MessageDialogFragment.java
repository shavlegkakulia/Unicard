package ge.unicard.pos.ui.common;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import ge.unicard.pos.BuildConfig;
import ge.unicard.pos.R;
import ge.unicard.pos.presentation.other.UpdateAppFragment;
import ge.unicard.pos.ui.base.BaseDialogFragment;
import ge.unicard.pos.ui.widgets.CButton;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.OutlineProvider;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.activity;
import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.fragmentManager;

/**
 * Created by Akaki on 10/25/18.
 */

public class MessageDialogFragment extends BaseDialogFragment {

    static final String TYPE_EXTRA = "type";
    static final String MESSAGE_EXTRA = "message";

    static final int MESSAGE_TYPE_INFORMATION = 1;
    static final int MESSAGE_TYPE_ERROR = 2;

    @IntDef({MESSAGE_TYPE_INFORMATION, MESSAGE_TYPE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    public static MessageDialogFragment newErrorMessageInstance(String errorMsg) {
        final MessageDialogFragment fr = new MessageDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(TYPE_EXTRA, MESSAGE_TYPE_ERROR);
        args.putString(MESSAGE_EXTRA, errorMsg);
        fr.setArguments(args);
        return fr;
    }

    public static MessageDialogFragment newSuccessMessageInstance(String successMsg) {
        final MessageDialogFragment fr = new MessageDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(TYPE_EXTRA, MESSAGE_TYPE_INFORMATION);
        args.putString(MESSAGE_EXTRA, successMsg);
        fr.setArguments(args);
        return fr;
    }

    @BindView(R.id.message_view)
    CTextView messageView;

    @BindView(R.id.message_title_view)
    CTextView messageTitleView;

    CButton cButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        @Type
        int type = MESSAGE_TYPE_INFORMATION;
        String message = "";
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE_EXTRA, MESSAGE_TYPE_INFORMATION);
            message = getArguments().getString(MESSAGE_EXTRA, "");
        }
        @StyleRes
        int style;
        @StringRes
        int messageTitle;
        switch (type) {
            case MESSAGE_TYPE_ERROR:
                style = R.style.MessageDialog_Error;
                messageTitle = R.string.error_message_dialog_title;
                break;
            case MESSAGE_TYPE_INFORMATION:
            default:
                style = R.style.MessageDialog_Information;
                messageTitle = R.string.information_message_dialog_title;
                break;
        }
        inflater = inflater.cloneInContext(new ContextThemeWrapper(requireContext(), style));
        final View view = bindView(inflater, R.layout.message_dialog_fragment);
        OutlineProvider.ROUNDED.applyTo(view, R.dimen.dialog_corner_radius);

        cButton = view.findViewById(R.id.dialog_ok_btn);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult(Activity.RESULT_OK, null);
                updateApp(view);

            }
        });

        messageView.setText(message);
        messageTitleView.setText(messageTitle);

        return view;
    }


    public void updateApp(View view){
        // String android_id = Settings.Secure.getString(getContext().getContentResolver(),
        //    Settings.Secure.ANDROID_ID);

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(view.getContext());

        appUpdaterUtils.setUpdateJSON("http://unipos.unicard.ge:9000/UniProcessingPrivate.UniPosSVC.svc/GetDeviceRelease/"+testDeviceID)
                .setUpdateFrom(UpdateFrom.JSON)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean aBoolean) {
//                                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
//
//                                downloadApk.startDownloadingApk(update.getUrlToDownload().toString());
                        if(update.getLatestVersionCode()> BuildConfig.VERSION_CODE){

                            final UpdateAppFragment fr = UpdateAppFragment.newInstance();
                            fr.show(fragmentManager, fr.getTag());
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError appUpdaterError) {

                    }
                }).start();
    }
}
