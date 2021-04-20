package ge.unicard.pos.networking;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import ge.unicard.pos.urlGenerator.UrlGenerator;

public final class ApiConstants {

    /*
    // Test server

    public static final String SERVER_ENDPOINT = "http://92.241.68.214:9000/";

    public static final String METHOD_GET_ACCOUNT_INF = "UniProcessing.UniPos.UniPosService.svc/GetAccountInfo";
    public static final String METHOD_BONUS_ACCUMULATION = "UniProcessing.UniPos.UniPosService.svc/BonusAccumulation";
    public static final String METHOD_MAKE_PAYMENT = "UniProcessing.UniPos.UniPosService.svc/MakePayment";
    public static final String METHOD_SEND_OTP = "UniProcessing.UniPos.UniPosService.svc/SendOtp";
    public static final String METHOD_GET_BALANCE= "UniProcessing.UniPos.UniPosService.svc/GetBalance";
    public static final String METHOD_PURCHASE= "UniProcessing.UniPos.UniPosService.svc/Purchase";
    public static final String METHOD_CLOSE_DAY = "UniProcessing.UniPos.UniPosService.svc/CloseDay";

    public static final String METHOD_GET_REWARD_LIST = "UniProcessing.UniPos.UniPosService.svc/GetRewardList";
    public static final String METHOD_REWARD_REALIZE = "UniProcessing.UniPos.UniPosService.svc/RewardRealize";

    public static final String METHOD_GET_DEVICE_INFO = "UniProcessing.UniPos.UniPosService.svc/GetDeviceInfo";

    */




    public static final String SERVER_ENDPOINT = "http://109.238.238.197:5000/"; //http://unipos.unicard.ge:9000/
    public static final String NEW_SERVER_ENDPOINT = "http://10.5.155.71:8065/";

    public static final String METHOD_GET_ACCOUNT_INF = "api/Bonus/GetAccountInfo"; //UniProcessingPrivate.UniPosSVC.svc/GetAccountInfo
    public static final String METHOD_BONUS_ACCUMULATION = "api/Bonus/BonusAccumulation"; // UniProcessingPrivate.UniPosSVC.svc/BonusAccumulation
    public static final String METHOD_MAKE_PAYMENT = "api/Bonus/MakePayment"; // UniProcessingPrivate.UniPosSVC.svc/MakePayment
    public static final String METHOD_SEND_OTP = "api/Bonus/SendOtp"; //UniProcessingPrivate.UniPosSVC.svc/SendOtp
    public static final String METHOD_GET_BALANCE= "api/Bonus/GetBalance"; //UniProcessingPrivate.UniPosSVC.svc/GetBalance
    public static final String METHOD_PURCHASE= "api/Bonus/Purchase"; //UniProcessingPrivate.UniPosSVC.svc/Purchase
    public static final String METHOD_CLOSE_DAY = "UniProcessingPrivate.UniPosSVC.svc/CloseDay";

    public static final String METHOD_GET_REWARD_LIST = "UniProcessingPrivate.UniPosSVC.svc/GetRewardList";
    public static final String METHOD_REWARD_REALIZE = "UniProcessingPrivate.UniPosSVC.svc/RewardRealize";

    public static final String METHOD_GET_DEVICE_INFO = "api/Bonus/GetDeviceInfo"; //UniProcessingPrivate.UniPosSVC.svc/GetDeviceInfo // +"?" + date.toString()

    public static final String METHOD_ALL_DATABASE = "UniProcessingPrivate.UniPosSVC.svc/AddMerchantsBatchData";

   // public static final String METHOD_SEND_MRZ = "";

    public static final String METHOD_REGISTER_NEW_CUSTOMER = "UniProcessingPrivate.UniPosSVC.svc/RegisterNewCustomer";

    // for check number phone
    public static final String METHOD_CHECK_PHONE = "api/Bonus/CheckPhone"; //UniProcessingPrivate.UniPosSVC.svc/CheckPhone
    public static final String METHOD_SUBMIT_PHONE = "api/Bonus/SubmitPhone"; // UniProcessingPrivate.UniPosSVC.svc/SubmitPhone

    public static final String SOURCE_MOBAPP = "MOBAPP";

    public static final String LANG_KA = "ka";

    public static final int RESULT_CODE_OK = 200;

   /* {
        "lang":"ka",
            "app_source":"UniPos",
            "device_id":"123456",
            "card": "1199111111111111",
            "phone":"555493814",
            "first_name":"san",
            "surname":"dro",
            "country":"ge",
            "document_number":"`12",
            "birth_date":"1234",
            "sex":"1",
            "expire_date":"123124",
            "personal_number":"123",
            "uid":"3332"
    } */
    @StringDef({
            METHOD_GET_ACCOUNT_INF,
            METHOD_BONUS_ACCUMULATION,
            METHOD_MAKE_PAYMENT,
            METHOD_SEND_OTP,
            METHOD_GET_BALANCE,
            METHOD_GET_BALANCE,
            METHOD_CLOSE_DAY,
            METHOD_GET_REWARD_LIST,
            METHOD_REWARD_REALIZE,
            METHOD_GET_DEVICE_INFO,
            METHOD_ALL_DATABASE,
            METHOD_CHECK_PHONE, METHOD_SUBMIT_PHONE, METHOD_REGISTER_NEW_CUSTOMER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Method {
    }

    @IntDef({
            RESULT_CODE_OK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    @StringDef({
            LANG_KA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Lang {
    }

    @StringDef({
            SOURCE_MOBAPP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    private ApiConstants() {
    }
}
