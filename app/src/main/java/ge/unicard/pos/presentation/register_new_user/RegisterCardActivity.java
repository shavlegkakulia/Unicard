package ge.unicard.pos.presentation.register_new_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.reader.CardInfoEntity;
import com.nexgo.oaf.apiv3.device.reader.CardReader;
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum;
import com.nexgo.oaf.apiv3.device.reader.OnCardInfoListener;

import java.util.HashSet;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.send_mrz.SendMRZActivity;
import ge.unicard.pos.ui.widgets.CTextInputEditText;
import ge.unicard.pos.utils.RegisterHelper;

import static com.nexgo.oaf.apiv3.APIProxy.getDeviceEngine;

public class RegisterCardActivity extends AppCompatActivity {

    ImageView image, back4;
    CTextInputEditText card;
    Button back; //, next;



    private int CR_ScanMRZ_4 = 0, CR_ScanMyFare_4 = 0, CR_ReceivePhone_4 = 0, CR_SwipeCard_4 = 0;

    private static final HashSet<CardSlotTypeEnum> sSlotTypes = new HashSet<>();
    CardReader cardReader;

    static {
        sSlotTypes.add(CardSlotTypeEnum.SWIPE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card);



        back = findViewById(R.id.register_card_back);
        back4 = findViewById(R.id.back4);
        back4.setOnClickListener(v -> super.onBackPressed());
       // next = findViewById(R.id.register_card_forward);
        back.setOnClickListener(v -> super.onBackPressed());

        card = findViewById(R.id.register_card_scan_input);
        image = findViewById(R.id.open_card_scan);
        getGif();


        CR_ScanMRZ_4 = getIntent().getIntExtra("CR_ScanMRZ",-1);
        CR_ScanMyFare_4 = getIntent().getIntExtra("CR_ScanMyFare",-1);
        CR_ReceivePhone_4 = getIntent().getIntExtra("CR_ReceivePhone",-1);
        CR_SwipeCard_4 = getIntent().getIntExtra("CR_SwipeCard",-1);

        cardReader = getDeviceEngine().getCardReader();
        startCardScan();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cardReader.close(CardSlotTypeEnum.SWIPE);
    }
    private void getGif() {
        // add GIF instruction
        Glide.with(this)
                .load(R.drawable.barati)
                .into(image);
    }

    private void onOpenRegister(){
        RegisterHelper registerHelper = new RegisterHelper(CR_ScanMRZ_4, CR_ScanMyFare_4, CR_ReceivePhone_4, CR_SwipeCard_4, this);
        registerHelper.filterActivitiesRegNewCustomer();
        //Intent myIntent = new Intent(this, SendMRZActivity.class);
       // startActivity(myIntent);

    }

    private void startCardScan(){
        // callback.onSearchStarted();
        cardReader.searchCard(sSlotTypes, 60, new OnCardInfoListener() {
            @Override
            public void onCardInfo(int retCode,
                                   CardInfoEntity cardInfo) {
                switch (retCode) {
                    case SdkResult.Success:
                        final String cardNo = cardInfo.getCardNo();
                        if(cardNo!=null) {
                            runOnUiThread(() -> {
                                Log.d("UNICARDLOG", cardNo);
                                card.setText(cardNo);
                                RegNewUserData.cardNumber = cardNo;
                               // next.setOnClickListener(v -> onOpenRegisterPhone());
                                onOpenRegister();

                                // callback.onResult(cardNo);
                            });
                        }
                        break;
                    case SdkResult.TimeOut:
                       runOnUiThread(() -> {
                            // callback.onScannerTimeout();
                            cardReader.stopSearch();
                        });
                        break;
                    case SdkResult.Scanner_Customer_Exit:
                       runOnUiThread(() -> {
                            // callback.onCustomerExit();
                            cardReader.stopSearch();
                        });
                        break;
                    default:
                        runOnUiThread(() -> {
                            // callback.onFailed();
                            cardReader.stopSearch();
                        });
                        break;
                }
            }

            @Override
            public void onSwipeIncorrect() {
               runOnUiThread(() -> {
                    // callback.onSwipeAgain();
                });
            }

            @Override
            public void onMultipleCards() {
                runOnUiThread(() -> {
                    // callback.onTooManyCards();
                });
            }
        });
    }

}
