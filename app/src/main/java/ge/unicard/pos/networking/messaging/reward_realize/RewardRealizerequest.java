package ge.unicard.pos.networking.messaging.reward_realize;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;

public class RewardRealizerequest extends BaseRequest {
    @NonNull
    @SerializedName("device_id")
    public String deviceId;

    @NonNull
    @SerializedName("reward_code")
    public String reward_code;


}
