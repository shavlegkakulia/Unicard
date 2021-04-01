package ge.unicard.pos.networking.messaging.send_mrz;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.BaseRequest;

public class SendMRZRequest extends BaseRequest {
    // add data for request "send MRZ"
     /* @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("app_source")
    @Expose
    private String appSource; */
    @SerializedName("device_id")
    @Expose
    public String deviceId;
    @SerializedName("card")
    @Expose
    public String card;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("surname")
    @Expose
    public String surname;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("document_number")
    @Expose
    public String documentNumber;
    @SerializedName("birth_date")
    @Expose
    public String birthDate;
    @SerializedName("sex")
    @Expose
    public String sex;
    @SerializedName("expire_date")
    @Expose
    public String expireDate;
    @SerializedName("personal_number")
    @Expose
    public String personalNumber;
    @SerializedName("uid")
    @Expose
    public String uid;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAppSource() {
        return appSource;
    }

    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
