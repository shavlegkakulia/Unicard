package ge.unicard.pos.presentation.register_new_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import ge.unicard.pos.R;
import ge.unicard.pos.ui.widgets.CTextInputEditText;
import ge.unicard.pos.utils.RegisterHelper;

public class RegisterPhoneActivity extends AppCompatActivity {


    ImageView backToolbar;
    Button back, next;

    CTextInputEditText phoneNumber;

    private int CR_ScanMRZ_3 = 0, CR_ScanMyFare_3 = 0, CR_ReceivePhone_3 = 0, CR_SwipeCard_3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        backToolbar = findViewById(R.id.back3);
        backToolbar.setOnClickListener(v -> RegisterPhoneActivity.super.onBackPressed());

        phoneNumber = findViewById(R.id.register_phone_input_reg);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //phoneNumber.requestFocus();
        next = findViewById(R.id.register_phone_forward);
        next.setOnClickListener(v -> onChangePhone());
       // onChangePhone();

        CR_ScanMRZ_3 = getIntent().getIntExtra("CR_ScanMRZ",-1);
        CR_ScanMyFare_3 = getIntent().getIntExtra("CR_ScanMyFare",-1);
        CR_ReceivePhone_3 = getIntent().getIntExtra("CR_ReceivePhone",-1);
        CR_SwipeCard_3 = getIntent().getIntExtra("CR_SwipeCard",-1);

        back = findViewById(R.id.register_phone_back);
        back.setOnClickListener(v -> RegisterPhoneActivity.super.onBackPressed());

    }

    private void onStartCardScan(String number){
        RegNewUserData.phoneNumber = number;
        RegisterHelper registerHelper = new RegisterHelper(CR_ScanMRZ_3, CR_ScanMyFare_3, CR_ReceivePhone_3, CR_SwipeCard_3, this);
        registerHelper.filterActivitiesRegNewCustomer();
        //Intent myIntent = new Intent(this, RegisterCardActivity.class);
       // startActivity(myIntent);
    }

    private void onChangePhone(){

        String phoneNum = phoneNumber.getText().toString();
        if(!phoneNum.matches("")) {
           // Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
           // return;
            onStartCardScan(phoneNum);

        }
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
