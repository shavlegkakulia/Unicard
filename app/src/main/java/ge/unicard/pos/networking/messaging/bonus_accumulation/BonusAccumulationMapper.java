package ge.unicard.pos.networking.messaging.bonus_accumulation;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class BonusAccumulationMapper  implements BaseResponseMapper<BonusAccumulationResponse, String> {

    @Override
    public String map(BonusAccumulationResponse response) {


        String statusToShow = ""+response.displayText+"\n"+
                ""+"\n"+
                "ბარათი: "+response.card+"\n"
                +"დაგროვილი ქულა: "+response.AccumulatedBonus +"\n"
                +"სულ დაგროვილი ქულა: "+response.bonus_accomulation_test +"\n"
                +"ხელმისაწვდომი თანხა: "+response.amount + "\u20BE\n";
        return statusToShow;
    }
}
