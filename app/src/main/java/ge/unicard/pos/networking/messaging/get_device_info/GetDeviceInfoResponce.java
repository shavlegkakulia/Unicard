package ge.unicard.pos.networking.messaging.get_device_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ge.unicard.pos.networking.messaging.base.BaseResponse;

public class GetDeviceInfoResponce extends BaseResponse {


    @SerializedName("APIVersion")
    @Expose
    private String aPIVersion;
    @SerializedName("AddressAdditionalValue")
    @Expose
    private String addressAdditionalValue;
    @SerializedName("AppVersion")
    @Expose
    private String appVersion;
    @SerializedName("AppVersionCode")
    @Expose
    private String appVersionCode;
    @SerializedName("DeviceID")
    @Expose
    private Object deviceID;
    @SerializedName("BannerURL")
    @Expose
    private String bannerURL;

    @SerializedName("DefaultPage")
    @Expose
    private String defaultPage;


    @SerializedName("MerchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("MerchantName")
    @Expose
    private String merchantName;
    @SerializedName("SimCardNumber")
    @Expose
    private String simCardNumber;
    @SerializedName("TerminalCode")
    @Expose
    private String terminalCode;
    @SerializedName("data")
    @Expose
    private List<TypeFunctionBtn> data = null;



    public String getAPIVersion() {
        return aPIVersion;
    }

    public void setAPIVersion(String aPIVersion) {
        this.aPIVersion = aPIVersion;
    }

    public String getAddressAdditionalValue() {
        return addressAdditionalValue;
    }

    public void setAddressAdditionalValue(String addressAdditionalValue) {
        this.addressAdditionalValue = addressAdditionalValue;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public Object getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Object deviceID) {
        this.deviceID = deviceID;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.bannerURL = bannerURL;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSimCardNumber() {
        return simCardNumber;
    }

    public void setSimCardNumber(String simCardNumber) {
        this.simCardNumber = simCardNumber;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public List<TypeFunctionBtn> getData() {
        return data;
    }

    public void setData(List<TypeFunctionBtn> data) {
        this.data = data;
    }

}
