package ge.unicard.pos.networking.messaging.get_device_info;

import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;

public class GetDeviceInfoMapper implements BaseResponseMapper<GetDeviceInfoResponce, String>{
    @Override
    public String map(GetDeviceInfoResponce response) throws Exception {
        return response.getAPIVersion();
    }


}
