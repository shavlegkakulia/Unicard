package ge.unicard.pos.networking.messaging.get_balance;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseResponse;

public class GetBalanceResponse
        extends BaseResponse {

    @NonNull
    @SerializedName("Balance")
    public String balance;
}
