package ge.unicard.pos.presentation.register_new_user

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.blacksharktech.xavierlib.Customization
import com.blacksharktech.xavierlib.XavierActivity
import com.blacksharktech.xavierlib.XavierField
import com.blacksharktech.xavierlib.XavierSDK
import com.bumptech.glide.Glide
import ge.unicard.pos.R
import ge.unicard.pos.utils.RegisterHelper
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterMRZActivity : AppCompatActivity() {

    var result: HashMap<XavierField, String>? = null

    // for MRZ scanner
    private var customization: Customization? = null
    private val XAVIER_RESULT = 1234


    private var CR_ScanMRZ_1 = 0
    private var CR_ScanMyFare_1 = 0
    private var CR_ReceivePhone_1 = 0
    private var CR_SwipeCard_1 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initCustomXavierUI()
       // openScannerMRZ()
        getGifNFS()
       /* if(intent != null){
            //result = intent.getSerializableExtra(XavierActivity.DOCUMENT_INFO) as? HashMap<XavierField, String>
            //addDataMRZ(result)
        }*/
        CR_ScanMRZ_1 = intent.getIntExtra("CR_ScanMRZ", -1)
        CR_ScanMyFare_1 = intent.getIntExtra("CR_ScanMyFare", -1)
        CR_ReceivePhone_1 = intent.getIntExtra("CR_ReceivePhone",-1)
        CR_SwipeCard_1 = intent.getIntExtra("CR_SwipeCard", -1)

       /* if(result!=null){
            register_mrz_forward.setOnClickListener { openNFSActivity() }
        } */
        register_mrz_scan.setOnClickListener {openScannerMRZ()  }
        register_mrz_back.setOnClickListener { super.onBackPressed() }
        back1.setOnClickListener { super.onBackPressed() }
    }

    private fun initCustomXavierUI() {
        customization = Customization()
        customization!!.flashOffButtonColor = Color.LTGRAY
        customization!!.flashOnButtonColor = Color.WHITE
        // More customization options are available!
    }

    private fun openScannerMRZ(){
        val xavierActivity = Intent(this@RegisterMRZActivity, XavierActivity::class.java)

        XavierSDK.getInstance().setAppKey("PUT IN YOUR LICENSE KEY HERE")
        XavierSDK.getInstance().setCustomization(customization)

        startActivityForResult(xavierActivity, XAVIER_RESULT)
    }

    private fun openNFSActivity(){
       // val i = Intent(this, RegisterNFSActivity::class.java)
       // startActivity(i)

        val registerHelper = RegisterHelper(CR_ScanMRZ_1, CR_ScanMyFare_1, CR_ReceivePhone_1, CR_SwipeCard_1, this)
        registerHelper.filterActivitiesRegNewCustomer()
    }

    private fun getGifNFS() {
        // add GIF instruction
        Glide.with(this)
                .load(R.drawable.skanireba)
                .into(open_mrz_scan)
    }

    private fun addDataMRZ(result: HashMap<XavierField, String>?){
        if(result !=null) {
            /*name.text = result[XavierField.GIVEN_NAME]
            last_name.text = result[XavierField.SURNAME]
            doc_number.text = result[XavierField.DOCUMENT_NUMBER]
            date_birth.text = result[XavierField.DATE_BIRTH]
            country_citizen.text = result[XavierField.COUNTRY_CITIZEN]
            sex.text = result[XavierField.SEX]
            expire_date.text = result[XavierField.DATE_EXPIRATION] */

            RegNewUserData.firstName = result[XavierField.GIVEN_NAME].toString()
            RegNewUserData.surname = result[XavierField.SURNAME].toString()
            RegNewUserData.documentNumber = result[XavierField.DOCUMENT_NUMBER].toString()
            RegNewUserData.birthDate  = result[XavierField.DATE_BIRTH].toString()
            RegNewUserData.countryCitizen = result[XavierField.COUNTRY_CITIZEN].toString()
            RegNewUserData.expireDate = result[XavierField.DATE_EXPIRATION].toString()

            if(result[XavierField.SEX].toString() == "F") {
                RegNewUserData.sex = "მდედრობითი"
            }
            else{
                RegNewUserData.sex = "მამრობითი"
            }

            openNFSActivity()


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == XAVIER_RESULT) {
            if (resultCode == Activity.RESULT_OK) {

                result = data!!.getSerializableExtra(XavierActivity.DOCUMENT_INFO) as HashMap<XavierField, String>?
                addDataMRZ(result)
                // open Activity with result MRZ scan
               /* val intent = Intent(this, RegisterMRZActivity::class.java)
                intent.putExtra(XavierActivity.DOCUMENT_INFO, data!!.getSerializableExtra(XavierActivity.DOCUMENT_INFO))
                intent.putExtra(XavierActivity.DOCUMENT_IMAGE, data.getByteArrayExtra(XavierActivity.DOCUMENT_IMAGE))
                startActivity(intent) */

            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data != null) {
                    val error = data.getStringExtra(XavierActivity.ERROR)
                    if (error != null) {
                       // val intent = Intent(this, ReadPNActivity::class.java)
                       // startActivity(intent)
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}


