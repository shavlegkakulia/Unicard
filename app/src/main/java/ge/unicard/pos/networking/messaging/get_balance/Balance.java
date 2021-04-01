package ge.unicard.pos.networking.messaging.get_balance;

public class Balance {

    private String amount;
    private String status;

    public Balance(String amount, String status) {
        this.amount = amount;
        this.status = status;
    }

    public Balance(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
