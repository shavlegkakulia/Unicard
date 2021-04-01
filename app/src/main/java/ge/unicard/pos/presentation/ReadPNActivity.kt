package ge.unicard.pos.presentation

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ge.unicard.pos.R
import kotlinx.android.synthetic.main.activity_read_pn.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream


class ReadPNActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_pn)
        goback.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onStart() {
        super.onStart()
        gifImageView.startAnimation()
    }

    override fun onStop() {
        super.onStop()
        gifImageView.stopAnimation()
    }
}
