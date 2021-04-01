package ge.unicard.pos.presentation.register_new_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.card.mifare.M1CardHandler;
import com.nexgo.oaf.apiv3.device.reader.CardInfoEntity;
import com.nexgo.oaf.apiv3.device.reader.CardReader;
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum;
import com.nexgo.oaf.apiv3.device.reader.OnCardInfoListener;
import java.util.HashSet;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.launcher.LauncherActivity;
import ge.unicard.pos.utils.RegisterHelper;

import static com.nexgo.oaf.apiv3.APIProxy.getDeviceEngine;

public class RegisterNFSActivity extends AppCompatActivity {

   // Button  forward;
    TextView back;
    ImageView backToolbar;
    ImageView gifNFS;


    private int CR_ScanMRZ_2 = 0, CR_ScanMyFare_2 = 0, CR_ReceivePhone_2 = 0, CR_SwipeCard_2 = 0;


    private static final HashSet<CardSlotTypeEnum> sSlotTypes = new HashSet<>();
    CardReader cardReader;


    static {
        sSlotTypes.add(CardSlotTypeEnum.RF);
        //sSlotTypes.add(CardSlotTypeEnum.PSAM1);
       // sSlotTypes.add(CardSlotTypeEnum.PSAM2);
        //sSlotTypes.add(CardSlotTypeEnum.PSAM3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nfs);


        back = findViewById(R.id.register_nfs_back);
        backToolbar = findViewById(R.id.back2);
       // forward = findViewById(R.id.register_nfs_forward);
        gifNFS = findViewById(R.id.open_nfs_scan);
        getGifNFS();

        back.setOnClickListener(v -> RegisterNFSActivity.super.onBackPressed());
        backToolbar.setOnClickListener(v -> RegisterNFSActivity.super.onBackPressed());


        CR_ScanMRZ_2 = getIntent().getIntExtra("CR_ScanMRZ",-1);
        CR_ScanMyFare_2 = getIntent().getIntExtra("CR_ScanMyFare",-1);
        CR_ReceivePhone_2 = getIntent().getIntExtra("CR_ReceivePhone",-1);
        CR_SwipeCard_2 = getIntent().getIntExtra("CR_SwipeCard",-1);

        cardReader = getDeviceEngine().getCardReader();
        startScanCardContactLess();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        getDeviceEngine().getCardReader().stopSearch();
        cardReader.close(CardSlotTypeEnum.RF);
    }

    @Override
    public void onStart(){
        super.onStart();
        startScanCardContactLess();
    }

   /* private void onOpenRegisterPhone(){
        forward.setOnClickListener(v -> onOpenRegisterPhoneActivity());
    }*/

   private void onOpenLauncherActivity(){
       Intent myIntent = new Intent(this, LauncherActivity.class);
       startActivity(myIntent);
   }

    private void onOpenRegisterPhoneActivity(){

        RegisterHelper registerHelper = new RegisterHelper(CR_ScanMRZ_2, CR_ScanMyFare_2, CR_ReceivePhone_2, CR_SwipeCard_2, this);
        registerHelper.filterActivitiesRegNewCustomer();
       // Intent myIntent = new Intent(this, RegisterPhoneActivity.class);
       // startActivity(myIntent);
    }
    private void getGifNFS(){
        // add GIF instruction for NFS-module
        Glide.with(this)
                .load(R.drawable.daadetbarati)
                .into(gifNFS);
    }

    private void startScanCardContactLess(){
        // callback.onSearchStarted();
        //"onReadCardAgain";

        //cardReader.g
        cardReader.searchCard(sSlotTypes, 60, new OnCardInfoListener() {
            @Override
            public void onCardInfo(int retCode,
                                   CardInfoEntity cardInfo) {


                if(cardInfo.getCardExistslot() == CardSlotTypeEnum.RF) {
                    int ret = -1;
                    final M1CardHandler m1CardHandler = getDeviceEngine().getM1CardHandler();

                    cardInfo.getRfCardType();
                    //step 3 , read UID
                    String uid = m1CardHandler.readUid();
                    Log.d("UNICARDLOGid", uid.toUpperCase());
                    Log.d("UNICARDLOGType",   cardInfo.getRfCardType().name());

                    if(!uid.isEmpty()){
                        RegNewUserData.cardID = uid.toUpperCase();
                       // onOpenRegisterPhone();
                        onOpenRegisterPhoneActivity();
                    }

                    }
              /*  Log.d("UNICARDLOG_NFC", "start");
                switch (retCode) {
                    case SdkResult.Success:
                        onMultipleCards();
                        final String cardNo = cardInfo.getCardNo();
                        cardInfo.getRfCardType();
                        if(cardNo!=null) {
                            runOnUiThread(() -> {
                                Log.d("UNICARDLOGNFC", cardNo);
                                //card.setText(cardNo);
                                //forward.setOnClickListener(v -> onOpenRegisterPhone());

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
                } */
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
                    Log.d("UNICARDLOG_NFC", "start");
                });
            }
        });
    }
}
