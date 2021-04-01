package ge.unicard.pos.networking.messaging.check_phone;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class CheckPhoneMapper implements BaseResponseMapper<CheckPhoneResponse, String> {
    @Override
    public String map(CheckPhoneResponse response) throws Exception {
        String messageCheckPhone = response.resultCode.toString();
        return messageCheckPhone;
    }
}
