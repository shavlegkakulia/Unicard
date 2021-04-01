package ge.unicard.pos.utils;

import com.nexgo.oaf.apiv3.DeviceInfo;

public class DeviceInfo2 extends com.nexgo.oaf.apiv3.DeviceInfo {

    private String deviceId;

    public DeviceInfo2(DeviceInfo deviceInfo) {
        setSn(deviceInfo.getSn());
        setKsn(deviceInfo.getKsn());
        setModel(deviceInfo.getModel());
        setOsVer(deviceInfo.getOsVer());
        setSdkVer(deviceInfo.getSdkVer());
        setFirmWareVer(deviceInfo.getFirmWareVer());
        setKernelVer(deviceInfo.getKernelVer());
        setVendor(deviceInfo.getVendor());
        setSupportGM(deviceInfo.isSupportGM());
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
