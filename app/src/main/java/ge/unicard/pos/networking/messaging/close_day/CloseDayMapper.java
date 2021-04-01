package ge.unicard.pos.networking.messaging.close_day;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class CloseDayMapper implements BaseResponseMapper<CloseDayResponse, Boolean> {
    @Override
    public Boolean map(CloseDayResponse response) throws Exception {
        return true;
    }
}
