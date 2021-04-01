package ge.unicard.pos.networking.messaging.bonus_accumulation;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;
import ge.unicard.pos.utils.DateTime;

public class BonusAccumulationRequest extends BaseRequest {

    @SerializedName("amount")
    public double amount;

    @SerializedName("tran_date")
    public DateTime tranDate;

    @SerializedName("device_id")
    public String deviceId;

    @SerializedName("batch_id")
    public String batchId;

    @SerializedName("card")
    public String card;

    @SerializedName("respcode")
    public String respcode;

    @SerializedName("stan")
    public String stan;
}
