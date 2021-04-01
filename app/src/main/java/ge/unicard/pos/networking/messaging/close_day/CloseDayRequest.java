package ge.unicard.pos.networking.messaging.close_day;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;
import ge.unicard.pos.utils.DateTime;

public class CloseDayRequest extends BaseRequest {

    @SerializedName("device_id")
    public String deviceId;

    @SerializedName("accumulate_trancount")
    public Integer accumulateTrancount;

    @SerializedName("batch_id")
    public String batchId;

    @SerializedName("batch_date")
    public String batchDate;

    @SerializedName("accumulate_amount")
    public double accumulateAmount;

    @SerializedName("accumulate_amount_revers")
    public double accumulateAmountRevers;

    @SerializedName("accumulate_reverse_count")
    public Integer accumulateReverseCount;

    @SerializedName("pay_tran_count")
    public Integer payTranCount;

    @SerializedName("pay_amount")
    public double payAmount;

    @SerializedName("pay_revers")
    public double payRevers;


    @SerializedName("pay_reverse_count")
    public Integer payReverseCount;

    //@SerializedName("app_source")
   // public String appSource;

    @SerializedName("giftcard_pay_amount")
    public double giftcardPayAmount;

    @SerializedName("giftcard_pay_revers")
    public double giftcarPayRevers;

    @SerializedName("giftcard_pay_reverse_count")
    public Integer giftcardPayReverseCount;

    @SerializedName("giftcard_pay_tran_count")
    public Integer giftcardPayTranCount;
}













