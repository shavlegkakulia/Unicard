package ge.unicard.pos.networking.messaging.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ge.unicard.pos.networking.messaging.get_reward_list.Reward;

public class RewardResponse extends BaseResponse{


    @SerializedName("card")
    public String card;

    @SerializedName("full_name")
    public String fullName;

    @SerializedName("rewards")
    public List<Reward> rewardList;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public List<Reward> getRewards() {
        return rewardList;
    }

    public void setRewards(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }
}
