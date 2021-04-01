package ge.unicard.pos.presentation.reward.list_rewards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import ge.unicard.pos.R;
import ge.unicard.pos.adapter.RewardAdapter;
import ge.unicard.pos.bus.MessageReward;
import ge.unicard.pos.bus.ResrdsItem;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.networking.messaging.get_reward_list.Reward;
import ge.unicard.pos.ui.base.BaseFragment;
import ge.unicard.pos.ui.base.BaseMvpFragment;

import static ge.unicard.pos.presentation.cards.RewardEnterPresenterImpl.rewardList;


public class RewardsListFragment extends BaseFragment{

    @BindView(R.id.recycler_rewards_list)
    RecyclerView recyclerView;


    RewardAdapter mAdapter;

    @BindView(R.id.text_card)
    TextView  card;

    @BindView(R.id.text_fullname)
    TextView fullname;


    public static RewardsListFragment newInstance() {
        return new RewardsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.fragment_rewards_list, container, false);


        return bindView(inflater, R.layout.fragment_rewards_list, false,
                new ToolbarInfo.Builder()
                        .setTitle(new ToolbarInfo.ActionTitle(getString(R.string.paying) , new View.OnClickListener() {
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
                                        if (getActivity() != null) {
                                            getActivity().onBackPressed();
                                        }
                                    }
                                }))
                        .setActionButton2(new ToolbarInfo.ActionButton(R.mipmap.logo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                
                            }
                        }))
                        .build());

        //String test = getArguments().getString("card") + " " + getArguments().getString("name");







        //return view;
    }


    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fullname.setText("მფლობელი: " + getArguments().getString("name"));
        card.setText("ბარათი: " + getArguments().getString("card"));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mAdapter = new RewardAdapter(getContext(), rewardList, getArguments().getString("name")
                ,getArguments().getString("card"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }


}
