package ge.unicard.pos.networking.messaging.get_reward_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reward {


    @SerializedName("reward_Image_url")
    @Expose
    private String rewardImageUrl;
    @SerializedName("reward_code")
    @Expose
    private String rewardCode;
    @SerializedName("reward_description")
    @Expose
    private String rewardDescription;
    @SerializedName("reward_id")
    @Expose
    private Integer rewardId;
    @SerializedName("reward_name")
    @Expose
    private String rewardName;
    @SerializedName("reward_validity")
    @Expose
    private String rewardValidity;

    public String getRewardImageUrl() {
        return rewardImageUrl;
    }

    public void setRewardImageUrl(String rewardImageUrl) {
        this.rewardImageUrl = rewardImageUrl;
    }

    public String getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(String rewardCode) {
        this.rewardCode = rewardCode;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardValidity() {
        return rewardValidity;
    }

    public void setRewardValidity(String rewardValidity) {
        this.rewardValidity = rewardValidity;
    }
}
