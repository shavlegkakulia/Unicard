package ge.unicard.pos.networking.messaging.submit_phone;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;

public class SubmitPhoneRequest extends BaseRequest {

    @SerializedName("device_id")
    public String deviceId;

    @SerializedName("card")
    public String card;

    @SerializedName("phone")
    public String phone;
}
