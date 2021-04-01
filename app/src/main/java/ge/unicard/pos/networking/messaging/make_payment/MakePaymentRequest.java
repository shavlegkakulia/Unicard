package ge.unicard.pos.networking.messaging.make_payment;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;
import ge.unicard.pos.utils.DateTime;

public class MakePaymentRequest extends BaseRequest {

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

    @SerializedName("otp")
    public String otp;
}
