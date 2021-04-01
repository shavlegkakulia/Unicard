package ge.unicard.pos.db;



import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Unique;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class TransactionPrintDB   {





    @PrimaryKey
    public Integer id;


    @SerializedName("address")
    public String address;



    @SerializedName("amount")
    public String amount;



    @SerializedName("bonus")
    public String bonus;



    @SerializedName("card")
    public String card;



    @SerializedName("merch_id")
    public String merchId;



    @SerializedName("merch_name")
    public String merchName;



    @SerializedName("org_name")
    public String orgName;



    @SerializedName("resp_code")
    public String respCode;


    @SerializedName("status")
    public String status;



    @SerializedName("terminal_id")
    public String terminalId;



    @SerializedName("tran_date")
    public String tran_date;



    @SerializedName("tran_type")
    public String tran_type;



    @SerializedName("receipt_id")
    public String receiptId;


    public TransactionPrintDB(){}

    public TransactionPrintDB(Integer id, String address, String amount, String bonus, String card,
                              String merchId, String merchName, String orgName, String respCode,
                              String status, String terminalId, String tran_date, String tran_type, String receiptId) {
        this.id = id;
        this.address = address;
        this.amount = amount;
        this.bonus = bonus;
        this.card = card;
        this.merchId = merchId;
        this.merchName = merchName;
        this.orgName = orgName;
        this.respCode = respCode;
        this.status = status;
        this.terminalId = terminalId;
        this.tran_date = tran_date;
        this.tran_type = tran_type;
        this.receiptId = receiptId;
    }
}
