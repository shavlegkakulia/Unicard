package ge.unicard.pos.presentation.launcher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import ge.unicard.pos.db.DeviceIdDB;
import ge.unicard.pos.networking.utilnetworking.NetworkChangeReceiver;
import ge.unicard.pos.ui.base.BaseActivity;


import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Akaki on 10/23/18.
 */
public class LauncherActivity extends BaseActivity {

    protected PowerManager.WakeLock mWakeLock;
    public static String testDeviceID;

    DeviceIdDB deviceIdDB;
    int a = 0;
    public static Activity activity;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for check state internet connection
        registerReceiver(
                new NetworkChangeReceiver(this),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

            activity = LauncherActivity.this;

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(LauncherActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return 0;
            }
            @SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
            Toast toast = Toast.makeText(LauncherActivity.this,
                    "Activity: " + deviceId, Toast.LENGTH_SHORT);
            toast.show();


            if (deviceId == null) {

                sharedPreferences = getPreferences(MODE_PRIVATE);
                String savedText = sharedPreferences.getString("deviceID", "");
                //Toast.makeText(this, "shared: "+savedText, Toast.LENGTH_LONG).show();

                testDeviceID = savedText;
            } else {
                testDeviceID = deviceId;
                // deviceIdDB = new DeviceIdDB(deviceId);
                // deviceIdDB.save();
                // mSettings = getSharedPreferences(deviceId, Context.MODE_PRIVATE);
                sharedPreferences = getPreferences(MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("deviceID", deviceId);

                editor.commit();
                String savedText = sharedPreferences.getString("deviceID", "");
                testDeviceID = savedText;

            }


            // for the screen is always on
            final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            this.mWakeLock.acquire();


            String android_id = Settings.Secure.getString(getApplication().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
/*
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(LauncherActivity.this);

        appUpdaterUtils.setUpdateJSON("http://92.241.68.214:9000/UniProcessing.UniPos.UniPosService.svc/GetDeviceRelease/"+android_id)
                .setUpdateFrom(UpdateFrom.JSON)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean aBoolean) {
//                                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
//
//                                downloadApk.startDownloadingApk(update.getUrlToDownload().toString());




                        if(update.getLatestVersionCode()>BuildConfig.VERSION_CODE){




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
                            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            final long downloadId = manager.enqueue(request);

                            //set BroadcastReceiver to install app when .apk is downloaded
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    Intent install = new Intent(Intent.ACTION_VIEW);
                                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    install.setDataAndType(uri,
                                            manager.getMimeTypeForDownloadedFile(downloadId));
                                    startActivity(install);
                                    unregisterReceiver(this);
                                    finish();

                                }
                            };
                            //register receiver for when .apk download is compete
                            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        }




                    }

                    @Override
                    public void onFailed(AppUpdaterError appUpdaterError) {

                    }
                }).start();

        */

    }

    @Override
    public void onResume(){
        super.onResume();
       // sendBroadcast(new Intent("android.intent.action.hide_navigationbar"));
        InnerRecevier innerReceiver = new InnerRecevier();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(innerReceiver, intentFilter);
    }

    // for home listen
    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        final String ACTION_CLOSE_SYSTEM_DIALOGS = "system";
        final String ACTION_CLOSE_SYSTEM_DIALOGS_KEY = "closekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("onReceiveonReceive", action);
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                startActivity(getPackageManager().getLaunchIntentForPackage("ge.unicard.pos"));
                //String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                //if (reason != null) {
                    //if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        // home is Pressed
                        //startActivity(getPackageManager().getLaunchIntentForPackage("ge.unicard.pos"));
                   // }
                //}
            }

        }
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        //startActivity(getPackageManager().getLaunchIntentForPackage("ge.unicard.pos"));
    }


    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }
}
