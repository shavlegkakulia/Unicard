package ge.unicard.pos.presentation.reward_preview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import ge.unicard.pos.R;
import ge.unicard.pos.interactors.RewardRealizeInteractor;
import ge.unicard.pos.presentation.cards.CardsContract;
import ge.unicard.pos.ui.base.BaseActivity;


public class RewardPreviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Fragment fr = RewardRealizeFragment.newInstance();
        fr.setArguments(getIntent().getExtras());
        loadFragment(fr);
     /*
        setContentView(R.layout.rewar_preview_fragment);
        activity = RewardPreviewActivity.this;

        buttonClose = findViewById(R.id.btn_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();

            }
        });

        buttonRealize = findViewById(R.id.btn_reward_realize);
        buttonRealize.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\"device_id\":\"864183031492514\",\"reward_code\":\"1234567890\", \"app_source\":\"MOBAPP\",\"lang\":\"ka\"}\r\n");
                Request request = new Request.Builder()
                        .url("http://92.241.68.214:9000/UniProcessing.UniPos.UniPosService.svc/RewardRealize")
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Postman-Token", "b623e7ba-cd01-4bd9-8dc8-9c7650e84fe8")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body1 = response.body();
                    }
                });


            }
        });

        imageView = findViewById(R.id.image_url);

        textRewardName = findViewById(R.id.text_reward_name);
        textValidaty = findViewById(R.id.text_reward_validity);
        textDescription = findViewById(R.id.text_description);

        textCard = findViewById(R.id.text_reward_card);
        textName = findViewById(R.id.text_preview_fullname);

        Picasso.get().load(getIntent().getStringExtra("url_image")).into(imageView);
        textRewardName.setText(getIntent().getStringExtra("reward_name"));
        textValidaty.setText(getIntent().getStringExtra("validaty"));
        textDescription.setText(getIntent().getStringExtra("description"));

        textCard.setText(getIntent().getStringExtra("card"));
        textName.setText(getIntent().getStringExtra("name"));
        mRewardRealizeInteractor.RewardRealize("8938731284", new RewardRealizeInteractor.Callback(this) {
            @Override
            public void onRewardRealize(int ResultCode, String ErrorMessage, String DisplayText) {

            }
        });
*/
    }
}
