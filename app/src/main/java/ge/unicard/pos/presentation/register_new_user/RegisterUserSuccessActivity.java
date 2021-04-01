package ge.unicard.pos.presentation.register_new_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import ge.unicard.pos.R;
import ge.unicard.pos.presentation.launcher.LauncherActivity;

public class RegisterUserSuccessActivity extends AppCompatActivity {

    ImageView imageSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_success);

        imageSuccess = findViewById(R.id.image_success);
        getGifSuccess();


        //create timer task to increment counter
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                // System.out.println("TimerTask executing counter is: " + counter);
               // counter++;
               // finish();
                Intent myIntent = new Intent(RegisterUserSuccessActivity.this, LauncherActivity.class);
                startActivity(myIntent);
            }
        };

        Timer timer = new Timer("MyTimer");
        timer.schedule(timerTask, 2000);
      //
    }

    private void getGifSuccess(){
        // add GIF instruction for NFS-module
        Glide.with(this)
                .load(R.drawable.success)
                .into(imageSuccess);
    }
}
