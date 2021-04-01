package ge.unicard.pos.networking.messaging.check_phone;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;

public class CheckPhoneRequest extends BaseRequest {

    @SerializedName("device_id")
    public String deviceId;

    @SerializedName("card")
    public String card;
}
