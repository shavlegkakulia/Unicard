package ge.unicard.pos.networking.messaging.reward_realize;


import ge.unicard.pos.networking.messaging.base.BaseResponse;
import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class RewardRealizeMapper implements BaseResponseMapper<RewardRealizeResponse, BaseResponse> {

    public RewardRealizeMapper() {
    }

    @Override
    public BaseResponse map(RewardRealizeResponse response) {
        return response;
    }
}
