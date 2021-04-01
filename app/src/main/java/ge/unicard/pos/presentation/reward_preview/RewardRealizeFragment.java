package ge.unicard.pos.presentation.reward_preview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.interactors.RewardRealizeInteractor;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.networking.messaging.base.TestResponse;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.ui.widgets.CButton;
import ge.unicard.pos.utils.DateTime;

import static ge.unicard.pos.adapter.RewardAdapter.arrayList;
import static ge.unicard.pos.adapter.RewardAdapter.rewardAdapter;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.addressReward;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchName;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.terminalID;

public class RewardRealizeFragment
        extends BaseMvpFragment<RewardRealizeContract.View,
        RewardRealizeContract.Presenter> implements RewardRealizeContract.View {

    int position;



    public static RewardRealizeFragment newInstance() {
        return new RewardRealizeFragment();
    }

    @Inject
    RewardRealizeContract.Presenter presenter;

    @BindView(R.id.image_url)
    ImageView imageView;

    @BindView(R.id.text_reward_name)
    TextView textRewardName;

    @BindView(R.id.text_reward_validity)
    TextView textValidaty;

    @BindView(R.id.text_description)
    TextView textDescription;

    @BindView(R.id.text_reward_card)
    TextView textName;

    @BindView(R.id.text_preview_fullname)
    TextView textCard;



    Button btn;

    TestResponse testResponse;

    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.rewar_preview_fragment, container, false);

        return bindView(inflater, R.layout.rewar_preview_fragment, false,
                new ToolbarInfo.Builder()
                        .setTitle(new ToolbarInfo.ActionTitle(getString(R.string.paying) , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }))
                        .setTitleGravity(Gravity.START)
                        .setActionButton1(new ToolbarInfo.ActionButton(
                                R.drawable.ic_back,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (getActivity() != null) {
                                            getActivity().onBackPressed();
                                        }
                                    }
                                }))
                        .setActionButton2(new ToolbarInfo.ActionButton(R.mipmap.logo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }))
                        .build());
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                });*/

              btn = view.findViewById(R.id.btn_reward_realize);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onRewardRealizeButtonClicked(testResponse);
            }
        });

        //Picasso.get().load(getArgs().getString("url_image")).into(imageView);
        Glide.with(this).load(getArgs().getString("url_image")).into(imageView);

        textRewardName.setText(getArgs().getString("reward_name"));
        textValidaty.setText(getArgs().getString("validaty"));
        textDescription.setText(getArgs().getString("description"));

        textCard.setText(getArgs().getString("card"));
        textName.setText(getArgs().getString("name"));

        position = getArgs().getInt("position");


        //calendar = Calendar.getInstance();
        //dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        //date = simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        testResponse = new TestResponse();
        testResponse.address = addressReward;
        testResponse.card = getArgs().getString("card");
        testResponse.merchId = merchID;
        testResponse.merchName = merchName;
        testResponse.reward_code = getArgs().getString("reward_code");
        testResponse.reward_name = getArgs().getString("reward_name");
        testResponse.status = "Vaucher";
        testResponse.terminalId = terminalID;
        testResponse.tranDate =  currentDateandTime;


        //{"card":"1199110501000500",
        // "reward_code":"3758067790",

         //"reward_name":"სმარტის 50 ლარიანი ვაუჩერი",



        //Log.d("reward_codereward_code", getArgs().getString("reward_code"));
    }


    @OnClick(R.id.btn_close)
    void onBack() {
        getActivity().finish();
    }

    @NonNull
    @Override
    protected RewardRealizeContract.Presenter instantiatePresenter() {
        return presenter;
    }

    @Override
    public void onSuccessGiveReward() {
        btn.setEnabled(false);

        rewardAdapter.notifyItemRemoved(position);
        arrayList.remove(position);
        rewardAdapter.notifyItemRangeChanged(position, arrayList.size());

        openDialog();
    }

    @Override
    public void onSendErrorMessage(String message, int resultCode) {
        if(resultCode != 200){
           // rewardAdapter.notifyItemRemoved(position);
           // arrayList.remove(position);
           // rewardAdapter.notifyItemRangeChanged(position, arrayList.size());
            btn.setEnabled(false);
            // getActivity().finish();
        }
        else {
            rewardAdapter.notifyItemRemoved(position);
            arrayList.remove(position);
            rewardAdapter.notifyItemRangeChanged(position, arrayList.size());
            getActivity().finish();
        }
    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Info");   //"შეიყვანეთ პაროლი"

        builder.setMessage("Success!");
        // Set up the input
        //final TextView message = new TextView(getContext());
       // message.setText("Success!");
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
       // input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
       // builder.setView(message);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.cancel();
            getActivity().onBackPressed();
        });

        builder.show();
    }
}

