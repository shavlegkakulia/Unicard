package ge.unicard.pos.networking.messaging.get_account_info;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseResponse;

public class GetAccountInfoResponse extends BaseResponse {

    @NonNull
    @SerializedName("amount")
    public Float amount;

    @NonNull
    @SerializedName("score")
    public Float bonus;

    @NonNull
    @SerializedName("card_type")
    public String cardType;

    @NonNull
    @SerializedName("fullname")
    public String cardholder;
}
