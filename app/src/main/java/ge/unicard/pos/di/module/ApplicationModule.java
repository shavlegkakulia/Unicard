package ge.unicard.pos.di.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import com.google.gson.GsonBuilder;
import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ge.unicard.pos.App;
import ge.unicard.pos.BuildConfig;
import ge.unicard.pos.db.CountStatusTransactionDB;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.BonusAccumulationInteractor;
import ge.unicard.pos.interactors.CardSearchInteractor;
import ge.unicard.pos.interactors.CheckPhoneInteractor;
import ge.unicard.pos.interactors.CloseDayInteractor;
import ge.unicard.pos.interactors.GetAccountInfoInteractor;
import ge.unicard.pos.interactors.GetBalanceInteractor;
import ge.unicard.pos.interactors.GetDeviceInfoInteractor;
import ge.unicard.pos.interactors.GetRewardListInteractor;
import ge.unicard.pos.interactors.MakePaymentInteractor;
import ge.unicard.pos.interactors.PrintReportInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.interactors.PurchaseInteractor;
import ge.unicard.pos.interactors.QrScannerInteractor;
import ge.unicard.pos.interactors.RewardRealizeInteractor;
import ge.unicard.pos.interactors.SendMRZInteractor;
import ge.unicard.pos.interactors.SendOtpInteractor;
import ge.unicard.pos.interactors.SetAllDatabaseInteractor;
import ge.unicard.pos.interactors.SubmitPhoneInteractor;
import ge.unicard.pos.interactors.TestRewersInteractor;
import ge.unicard.pos.interactors.base.GetDeviceInfoInteractorImpl;
import ge.unicard.pos.interactors.base.impl.BonusAccumulationInteractorImpl;
import ge.unicard.pos.interactors.base.impl.CardSearchInetractorImpl;
import ge.unicard.pos.interactors.base.impl.CheckPhoneInteractorImpl;
import ge.unicard.pos.interactors.base.impl.CloseDayInteractorImpl;
import ge.unicard.pos.interactors.base.impl.GetAccountInfoInteractorImpl;
import ge.unicard.pos.interactors.base.impl.GetBalanceInteractorImpl;
import ge.unicard.pos.interactors.base.impl.GetRewardListInteractorImpl;
import ge.unicard.pos.interactors.base.impl.MakePaymentInteractorImpl;
import ge.unicard.pos.interactors.base.impl.PrintReportInteractorImpl;
import ge.unicard.pos.interactors.base.impl.PrinterInteractorImpl;
import ge.unicard.pos.interactors.base.impl.PurchaseInteractorImpl;
import ge.unicard.pos.interactors.base.impl.QrScannerInetractorImpl;
import ge.unicard.pos.interactors.base.impl.RewardRealizeInteractorImpl;
import ge.unicard.pos.interactors.base.impl.SendMRZInteractorImpl;
import ge.unicard.pos.interactors.base.impl.SendOtpInteractorImpl;
import ge.unicard.pos.interactors.base.impl.SetAllDatabaseInteractorImpl;
import ge.unicard.pos.interactors.base.impl.SubmitPhoneInteractorImpl;
import ge.unicard.pos.interactors.base.impl.TestRewersInteractorImpl;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.preferences.AppPreferences;
import ge.unicard.pos.preferences.AppPreferencesImpl;
import ge.unicard.pos.presentation.cards.BalancePresenter;
import ge.unicard.pos.presentation.cards.BalancePresenterImpl;
import ge.unicard.pos.presentation.cards.BonusAccumulationPresenter;
import ge.unicard.pos.presentation.cards.BonusAccumulationPresenterImpl;
import ge.unicard.pos.presentation.cards.MakePaymentPresenter;
import ge.unicard.pos.presentation.cards.MakePaymentPresenterImpl;
import ge.unicard.pos.presentation.cards.PurchasePresenter;
import ge.unicard.pos.presentation.cards.PurchasePresenterImpl;
import ge.unicard.pos.presentation.report_action.ActionReportContract;
import ge.unicard.pos.presentation.report_action.ActionReportPresenter;
import ge.unicard.pos.presentation.reward_preview.RewardRealizeContract;
import ge.unicard.pos.presentation.reward_preview.RewardRealizePresenterImpl;
import ge.unicard.pos.presentation.launcher.LauncherContract;
import ge.unicard.pos.presentation.launcher.LauncherPresenter;
import ge.unicard.pos.presentation.cards.RewardEnterPresenter;
import ge.unicard.pos.presentation.cards.RewardEnterPresenterImpl;
import ge.unicard.pos.presentation.send_mrz.SendMRZContract;
import ge.unicard.pos.presentation.send_mrz.SendMRZPresenterImpl;
import ge.unicard.pos.presentation.send_otp.SendOtpContract;
import ge.unicard.pos.presentation.send_otp.SendOtpPresenter;
import ge.unicard.pos.presentation.transactions.TransactionsContract;
import ge.unicard.pos.presentation.transactions.TransactionsPresenter;
import ge.unicard.pos.utils.DeviceInfo2;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Akaki on 10/23/18.
 */

@Module
public class ApplicationModule {

