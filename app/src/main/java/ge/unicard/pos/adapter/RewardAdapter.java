package ge.unicard.pos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ge.unicard.pos.R;
import ge.unicard.pos.networking.messaging.get_reward_list.Reward;
import ge.unicard.pos.presentation.reward_preview.RewardPreviewActivity;

public class RewardAdapter  extends RecyclerView.Adapter<RewardAdapter.ViewHolder>{


    private Context ctx;
    public static List<Reward> arrayList;
    String fullname, card;

    public static RewardAdapter rewardAdapter;

    public RewardAdapter(Context ctx, List<Reward> arrayList, String fullname, String card) {

        this.ctx = ctx;
        this.arrayList = arrayList;
        this.fullname = fullname;
        this.card = card;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView title, description, countDay;
        ImageView image;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.text_title);
            countDay = view.findViewById(R.id.text_validity);
            image = view.findViewById(R.id.image_reward_dialog);

        }
    }

    @NonNull
    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_reward_dialog, viewGroup, false);

        rewardAdapter = RewardAdapter.this;

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.ViewHolder viewHolder, int i) {

        viewHolder.title.setText(arrayList.get(i).getRewardName());
        viewHolder.countDay.setText(arrayList.get(i).getRewardValidity());


        //Picasso.get().load(arrayList.get(i).getRewardImageUrl()).into(viewHolder.image);

        Glide.with(ctx).load(arrayList.get(i).getRewardImageUrl()).into(viewHolder.image);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, RewardPreviewActivity.class);
                intent.putExtra("card", card);
                intent.putExtra("name", fullname);

                intent.putExtra("reward_name",arrayList.get(i).getRewardName());
                intent.putExtra("validaty",arrayList.get(i).getRewardValidity());
                intent.putExtra("description",arrayList.get(i).getRewardDescription());
                intent.putExtra("url_image",arrayList.get(i).getRewardImageUrl());

                intent.putExtra("reward_code", arrayList.get(i).getRewardCode());

                intent.putExtra("position", i);

                ctx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
