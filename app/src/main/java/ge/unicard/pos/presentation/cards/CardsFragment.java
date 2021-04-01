package ge.unicard.pos.presentation.cards;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ge.unicard.pos.R;
import ge.unicard.pos.adapter.RewardAdapter;
import ge.unicard.pos.bus.MessageColor;
import ge.unicard.pos.bus.MessageEvent;
import ge.unicard.pos.bus.MessageEventPurchase;
import ge.unicard.pos.bus.MessageReward;
import ge.unicard.pos.db.TransactionPrintDB;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.presentation.send_otp.SendOtpActivity;
import ge.unicard.pos.presentation.send_otp.SendOtpContract;
import ge.unicard.pos.ui.adapters.ActionsAdapter;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.ui.widgets.CButton;
import ge.unicard.pos.ui.widgets.CTextInputEditText;
import ge.unicard.pos.ui.widgets.CTextInputLayout;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.ActionModeUtils;
import ge.unicard.pos.utils.DecimalDigitsInputFilter;
import ge.unicard.pos.utils.ViewUtils;
import io.realm.Realm;
import io.realm.RealmResults;

import static ge.unicard.pos.presentation.launcher.LauncherFragment.urlLogoToolBar;

/**
 * Created by Akaki on 10/31/18.
 */
public abstract class CardsFragment
        extends BaseMvpFragment<CardsContract.View, CardsContract.Presenter>
        implements CardsContract.View {


    // for print again
    TransactionResponse transactionResponse;
    PurchaseResponse purchaseResponse;
    PrinterInteractor mPrinterInteractor;

    public static int MODE_TRANSACTION;

    //RecyclerView recyclerView;


    GradientDrawable gd_rigth, gd_left;

    static final int SEND_OTP_ACTIVITY_RC = 100;

    @BindView(R.id.card_input_layout)
    CTextInputLayout cardInputLayout;

    @BindView(R.id.card_input)
    CTextInputEditText cardInput;

    @BindView(R.id.amount_input_layout)
    public
    CTextInputLayout amountInputLayout;

    @BindView(R.id.amount_input)
    CTextInputEditText amountInput;

    @BindView(R.id.submit_btn)
    public
    CButton submitButton;

    @BindView(R.id.printer_again_btn)
    CButton printerAgainButton;

    @BindView(R.id.scan_card_btn)
    ConstraintLayout scanCardButton;

    @BindView(R.id.scan_qr_btn)
    ConstraintLayout scanQrButton;

    @BindView(R.id.status_view)
    CTextView statusView;

    public  static int numColor = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        transactionResponse = new TransactionResponse();

        return bindView(inflater, R.layout.card_fragment, true,
                new ToolbarInfo.Builder()
                        .setTitle(new ToolbarInfo.ActionTitle(getActionLabel() , new View.OnClickListener() {
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
                        .setActionButton2(new ToolbarInfo.ActionButton(urlLogoToolBar, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }))
                        .build());
    }

    protected abstract String getActionLabel();

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Drawable drawable = submitButton.getBackground();
        // scanCardButton.setBackground(drawable);
        //submitButton.getBackground().toString();

        //((TextView)scanCardButton.findViewById(R.id.textView3)).setText(getString(R.string.fs_with_card, getActionLabel()));
        //((TextView)scanQrButton.findViewById(R.id.textView2)).setText(getString(R.string.fs_with_qr_code , getActionLabel()));
        submitButton.setText(getActionLabel());
        amountInput.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});


        if (numColor == 0){
            if (ActionsAdapter.color != null) {
                int[] colors = {ActionsAdapter.color, ActionsAdapter.color};
                float[] radiu_rigth = {0f, 0f, 15f, 15f, 0f, 0f, 0f, 0f};
                float[] radius_left = {15f, 15f, 0f, 0f, 0f, 0f, 0f, 0f};
                //create a new gradient color
                gd_rigth = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
                gd_rigth.setCornerRadii(radiu_rigth);
                //apply the button background to newly created drawable gradient

                gd_left = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
                gd_left.setCornerRadii(radius_left);

                scanCardButton.setBackground(gd_rigth);
                //scanCardButton.setBackgroundColor(ActionsAdapter.color);
                scanQrButton.setBackground(gd_left);
                //ActionsAdapter.color = null;
            }

    }
    // make payment
    else if(numColor == 1){
            int[] colors = {Color.parseColor("#fdc215"), Color.parseColor("#fdc215")};
            float[] radiu_rigth = {0f, 0f, 15f, 15f, 0f, 0f, 0f, 0f};
            float[] radius_left = {15f, 15f, 0f, 0f, 0f, 0f, 0f, 0f};
            //create a new gradient color
            gd_rigth = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd_rigth.setCornerRadii(radiu_rigth);
            //apply the button background to newly created drawable gradient

            gd_left = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd_left.setCornerRadii(radius_left);

            scanCardButton.setBackground(gd_rigth);
            //scanCardButton.setBackgroundColor(ActionsAdapter.color);
            scanQrButton.setBackground(gd_left);
            numColor = 0;
        }
        // bonus accomulated
        else if(numColor == 3){
            int[] colors = {Color.parseColor("#90c84d"), Color.parseColor("#90c84d")};
            float[] radiu_rigth = {0f, 0f, 15f, 15f, 0f, 0f, 0f, 0f};
            float[] radius_left = {15f, 15f, 0f, 0f, 0f, 0f, 0f, 0f};
            //create a new gradient color
            gd_rigth = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd_rigth.setCornerRadii(radiu_rigth);
            //apply the button background to newly created drawable gradient

            gd_left = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd_left.setCornerRadii(radius_left);

            scanCardButton.setBackground(gd_rigth);
            //scanCardButton.setBackgroundColor(ActionsAdapter.color);
            scanQrButton.setBackground(gd_left);
            numColor = 0;
        }
        else {
            // numColor == 2
            // purchase
            int[] colors = {Color.parseColor("#497FA1"), Color.parseColor("#497FA1")};
            float[] radiu_rigth = {0f, 0f, 15f, 15f, 0f, 0f, 0f, 0f};
            float[] radius_left = {15f, 15f, 0f, 0f, 0f, 0f, 0f, 0f};
            //create a new gradient color
            gd_rigth = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd_rigth.setCornerRadii(radiu_rigth);
            //apply the button background to newly created drawable gradient

            gd_left = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd_left.setCornerRadii(radius_left);

            scanCardButton.setBackground(gd_rigth);
            //scanCardButton.setBackgroundColor(ActionsAdapter.color);
            scanQrButton.setBackground(gd_left);
            numColor = 0;
        }



       // String s1 =ActionsAdapter.color;
       //String s =R.color._00AB8A;

        ActionModeUtils.removeSelectionAction(cardInput);
        ActionModeUtils.removeSelectionAction(amountInput);
        getPresenter().onScanCardButtonClicked();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }



    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // EventBus for print
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {

        if(event.TYPE_TRANSACTION == 0){

        }
        else {

        }
        printerAgainButton.setVisibility(View.VISIBLE);
        mPrinterInteractor = event.printerInteractor;
        transactionResponse = event.transactionResponse;
        MODE_TRANSACTION = 0;
        if(event.printerInteractor != null){
            amountInput.getText().clear();
            cardInput.getText().clear();
        }
    }

    ;

    // EventBus for print
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventPurchase(MessageEventPurchase event) {
        printerAgainButton.setVisibility(View.VISIBLE);
        mPrinterInteractor = event.printerInteractor;
        purchaseResponse = event.purchaseResponse;
        MODE_TRANSACTION = 1;
        if(event.printerInteractor != null){
            amountInput.getText().clear();
            cardInput.getText().clear();
        }

    }

    ;

    @Subscribe()
    public void onEventColor(MessageColor color) {
        Log.d("jkfvgjsdvisdv", String.valueOf(color));
    }



    @OnClick(R.id.scan_card_btn)
    public void onScanCardButtonClick() {
        clearInputsAnStatus();
        printerAgainButton.setVisibility(View.GONE);

        getPresenter().onScanCardButtonClicked();
    }

    @OnClick(R.id.scan_qr_btn)
    public void onScanQrButtonClick() {
        clearInputsAnStatus();
        printerAgainButton.setVisibility(View.GONE);
        getPresenter().onScanQrButtonClicked();

    }

    @OnClick(R.id.submit_btn)
    public void onSubmitButtonClick() {
        //Log.d("amountDouble", amountInput.getText().toString());

        getPresenter().onSubmitButtonClicked(
                Objects.toString(cardInput.getText(), ""),
                Objects.toString(String.valueOf(amountInput.getText().toString()), "")


        );



        // for remove keyboard if click button
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);


        printerAgainButton.setEnabled(true);


        //amountInput.getText().clear();
       // cardInput.getText().clear();
        //submitButton.setEnabled(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().stopScan();
    }

    @OnClick(R.id.printer_again_btn)
    public void onPrinterAgainButton() {

        //submitButton.setEnabled(false);
        if (MODE_TRANSACTION == 0) {
            mPrinterInteractor.print(transactionResponse,
                    new PrinterInteractor.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailed() {
                        }
                    }
            );
        } else {
            mPrinterInteractor.printPurchase(purchaseResponse, new PrinterInteractor.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailed() {

                }
            });
        }


    }

    @Override
    public void openSendOtpPage(String cardNo) {
        startActivityForResult(SendOtpActivity.buildIntent(requireContext(), amountInput.getText().toString(), cardNo),
                SEND_OTP_ACTIVITY_RC);

    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode
            , Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_OTP_ACTIVITY_RC) {
            if (resultCode == Activity.RESULT_OK) {
                getPresenter().onOtpReceived(
                        data.getStringExtra(SendOtpContract.STAN_EXTRA),
                        data.getStringExtra(SendOtpContract.OTP_EXTRA),
                        cardInput.getText().toString(),
                        amountInput.getText().toString());
                cardInput.setText("");
                amountInput.setText("");
                Log.d("amountInput", amountInput.getText().toString());
                submitButton.setEnabled(false);
            }
            else {
                Log.d("SEND_OTP_ACTIVITY_RC", "error");
            }

        }
    }

    @Override
    public void showSwipeAgainMessage() {
        Toast.makeText(requireContext(), getString(R.string.swip_again), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTooManyCardsMessage() {
        Toast.makeText(requireContext(), getString(R.string.search_toomany_cards), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showScannerTimeOutMessage() {
        Toast.makeText(requireContext(),
                getString(R.string.scanner_timeout), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCustomerExitOutMessage() {
        Toast.makeText(requireContext(),
                getString(R.string.scanner_customer_exit), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showScanCardFailedMessage() {
        Toast.makeText(requireContext(),
                getString(R.string.scanner_fail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFillCardInput(String data) {
        ViewUtils.setTextWithoutHintAnimation(cardInputLayout, data);
    }

    @Override
    public void showPromptSwipeCardMessage() {
        Toast.makeText(requireContext(),
                getString(R.string.prompt_swipe_card), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowStatus(String status,
                             boolean withError) {


        statusView.setText(status);
        statusView.setTextColor(
                ContextCompat.getColor(requireContext(),
                        withError ? R.color._C76654 : R.color._00AB8A));
    }

    @Override
    public void onShowGetInfo(AccountInfoResponse accountInfoResponse,
                              boolean withError) {
        statusView.setText(accountInfoResponse.fullname);
        statusView.setTextColor(
                ContextCompat.getColor(requireContext(),
                        withError ? R.color._C76654 : R.color._00AB8A));
    }

    @Override
    public void onShowAmountFieldIsEmptyMessage() {
        amountInputLayout.setErrorEnabled(true);
        amountInputLayout.setError(getString(R.string.amount_filed_is_empty));
        submitButton.setEnabled(true);
    }

    @Override
    public void onShowCardFieldIsEmptyMessage() {
        cardInputLayout.setErrorEnabled(true);
        cardInputLayout.setError(getString(R.string.card_filed_is_empty));
    }

    @OnTextChanged({
            R.id.card_input,
            R.id.amount_input})
    void onClearErrors() {
        cardInputLayout.setErrorEnabled(false);
        cardInputLayout.setError("");

        amountInputLayout.setErrorEnabled(false);
        amountInputLayout.setError("");
    }

    @Override
    public void onEnableSubmitButton(boolean enabled) {
        submitButton.setEnabled(true);
    }





    private void clearInputsAnStatus() {
        ViewUtils.setTextWithoutHintAnimation(cardInputLayout, "");
        ViewUtils.setTextWithoutHintAnimation(amountInputLayout, "");
        onShowStatus("", false);
        onClearErrors();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
