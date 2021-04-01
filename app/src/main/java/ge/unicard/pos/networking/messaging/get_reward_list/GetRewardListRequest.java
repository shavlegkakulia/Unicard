package ge.unicard.pos.networking.messaging.get_reward_list;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;


public class GetRewardListRequest extends BaseRequest {


    @SerializedName("device_id")
    public String deviceId;

    @SerializedName("card")
    public String card;

    @SerializedName("reward_bar_code")
    public String rewardBarCode;

   /* @SerializedName("app_source")
    public String appSource;

    @SerializedName("lang")
    public String lang; */




}
