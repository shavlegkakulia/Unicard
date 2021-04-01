package ge.unicard.pos.networking.messaging.submit_phone;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class SubmitPhoneMapper implements BaseResponseMapper<SubmitPhoneResponse, String> {
    @Override
    public String map(SubmitPhoneResponse response) throws Exception {
        String messageCheckPhone = response.resultCode.toString();
        return messageCheckPhone;
    }
}
