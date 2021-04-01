package ge.unicard.pos.networking.messaging.base;

import com.google.gson.annotations.SerializedName;

public class AccountInfoResponse extends BaseResponse{

    @SerializedName("fullname")
    public String fullname;
    @SerializedName("score")
    public Double score;
    @SerializedName("amount")
    public Double amount;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
