package ge.unicard.pos.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import ge.unicard.pos.R;

/**
 * Created by Akaki on 10/23/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.container_view)
    View containerView;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left);

        setTheme(R.style.PageTheme);
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);
    }

    public void loadFragment(Fragment fr) {
        loadFragment(fr, false);
    }

    protected void loadFragment(Fragment fr,
                                boolean addToBackStack) {
        loadFragment(fr, addToBackStack, false);
    }

    protected void loadFragment(Fragment fr,
                                boolean addToBackStack,
                                boolean animate) {
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction tr = fm.beginTransaction();

       // tr.commitAllowingStateLoss();
        tr.replace(containerView.getId(), fr);
        if (addToBackStack) {
            tr.addToBackStack(null);
        }
        if (animate) {
            // TODO: 10/25/18 custom animations
        }
        tr.commit();
    }

    protected void loadFragment(Fragment fr, boolean addToBackStack, int rewardMode) {
        loadFragment(fr, addToBackStack, 1);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        ensureSystemUiHidden();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        if(isFinishing()){
//            restoreSystemUi();
//        }
//    }

    private void ensureSystemUiHidden(){
        Settings.System.putInt(getContentResolver(), "status_bar_disabled", 1);
        sendBroadcast(new Intent("android.intent.action.hide_navigationbar"));
        //hide buttons
        final Window wnd = getWindow();
        final WindowManager.LayoutParams lp = wnd.getAttributes();
        lp.dimAmount = 1.0f;
        wnd.setAttributes(lp);
        wnd.addFlags( FLAG_BLOCK_HOME_BUTTON | FLAG_BLOCK_MENU_BUTTON);
    }

    private void restoreSystemUi(){
        Settings.System.putInt(getContentResolver(), "status_bar_disabled", 0);
    }

    static final int FLAG_BLOCK_HOME_BUTTON = 3;
    static final int FLAG_BLOCK_MENU_BUTTON = 5;
}
