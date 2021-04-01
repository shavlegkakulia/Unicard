package ge.unicard.pos.networking.messaging.send_otp;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class SendOtpMapper implements BaseResponseMapper<SendOtpResponse, String> {

    public SendOtpMapper() {
    }

    @Override
    public String map(SendOtpResponse response) {

        final String info = response.displayText;
        return info;
    }
}

