package ge.unicard.pos.db;

import com.orm.SugarRecord;

public class DeviceIdDB {

    public String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceIdDB() {
    }

    public DeviceIdDB(String deviceId) {
        this.deviceId = deviceId;
    }
}

