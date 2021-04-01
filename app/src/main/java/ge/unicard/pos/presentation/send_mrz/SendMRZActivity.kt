package ge.unicard.pos.presentation.send_mrz

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blacksharktech.xavierlib.XavierActivity
import com.blacksharktech.xavierlib.XavierField

import ge.unicard.pos.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.HashMap

class SendMRZActivity : BaseActivity() {


   // var result: HashMap<XavierField, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // result = intent.getSerializableExtra(XavierActivity.DOCUMENT_INFO) as HashMap<XavierField, String>

        // card_input.setText(result!![XavierField.GIVEN_NAME].toString())
        // card_input_surname.setText(result!![XavierField.SURNAME])
        // card_input_pn.setText(result!![XavierField.DOCUMENT_NUMBER])
        // card_input_date.setText(result!![XavierField.DATE_BIRTH])
        // card_input_citizenship.setText(result!![XavierField.COUNTRY_CITIZEN])

        val fr = SendMRZFragment2.newInstance(/*result!![XavierField.GIVEN_NAME].toString() + "", result!![XavierField.SURNAME] + "",
                result!![XavierField.DOCUMENT_NUMBER] + "", result!![XavierField.DATE_BIRTH] + "", result!![XavierField.COUNTRY_CITIZEN] + ""*/)
       //
        // fr.arguments = intent.extras
        loadFragment(fr)
    }
}
