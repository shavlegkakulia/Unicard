package ge.unicard.pos.networking.utilnetworking;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.launcher.LauncherFragment;
import ge.unicard.pos.presentation.other.InternetFragment;
import ge.unicard.pos.ui.base.BaseActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {

    BaseActivity activity;

    public NetworkChangeReceiver(BaseActivity _activity){
        activity = _activity;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);

        if( status == 2){
            //activity.load
            activity.setTheme(R.style.Launcher_Theme);
            activity.loadFragment(LauncherFragment.newInstance());
        }
        else{
            activity.loadFragment(InternetFragment.newInstance());
        }
      //  Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
