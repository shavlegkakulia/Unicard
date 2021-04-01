package ge.unicard.pos.presentation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blacksharktech.xavierlib.Metrics;
import com.blacksharktech.xavierlib.PhotoUtil;
import com.blacksharktech.xavierlib.XavierActivity;
import com.blacksharktech.xavierlib.XavierField;

import java.util.HashMap;
import java.util.Objects;

import ge.unicard.pos.R;

public class ResultActivity extends AppCompatActivity {
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        double scannerActiveMillis = Metrics.getInstance().getActiveTimeSeconds();
        TextView scannerActive = findViewById(R.id.scanner_active);
        scannerActive.setText(scannerActiveMillis + " s");

        HashMap<XavierField, String> result = (HashMap<XavierField, String>) getIntent().getSerializableExtra(XavierActivity.DOCUMENT_INFO);



        TextView givenName = findViewById(R.id.given_name);
        givenName.setText(result.get(XavierField.GIVEN_NAME));
        TextView surname = findViewById(R.id.surname);
        surname.setText(result.get(XavierField.SURNAME));
        TextView docType = findViewById(R.id.doc_type);
        docType.setText(result.get(XavierField.DOCUMENT_TYPE));
        TextView birthdate = findViewById(R.id.birthdate);
        birthdate.setText(result.get(XavierField.DATE_BIRTH));
        TextView gender = findViewById(R.id.gender);
        gender.setText(result.get(XavierField.SEX));
        TextView expiration = findViewById(R.id.expiration);
        expiration.setText(result.get(XavierField.DATE_EXPIRATION));
        TextView docNumber = findViewById(R.id.doc_number);
        docNumber.setText(result.get(XavierField.DOCUMENT_NUMBER));
        TextView issuing = findViewById(R.id.issuing_state);
        issuing.setText(result.get(XavierField.COUNTRY_ISSUE));
        TextView nationality = findViewById(R.id.nationality);
        nationality.setText(result.get(XavierField.COUNTRY_CITIZEN));
        TextView optional = findViewById(R.id.optional);
        optional.setText(result.get(XavierField.OPTIONAL_DATA));
        TextView optional2 = findViewById(R.id.optional_2);
        optional2.setText(result.get(XavierField.OPTIONAL_DATA_2));
        TextView docNumCheck = findViewById(R.id.doc_num_check);
        docNumCheck.setText(result.get(XavierField.DOCUMENT_NUMBER_CHECK_DIGIT));
        TextView dateBirthCheck = findViewById(R.id.birth_date_check);
        dateBirthCheck.setText(result.get(XavierField.DATE_BIRTH_CHECK_DIGIT));
        TextView dateExpirationCheck = findViewById(R.id.expiration_check);
        dateExpirationCheck.setText(result.get(XavierField.DATE_EXPIRATION_CHECK_DIGIT));
        TextView optionalCheck = findViewById(R.id.optional_check);
        optionalCheck.setText(result.get(XavierField.OPTIONAL_DATA_CHECK_DIGIT));
        TextView compositeCheck = findViewById(R.id.composite_check);
        compositeCheck.setText(result.get(XavierField.COMPOSITE_CHECK_DIGIT));


        TextView rawMRZ = findViewById(R.id.mrzText);
        rawMRZ.setText(result.get(XavierField.RAW_MRZ));
    }

    @Override
    public void onBackPressed() {
        bitmap.recycle();
        super.onBackPressed();
    }

}
