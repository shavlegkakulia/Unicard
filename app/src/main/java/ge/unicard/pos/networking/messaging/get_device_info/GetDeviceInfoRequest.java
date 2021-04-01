package ge.unicard.pos.networking.messaging.get_device_info;

import com.google.gson.annotations.SerializedName;

public class GetDeviceInfoRequest {

    @SerializedName("device_id")
    public String deviceId;
}
