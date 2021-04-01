package ge.unicard.pos.networking.messaging.send_otp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;

public class SendOtpRequest extends BaseRequest {

    @NonNull
    @SerializedName("device_id")
    public String deviceId;

    @NonNull
    @SerializedName("card")
    public String card;

    @SerializedName("stan")
    public String stan;

}
