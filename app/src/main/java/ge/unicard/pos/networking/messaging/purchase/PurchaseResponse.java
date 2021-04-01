package ge.unicard.pos.networking.messaging.purchase;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseResponse;

public class PurchaseResponse extends BaseResponse {




    @Nullable
    @SerializedName("Address")
    public String address;


    @Nullable
    @SerializedName("EndBalance")
    public String endBalance;

    @Nullable
    @SerializedName("Merchant_id")
    public String merchId;

    @Nullable
    @SerializedName("SpentAmount")
    public String spentAmount;

    @Nullable
    @SerializedName("OrgName")
    public String OrgName;

    @Nullable
    @SerializedName("merch_name")
    public String merchName;


    @Nullable
    @SerializedName("Status")
    public String status;

    @Nullable
    @SerializedName("terminalId")
    public String terminalId;


    @Nullable
    @SerializedName("TranType")
    public String tran_type;

    @Nullable
    @SerializedName("Receipt_id")
    public String receiptId;

    @Nullable
    @SerializedName("amount")
    public String amount;

    @Nullable
    @SerializedName("card")
    public String card;

    @Nullable
    @SerializedName("tran_date")
    public String tranDate;

    @Nullable
    @SerializedName("stan")
    public String stan;

    @Nullable
    @SerializedName("resp_code")
    public String respCode;


}
