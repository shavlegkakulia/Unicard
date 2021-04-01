package ge.unicard.pos.networking.messaging.get_balance;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;

public class GetBalanceRequest  extends BaseRequest {

    @NonNull
    @SerializedName("device_id")
    public String deviceId;

    @NonNull
    @SerializedName("card")
    public String card;
}
