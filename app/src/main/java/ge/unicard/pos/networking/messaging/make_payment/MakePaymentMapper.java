package ge.unicard.pos.networking.messaging.make_payment;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;
import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoResponse;

public class MakePaymentMapper implements BaseResponseMapper<MakePaymentResponse, String> {

    @Override
    public String map(MakePaymentResponse response) {
        return response.displayText;
    }
}

