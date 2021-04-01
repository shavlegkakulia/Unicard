package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ge.unicard.pos.interactors.SendMRZInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.model.RegisterUser;
import ge.unicard.pos.networking.ApiConfig;
import ge.unicard.pos.networking.ApiConstants;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.GeneralApiCallback;
import ge.unicard.pos.networking.messaging.base.BaseResponse;
import ge.unicard.pos.networking.messaging.send_mrz.SendMRZMapper;
import ge.unicard.pos.networking.messaging.send_mrz.SendMRZRequest;
import ge.unicard.pos.networking.messaging.send_mrz.SendMRZResponse;
import ge.unicard.pos.utils.DeviceInfo2;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

public class SendMRZInteractorImpl  extends ApiServicesInteractor
        implements SendMRZInteractor {
    private final DeviceInfo2 mDeviceInfo;
    private final PosSession mPosSession;

    @Inject
    public SendMRZInteractorImpl(ApiServices services,  DeviceInfo2 deviceInfo, PosSession posSession) {
        super(services);
        mDeviceInfo = deviceInfo;
        mPosSession = posSession;
    }

    @Override
    public void SendMRZ(@NonNull Callback callback, RegisterUser registerUser) {
        final SendMRZRequest request = new SendMRZRequest();

        if(!testDeviceID.isEmpty()) {
            request.deviceId = testDeviceID;//mDeviceInfo.getDeviceId();
        }
        else {
            request.deviceId = mDeviceInfo.getDeviceId();
        }
        request.lang = ApiConstants.LANG_KA;
        request.appSource = ApiConstants.SOURCE_MOBAPP;

        request.card = registerUser.cardNumber;
        request.phone = registerUser.phoneNumber;

        // MRZ scan
        request.firstName = registerUser.firstName;
        request.surname = registerUser.surName;
        request.country = registerUser.countryCitizen;
        request.documentNumber = registerUser.documentNumber;
        request.birthDate = registerUser.birthDate;
        request.sex = registerUser.sex;
        request.expireDate = registerUser.expireDate;

        request.personalNumber = registerUser.cardID;
        // stan
        request.uid = mPosSession.getOrCreateBatchId();



        /*
        *
        *

        givenName.setText(result.get(XavierField.GIVEN_NAME));
        surname.setText(result.get(XavierField.SURNAME));
        docType.setText(result.get(XavierField.DOCUMENT_TYPE));
        birthdate.setText(result.get(XavierField.DATE_BIRTH));
        gender.setText(result.get(XavierField.SEX));
        expiration.setText(result.get(XavierField.DATE_EXPIRATION));
        docNumber.setText(result.get(XavierField.DOCUMENT_NUMBER));
        issuing.setText(result.get(XavierField.COUNTRY_ISSUE));
        nationality.setText(result.get(XavierField.COUNTRY_CITIZEN));
        optional.setText(result.get(XavierField.OPTIONAL_DATA));
        optional2.setText(result.get(XavierField.OPTIONAL_DATA_2));
        docNumCheck.setText(result.get(XavierField.DOCUMENT_NUMBER_CHECK_DIGIT));
        dateBirthCheck.setText(result.get(XavierField.DATE_BIRTH_CHECK_DIGIT));
        dateExpirationCheck.setText(result.get(XavierField.DATE_EXPIRATION_CHECK_DIGIT));
        optionalCheck.setText(result.get(XavierField.OPTIONAL_DATA_CHECK_DIGIT));
        compositeCheck.setText(result.get(XavierField.COMPOSITE_CHECK_DIGIT));*/

       /*     + "lang":"ka",
              + "app_source":"UniPos",
             +  "device_id":"123456",

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
                "uid":"3332" */

        getServices().sendMRZ(request).enqueue(new GeneralApiCallback<SendMRZResponse, BaseResponse, SendMRZMapper>(callback, new SendMRZMapper()) {
            // result
            @Override
            public void onMappingSuccess(BaseResponse result) {
                callback.onSendMRZ(result.resultCode,
                        result.displayText,
                        result.errorMessage  );
            }

            // error
            @Override
            public void onFailure(@Nullable SendMRZResponse result, int reason, @Nullable String description) {
                callback.onMessageError(result.displayText, result.resultCode);
                super.onFailure(result, reason, description);
            }
        });

    }
}
