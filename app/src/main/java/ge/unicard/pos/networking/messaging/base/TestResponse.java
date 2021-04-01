package ge.unicard.pos.networking.messaging.base;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class TestResponse {

    @Nullable
    @SerializedName("merch_name")
    public String merchName;

    @Nullable
    @SerializedName("terminalId")
    public String terminalId;

    @Nullable
    @SerializedName("Merchant_id")
    public String merchId;

    @Nullable
    @SerializedName("Address")
    public String address;

    @Nullable
    @SerializedName("Status")
    public String status;

    @Nullable
    @SerializedName("card")
    public String card;

    @Nullable
    @SerializedName("tran_date")
    public String tranDate ;

    @Nullable
    @SerializedName("reward_name")
    public String reward_name ;

    @Nullable
    @SerializedName("reward_code")
    public String reward_code;

}
