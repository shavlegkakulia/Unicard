package ge.unicard.pos.presentation.reward.list_rewards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import ge.unicard.pos.R;
import ge.unicard.pos.bus.MessageEvent;
import ge.unicard.pos.bus.MessageReward;
import ge.unicard.pos.bus.ResrdsItem;
import ge.unicard.pos.networking.messaging.get_reward_list.Reward;
import ge.unicard.pos.ui.base.BaseActivity;

public class RewardsListActivity extends BaseActivity {

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        bundle.putString("card", getIntent().getStringExtra("card"));
        bundle.putString("name", getIntent().getStringExtra("name"));

        final RewardsListFragment fr = RewardsListFragment.newInstance();
        fr.setArguments(bundle);
        loadFragment(fr);
    }




}
