package ge.unicard.pos.networking;

import java.util.Date;

import javax.annotation.PostConstruct;

import ge.unicard.pos.networking.messaging.all_database.AllDatabaseRequest;
import ge.unicard.pos.networking.messaging.all_database.AllDatabaseResponse;
import ge.unicard.pos.networking.messaging.check_phone.CheckPhoneRequest;
import ge.unicard.pos.networking.messaging.check_phone.CheckPhoneResponse;
import ge.unicard.pos.networking.messaging.close_day.CloseDayRequest;
import ge.unicard.pos.networking.messaging.close_day.CloseDayResponse;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceRequest;
import ge.unicard.pos.networking.messaging.get_balance.GetBalanceResponse;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoRequest;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoResponce;
import ge.unicard.pos.networking.messaging.get_reward_list.GetRewardListRequest;
import ge.unicard.pos.networking.messaging.get_reward_list.GetRewardListResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseRequest;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationRequest;
import ge.unicard.pos.networking.messaging.bonus_accumulation.BonusAccumulationResponse;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoRequest;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoResponse;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentRequest;
import ge.unicard.pos.networking.messaging.make_payment.MakePaymentResponse;
import ge.unicard.pos.networking.messaging.reward_realize.RewardRealizeResponse;
import ge.unicard.pos.networking.messaging.reward_realize.RewardRealizerequest;
import ge.unicard.pos.networking.messaging.send_mrz.SendMRZRequest;
import ge.unicard.pos.networking.messaging.send_mrz.SendMRZResponse;
import ge.unicard.pos.networking.messaging.send_otp.SendOtpRequest;
import ge.unicard.pos.networking.messaging.send_otp.SendOtpResponse;
import ge.unicard.pos.networking.messaging.submit_phone.SubmitPhoneRequest;
import ge.unicard.pos.networking.messaging.submit_phone.SubmitPhoneResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Headers;
public interface ApiServices {



    @POST(ApiConstants.METHOD_GET_ACCOUNT_INF)
    Call<GetAccountInfoResponse> getAccountInfo(@Body GetAccountInfoRequest request);

    @POST(ApiConstants.METHOD_BONUS_ACCUMULATION)
    Call<BonusAccumulationResponse> bonusAccumulation(@Body BonusAccumulationRequest request);

    @POST(ApiConstants.METHOD_MAKE_PAYMENT)
    Call<MakePaymentResponse> makePayment(@Body MakePaymentRequest request);

    @POST(ApiConstants.METHOD_SEND_OTP)
    Call<SendOtpResponse> sendOtp(@Body SendOtpRequest request);

    @POST(ApiConstants.METHOD_GET_BALANCE)
    Call<GetBalanceResponse> getBalance(@Body GetBalanceRequest request);

    @POST(ApiConstants.METHOD_PURCHASE)
    Call<PurchaseResponse> purchase(@Body PurchaseRequest request);

    @POST(ApiConstants.METHOD_CLOSE_DAY)
    Call<CloseDayResponse> closeDay(@Body CloseDayRequest request);

    @POST(ApiConstants.METHOD_GET_REWARD_LIST)
    Call<GetRewardListResponse> getRewardList(@Body GetRewardListRequest request);

    @POST(ApiConstants.METHOD_REWARD_REALIZE)
    Call<RewardRealizeResponse> RewardRealizeServ(@Body RewardRealizerequest request);

    @POST(ApiConstants.METHOD_REGISTER_NEW_CUSTOMER)
    Call<SendMRZResponse> sendMRZ(@Body SendMRZRequest request);

    @POST(ApiConstants.METHOD_BONUS_ACCUMULATION)
    Call<BonusAccumulationResponse> rewersTest(@Body BonusAccumulationRequest request);


    @POST(ApiConstants.METHOD_GET_DEVICE_INFO )
    Call<GetDeviceInfoResponce>  getDeviceInfo(@Body GetDeviceInfoRequest request);

    @POST(ApiConstants.METHOD_ALL_DATABASE)
    Call<AllDatabaseResponse> sendAllDatabase(@Body AllDatabaseRequest request);

    @POST(ApiConstants.METHOD_CHECK_PHONE)
    Call<CheckPhoneResponse> checkPhone(@Body CheckPhoneRequest request);

    @POST(ApiConstants.METHOD_SUBMIT_PHONE)
    Call<SubmitPhoneResponse> submitPhone(@Body SubmitPhoneRequest request);


}
