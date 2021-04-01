package ge.unicard.pos.networking.messaging.get_balance;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class GetBalanceMapper implements BaseResponseMapper<GetBalanceResponse, Balance> {

    @Override
    public Balance map(GetBalanceResponse response) {
        return new Balance("ხელმისაწვდომი ნაშთი: "+ response.balance + "\u20BE\n", response.displayText);
    }
}
