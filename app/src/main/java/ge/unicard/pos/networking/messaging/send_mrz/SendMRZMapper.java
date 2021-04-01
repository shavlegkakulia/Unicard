package ge.unicard.pos.networking.messaging.send_mrz;

import ge.unicard.pos.networking.messaging.base.BaseResponse;
import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class SendMRZMapper implements BaseResponseMapper<SendMRZResponse, BaseResponse> {

    public SendMRZMapper(){

    }

    @Override
    public BaseResponse map(SendMRZResponse response)  {
        return response;
    }
}
