package ge.unicard.pos.networking.messaging.get_account_info;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class GetAccountInfoMapper implements BaseResponseMapper<GetAccountInfoResponse, String> {

    @Override
    public String map(GetAccountInfoResponse response) {
        final String info =
                         response.displayText + "\n" +
                                 ""+"\n"+
                        "მფლობელი: " + response.cardholder + "\n"+
                        "ხელმისაწვდომი თანხა: " + response.amount  + "\u20BE\n"+
                        "ხელმისაწვდომი ქულა: " + response.bonus + "\n" +
                        "ბარათის ტიპი: " + response.cardType + "\n";

        // TODO: 11/16/2018 define tsrings
        return info;
    }
}
