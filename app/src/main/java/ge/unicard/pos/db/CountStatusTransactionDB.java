package ge.unicard.pos.db;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class CountStatusTransactionDB /*extends SugarRecord*/ {


    // тип транзакции
    public int typeTransactionID;

    // сумма
    public double amountBonusAccomulation;

    // сумма возвратов
    public double reverstBonusAccomulation;

    // количетсов транзакций этого типа
    public int countTransactionBonusAccomulation;

    // количество отмененных транзакций
    public int countRewersBonusAccomulation;

    public CountStatusTransactionDB(int typeTransactionID, double amountBonusAccomulation, double reverstBonusAccomulation, int countTransactionBonusAccomulation, int countRewersBonusAccomulation) {
        this.typeTransactionID = typeTransactionID;
        this.amountBonusAccomulation = amountBonusAccomulation;
        this.reverstBonusAccomulation = reverstBonusAccomulation;
        this.countTransactionBonusAccomulation = countTransactionBonusAccomulation;
        this.countRewersBonusAccomulation = countRewersBonusAccomulation;
    }

    public int getTypeTransactionID() {
        return typeTransactionID;
    }

    public void setTypeTransactionID(int typeTransactionID) {
        this.typeTransactionID = typeTransactionID;
    }

    public double getAmountBonusAccomulation() {
        return amountBonusAccomulation;
    }

    public void setAmountBonusAccomulation(double amountBonusAccomulation) {
        this.amountBonusAccomulation = amountBonusAccomulation;
    }

    public double getReverstBonusAccomulation() {
        return reverstBonusAccomulation;
    }

    public void setReverstBonusAccomulation(double reverstBonusAccomulation) {
        this.reverstBonusAccomulation = reverstBonusAccomulation;
    }

    public int getCountTransactionBonusAccomulation() {
        return countTransactionBonusAccomulation;
    }

    public void setCountTransactionBonusAccomulation(int countTransactionBonusAccomulation) {
        this.countTransactionBonusAccomulation = countTransactionBonusAccomulation;
    }

    public int getCountRewersBonusAccomulation() {
        return countRewersBonusAccomulation;
    }

    public void setCountRewersBonusAccomulation(int countRewersBonusAccomulation) {
        this.countRewersBonusAccomulation = countRewersBonusAccomulation;
    }

    /*   // сумма

    @SerializedName("giftcard_pay_amount")
    public double amountPurchase;

    // сумма возвратов
    @SerializedName("giftcard_pay_revers")
    public double reverstPurchase;

    // количетсов транзакций этого типа
    @SerializedName("giftcard_pay_tran_count")
    public double countTransactionPurchase;

    // количество отмененных транзакций
    @SerializedName("giftcard_pay_reverse_count")
    public double countRewerPurchase;


    // сумма
    @SerializedName("pay_amount")
    public double amountMakePayment;

    // сумма возвратов
    @SerializedName("pay_revers")
    public double reverstMakePayment;

    // количетсов транзакций этого типа
    @SerializedName("pay_tran_count")
    public double countTransactionMakePayment;

    // количество отмененных транзакций
    @SerializedName("pay_reverse_count")
    public double countRewersMakePayment; */


    public  CountStatusTransactionDB(){}
}
