package ge.unicard.pos.networking.messaging.get_reward_list;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class GetRewardListMapper implements BaseResponseMapper<GetRewardListResponse, String> {
    @Override
    public String map(GetRewardListResponse response) throws Exception {
        return response.displayText;
    }
}
