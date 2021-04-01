package ge.unicard.pos.presentation.send_mrz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.reader.CardInfoEntity;
import com.nexgo.oaf.apiv3.device.reader.CardReader;
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum;
import com.nexgo.oaf.apiv3.device.reader.OnCardInfoListener;

import org.w3c.dom.Text;

import java.util.HashSet;

import javax.inject.Inject;

import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.model.RegisterUser;
import ge.unicard.pos.presentation.launcher.LauncherActivity;
import ge.unicard.pos.presentation.register_new_user.RegNewUserData;
import ge.unicard.pos.presentation.register_new_user.RegisterUserSuccessActivity;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.ui.widgets.CTextInputEditText;

import static com.nexgo.oaf.apiv3.APIProxy.getDeviceEngine;


public class SendMRZFragment2  extends BaseMvpFragment<SendMRZContract.View,
        SendMRZContract.Presenter> implements SendMRZContract.View {

   // CTextInputEditText phone;
    Button back, registerNewUser;
    ImageView back5;
    TextView regName, regCardID, regPassportID, regCountryCitizen, regDateBirth, regSex, regCardNumber, regPhoneNumber;
    RegisterUser registerUser;


    View view;

    @Inject
    SendMRZContract.Presenter presenter;

    public SendMRZFragment2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SendMRZFragment2 newInstance(/*String name, String last_name*/) {
        SendMRZFragment2 fragment = new SendMRZFragment2();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_send_mrz, container, false);
        //phone = view.findViewById(R.id.register_phone_input);
        back = view.findViewById(R.id.back_register);
        back5 = view.findViewById(R.id.back5);
        registerNewUser = view.findViewById(R.id.register_new_user);

        registerUser = new RegisterUser();
        getInfoRegistrationNewUser(view);

        back.setOnClickListener(v -> getActivity().onBackPressed());
        back5.setOnClickListener(v -> getActivity().onBackPressed());
        registerNewUser.setOnClickListener(v -> getPresenter().onSendDataMRZ(registerUser));


        return view;
    }


    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);
    }

    private void getInfoRegistrationNewUser(View view){

        // init TextView
        regName = view.findViewById(R.id.name);
        regCardID = view.findViewById(R.id.card_id);
        regPassportID = view.findViewById(R.id.passport_id);
        regCountryCitizen = view.findViewById(R.id.countryCitizen);
        regDateBirth = view.findViewById(R.id.date_birth);
        regSex = view.findViewById(R.id.sex);
        regCardNumber = view.findViewById(R.id.card_number);
        regPhoneNumber = view.findViewById(R.id.phone_number);

        if(RegNewUserData.phoneNumber.equals("")){
            regPhoneNumber.setVisibility(View.INVISIBLE);
        }else{ regPhoneNumber.setText("მობილური: " +RegNewUserData.phoneNumber);}
        if(RegNewUserData.cardID.equals("")){
            regCardID.setVisibility(View.INVISIBLE);
        }else{ regCardID.setText("ID ბარათის No: " + RegNewUserData.cardID);}
        if(RegNewUserData.cardNumber.equals("")){
            regCardNumber.setVisibility(View.INVISIBLE);
        }else{regCardNumber.setText("უნიქარდის No: " +RegNewUserData.cardNumber);}
        if(RegNewUserData.surname.equals("")){
            regName.setVisibility(View.INVISIBLE);
        }else{regName.setText(RegNewUserData.firstName + " " + RegNewUserData.surname);}
        if(RegNewUserData.countryCitizen.equals("")){
            regCountryCitizen.setVisibility(View.INVISIBLE);
        }else{regCountryCitizen.setText("მოქალაქე: " +RegNewUserData.countryCitizen);}
         if(RegNewUserData.birthDate.equals("")){
            regDateBirth.setVisibility(View.INVISIBLE);
        }else{ regDateBirth.setText("დაბადების თარიღი: " +RegNewUserData.birthDate);}

        if(RegNewUserData.sex.equals("")){
            regSex.setVisibility(View.INVISIBLE);
        } else{ regSex.setText("სქესი: " +RegNewUserData.sex);}

        if(RegNewUserData.documentNumber.equals("")){
            regPassportID.setVisibility(View.INVISIBLE);
        } else{ regPassportID.setText("პირადი No: " +RegNewUserData.documentNumber); }
        // add data for TextView




        registerUser.setFirstName(RegNewUserData.firstName);
        registerUser.setSurName(RegNewUserData.surname);
        registerUser.setBirthDate(RegNewUserData.birthDate);
        registerUser.setCardID(RegNewUserData.cardID);
        registerUser.setCardNumber(RegNewUserData.cardNumber);
        registerUser.setCountryCitizen(RegNewUserData.countryCitizen);
        registerUser.setDocumentNumber(RegNewUserData.documentNumber);
        registerUser.setExpireDate(RegNewUserData.expireDate);
        registerUser.setPhoneNumber(RegNewUserData.phoneNumber);
        registerUser.setSex(RegNewUserData.sex);
    }

    @NonNull
    @Override
    protected SendMRZContract.Presenter instantiatePresenter() {
        return presenter;
    }


    @Override
    public void onSendMRZSuccess() {
        Log.d("UNICARDLOG","Success");

        // Success
        Intent myIntent = new Intent(view.getContext(), RegisterUserSuccessActivity.class);
        view.getContext().startActivity(myIntent);
    }

    @Override
    public void onSendErrorMessage(String message, int resultCode) {

    }
}
