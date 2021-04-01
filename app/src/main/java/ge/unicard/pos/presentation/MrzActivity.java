package ge.unicard.pos.presentation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blacksharktech.xavierlib.Customization;
import com.blacksharktech.xavierlib.XavierActivity;
import com.blacksharktech.xavierlib.XavierSDK;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.register_new_user.RegisterMRZActivity;

public class MrzActivity extends AppCompatActivity {
    private static final int XAVIER_RESULT = 1234;

    private Customization customization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCustomXavierUI();

        Button button = findViewById(R.id.startDemoButton);
        button.setOnClickListener(v -> {

        });
        Intent xavierActivity = new Intent(MrzActivity.this, XavierActivity.class);

        XavierSDK.getInstance().setAppKey("PUT IN YOUR LICENSE KEY HERE");
        XavierSDK.getInstance().setCustomization(customization);

        startActivityForResult(xavierActivity, XAVIER_RESULT);
    }

    public void initCustomXavierUI() {

        customization = new Customization();

        customization.flashOffButtonColor = Color.LTGRAY;
        customization.flashOnButtonColor = Color.WHITE;

        // More customization options are available!
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == XAVIER_RESULT){
            if(resultCode == RESULT_OK) {

                // open Activity with result MRZ scan
                Intent intent = new Intent(this, RegisterMRZActivity.class);
                intent.putExtra(XavierActivity.DOCUMENT_INFO, data.getSerializableExtra(XavierActivity.DOCUMENT_INFO));
                intent.putExtra(XavierActivity.DOCUMENT_IMAGE, data.getByteArrayExtra(XavierActivity.DOCUMENT_IMAGE));
                startActivity(intent);

                MrzActivity.this.finish();

            } else if(resultCode == RESULT_CANCELED){
                MrzActivity.this.finish();
                if(data != null) {
                    String error = data.getStringExtra(XavierActivity.ERROR);
                    if (error != null) {
                        Intent intent = new Intent(this, ReadPNActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}
