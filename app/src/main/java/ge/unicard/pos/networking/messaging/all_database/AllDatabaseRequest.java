package ge.unicard.pos.networking.messaging.all_database;

import com.google.gson.annotations.SerializedName;

public class AllDatabaseRequest{

    @SerializedName("device_id")
    public String deviceID;

    @SerializedName("batch_id")
    public String batchID;

    @SerializedName("batch_data")
    public String batchData;
}
