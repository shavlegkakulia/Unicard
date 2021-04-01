package ge.unicard.pos.networking.messaging.purchase;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class PurchaseMapper implements BaseResponseMapper<PurchaseResponse, String> {

    @Override
    public String map(PurchaseResponse response) {
        final String info =
                ""+response.displayText+"\n"+
                        "ბარათი: "+response.card+"\n"
                        +"ხელმისაწვდომი თანხა: "+response.amount + "\u20BE\n";
        return info;


    }
}
