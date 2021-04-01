package ge.unicard.pos.presentation.other;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import java.io.File;

import butterknife.OnClick;
import ge.unicard.pos.BuildConfig;
import ge.unicard.pos.R;
import ge.unicard.pos.ui.base.BaseDialogFragment;
import ge.unicard.pos.ui.widgets.CButton;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.OutlineProvider;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.activity;
import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.fragmentManager;


public class UpdateAppFragment extends BaseDialogFragment {




 CButton cButtonYes, cButtonNo;
 CTextView cTextView;


    public static UpdateAppFragment newInstance() {
        UpdateAppFragment fragment = new UpdateAppFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_app, container, false);

        cTextView = view.findViewById(R.id.description_view_update);
        cTextView.setText("თქვენ სარგებლობთ აპლიკაციის ძველი "+ String.valueOf(BuildConfig.VERSION_CODE) +" ვერსიით." + " გთხოვთ განაახლოთ აპლიკაცია");


        cButtonYes = view.findViewById(R.id.update_dialog_yes_btn);
        cButtonNo = view.findViewById(R.id.update_dialog_no_btn);
        cButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult(Activity.RESULT_CANCELED, null);
            }
        });
        cButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult(Activity.RESULT_OK, null);
                updateApp(view);
                Intent launchIntent = view.getContext().getPackageManager().getLaunchIntentForPackage("unicard.ge.unicardappopener");
                view.getContext().startActivity(launchIntent);
            }
        });

        OutlineProvider.ROUNDED.applyTo(view, R.dimen.dialog_corner_radius);
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



                            //get destination to update file and set Uri
                            //TODO: First I wanted to store my update .apk file on internal storage for my app but apparently android does not allow you to open and install
                            //aplication with existing package from there. So for me, alternative solution is Download directory in external storage. If there is better
                            //solution, please inform us in comment
                            String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                            String fileName = "AppName.apk";
                            destination += fileName;
                            final Uri uri = Uri.parse("file://" + destination);

                            //Delete update file if exists
                            File file = new File(destination);
                            if (file.exists())
                                //file.delete() - test this, I think sometimes it doesnt work
                                file.delete();

                            //get url of app on server
                            String url = update.getUrlToDownload().toString();

                            //set downloadmanager
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setDescription("Update");
                            request.setTitle("Update");

                            //set destination
                            request.setDestinationUri(uri);

                            // get download service and enqueue file
                            final DownloadManager manager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            final long downloadId = manager.enqueue(request);

                            //set BroadcastReceiver to install app when .apk is downloaded
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    Intent install = new Intent(Intent.ACTION_VIEW);
                                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    install.setDataAndType(uri,
                                            manager.getMimeTypeForDownloadedFile(downloadId));
                                    view.getContext().startActivity(install);
                                    ctxt.unregisterReceiver(this);

                                    //startActivity(getContext().getPackageManager().getLaunchIntentForPackage("ge.unicard.pos"));
                                    //finish();
                                }
                            };
                            //register receiver for when .apk download is compete
                            view.getContext().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError appUpdaterError) {

                    }
                }).start();
    }

}
