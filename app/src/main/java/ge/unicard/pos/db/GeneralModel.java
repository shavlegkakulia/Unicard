package ge.unicard.pos.db;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Unique;


import ge.unicard.pos.utils.DateTime;

public class GeneralModel extends SugarRecord {

    @SerializedName("amount")
    public String amount;

    @SerializedName("tran_date")
    public String tranDate;


    @SerializedName("card")
    public String card;

    @SerializedName("bonus")
    public String bonus;

    @SerializedName("status")
    public String status;


    @SerializedName("stan")
    public String stan;

    @SerializedName("resp_code")
    public String responseCode;

    @SerializedName("")
    public String receiptId;

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    @SerializedName("batch_id")
    public String batchID;

    @SerializedName("typeTransactionForApp")
    public Integer type;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    // Default constructor is necessary for SugarRecord
    public GeneralModel() {}



    public GeneralModel(String amount, String tranDate,
                        String card, String bonus, String status, Integer type, String stan,
                        String responseCode, String batchID, String receiptId){
        this.amount = amount;
        this.tranDate = tranDate;
        this.card = card;
        this.bonus = bonus;
        this.status = status;
        this.type = type;
        this.stan = stan;
        this.responseCode = responseCode;
        this.batchID = batchID;
        this.receiptId = receiptId;

    }
}