    private final App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    App provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    AppPreferences provideAppPreferences(@ApplicationContext Context context) {
        return new AppPreferencesImpl(PreferenceManager.getDefaultSharedPreferences(context));
    }

    @Provides
    @Singleton
    PosSession providePosSession(AppPreferences appPreferences) {
        return new PosSession(appPreferences.batchId());
    }

    @Provides
    @Singleton
    DeviceEngine provideDeviceEngine() {
        return APIProxy.getDeviceEngine();
    }

    @SuppressLint("MissingPermission")
    @Provides
    @Singleton
    DeviceInfo2 provideDeviceInfo(@ApplicationContext Context context,
                                  DeviceEngine deviceEngine) {
        final TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceId = telephonyManager.getDeviceId();
        final DeviceInfo2 deviceInfo = new DeviceInfo2(deviceEngine.getDeviceInfo());
        deviceInfo.setDeviceId(deviceId);
        return deviceInfo;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory) {
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.addHeader("Accept", "application/json");
                return chain.proceed(requestBuilder.build());
            }
        }).connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS);

        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
            clientBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.SERVER_ENDPOINT)
                .addConverterFactory(gsonConverterFactory)
                .client(clientBuilder.build())
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory
                .create(new GsonBuilder().create());
    }

    @Provides
    @Singleton
    GeneralModel getGeneralModel(GeneralModel generalModel) {
        generalModel.save();
        return generalModel;
    }

 /*   @Provides
    @Singleton
    CountStatusTransactionDB getCountStatusTransactionDB(CountStatusTransactionDB countStatusTransactionDB) {
        countStatusTransactionDB.save();
        return countStatusTransactionDB;
    } */

    @Provides
    @Singleton
    ApiServices provideApiServices(Retrofit retrofit) {
        return retrofit.create(ApiServices.class);
    }

    @Provides
    QrScannerInteractor provideQrScannerInteractor(QrScannerInetractorImpl interactor) {
        return interactor;
    }

    @Provides
    CardSearchInteractor provideCardSearchInteractor(CardSearchInetractorImpl interactor) {
        return interactor;
    }

    @Provides
    GetAccountInfoInteractor provideAccountInfoInteractor(GetAccountInfoInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    GetRewardListInteractor provideGetRewardListInteractor(GetRewardListInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    BonusAccumulationInteractor provideBonusAccumulationInteractor(BonusAccumulationInteractorImpl interactor) {
        return interactor;
    }


    @Provides
    SendOtpInteractor provideSendOtpInteractor(SendOtpInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    MakePaymentInteractor provideMakePaymentInteractor(MakePaymentInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    CheckPhoneInteractor provideCheckPhoneInteractor(CheckPhoneInteractorImpl interactor){
        return interactor;
    }

    @Provides
    SubmitPhoneInteractor provideSubmitPhoneInteractor(SubmitPhoneInteractorImpl interactor){
        return interactor;
    }

    @Provides
    RewardRealizeInteractor provideRewardRealizeInteractor(RewardRealizeInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    SendMRZInteractor providerSendMRZInteractor(SendMRZInteractorImpl interactor){
        return interactor;
    }

    @Provides
    GetBalanceInteractor provideGetBalanceInteractor(GetBalanceInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    PurchaseInteractor providePurchaseInteractor(PurchaseInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    SetAllDatabaseInteractor provideSetAllDatabaseInteractor(SetAllDatabaseInteractorImpl interactor){
        return interactor;
    }

    @Provides
    GetDeviceInfoInteractor provideGetDeviceInfoInteractor(GetDeviceInfoInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    CloseDayInteractor provideCloseDayInteractor(CloseDayInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    PrinterInteractor providePrinterInteractor(PrinterInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    PrintReportInteractor providePrintReportInteractor(PrintReportInteractorImpl interactor){
        return  interactor;
    }

    @Provides
    BonusAccumulationPresenter provideBonusAccumulation(BonusAccumulationPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    SendMRZContract.Presenter provideSendMRZ(SendMRZPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    MakePaymentPresenter provideMakePaymentPresenter(MakePaymentPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    SendOtpContract.Presenter provideSendOtpPresenter(SendOtpPresenter presenter) {
        return presenter;
    }

    @Provides
    ActionReportContract.Presenter provideActionReportPresenter(ActionReportPresenter presenter){
        return presenter;
    }


    @Provides
    TransactionsContract.Presenter provideTransactionsPresenter(TransactionsPresenter presenter){
        return presenter;
    }


    @Provides
    PurchasePresenter providePurchasePresenter(PurchasePresenterImpl presenter) {
        return presenter;
    }

    @Provides
    LauncherContract.Presenter provideLauncherPresenter(LauncherPresenter presenter) {
        return presenter;
    }

    @Provides
    BalancePresenter provideBalancePresenter(BalancePresenterImpl presenter) {
        return presenter;
    }

    @Provides
    RewardRealizeContract.Presenter rewardRealizePresenter(RewardRealizePresenterImpl presenter) {
        return presenter;
    }

    @Provides
    RewardEnterPresenter rewardEnterPresenter(RewardEnterPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    TestRewersInteractor provideTestRewersInteractor(TestRewersInteractorImpl interactor) {
        return interactor;
    }

}
