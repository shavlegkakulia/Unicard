package ge.unicard.pos.networking.messaging.base;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class TransactionResponse extends BaseResponse {

    @Nullable
    @SerializedName("address")
    public String address;

    @Nullable
    @SerializedName("amount")
    public String amount;

    @Nullable
    @SerializedName("spent_bonus")
    public String bonus;

    @Nullable
    @SerializedName("AccumulatedBonus")
    public String AccumulatedBonus;

    @Nullable
    @SerializedName("card")
    public String card;

    @Nullable
    @SerializedName("merch_id")
    public String merchId;

    @Nullable
    @SerializedName("merch_name")
    public String merchName;

    @Nullable
    @SerializedName("org_name")
    public String orgName;

    @Nullable
    @SerializedName("resp_code")
    public String respCode;

    @Nullable
    @SerializedName("status")
    public String status;

    @Nullable
    @SerializedName("terminal_id")
    public String terminalId;

    @Nullable
    @SerializedName("tran_date")
    public String tran_date;

    @Nullable
    @SerializedName("tran_type")
    public String tran_type;

    @Nullable
    @SerializedName("stan")
    public String stan;

    @Nullable
    @SerializedName("receipt_id")
    public String receiptId;
}
