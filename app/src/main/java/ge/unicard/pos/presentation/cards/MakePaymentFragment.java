package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import javax.inject.Inject;

import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;
import ge.unicard.pos.presentation.reward.list_rewards.RewardsListActivity;
import ge.unicard.pos.ui.widgets.CButton;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.OutlineProvider;

import static ge.unicard.pos.presentation.launcher.LauncherFragment.checkPermisionFuncPhone;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.checkPermisionFuncRewards;

/**
 * Created by Akaki on 10/31/18.
 */
public class MakePaymentFragment extends CardsFragment {

    public static MakePaymentFragment newInstance() {
        return new MakePaymentFragment();
    }

    @Inject
    MakePaymentPresenterImpl presenter;

    EditText editTextPhone;

    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);
    }

    protected String getActionLabel() {
        return getString(R.string.action_make_payment);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @NonNull
    @Override
    protected MakePaymentPresenter instantiatePresenter() {
        return presenter;
    }

    @Override
    public void onShowGetInfo(AccountInfoResponse accountInfoResponse, boolean withError) {

    }

    @Override
    public void onCheckPhomeDialog(String codeRequest,String card) {
            openAlertDialog(getView(), card, 0);
    }

    @Override
    public void onStartDialogRewards(String card) {
        openAlertDialog(getView(), card, 1);
    }




    private void openAlertDialog(View view, String card, int typeFunc) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewCustomUI = inflater.inflate(R.layout.check_phone_dialog, null);
        final AlertDialog alertDialog = builder.create();

        CButton cButtonYes = viewCustomUI.findViewById(R.id.dialog_yes_btn_check_phone);
        CButton cButtonNo = viewCustomUI.findViewById(R.id.dialog_no_btn_check_phone);
        CTextView title = viewCustomUI.findViewById(R.id.alert_dialog_title);
        CTextView textView = viewCustomUI.findViewById(R.id.alert_dialog_text);

        editTextPhone = viewCustomUI.findViewById(R.id.editTextPhoneNumber);
        if (typeFunc == 0) {

            if (checkPermisionFuncPhone == true) {

                title.setText(R.string.title_check_phone_and_rewards);
                textView.setText(R.string.check_phone);

                editTextPhone.addTextChangedListener(textWatcher);
                editTextPhone.setText("5");
                editTextPhone.setFocusableInTouchMode(true);
                editTextPhone.setFocusable(true);
                Selection.setSelection(editTextPhone.getText(), editTextPhone.getText().length());

                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                cButtonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().onSubmitPhone(editTextPhone.getText().toString().replace("-", ""), card);
                        alertDialog.cancel();
                    }
                });

                cButtonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.setView(viewCustomUI);
                OutlineProvider.ROUNDED.applyTo(view, R.dimen.dialog_corner_radius);
                alertDialog.show();
            }
        } else {
            if (checkPermisionFuncRewards == true) {

                getPresenter().onCheckListReward(card);
            }
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
         int length_before = 0;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            length_before = s.length();
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (length_before < s.length()) {
                if (s.length() == 3 || s.length() == 6 || s.length() == 9)
                    s.append("-");
                if (s.length() > 3) {
                    if (Character.isDigit(s.charAt(3)))
                        s.insert(3, "-");
                }
                if (s.length() > 6) {
                    if (Character.isDigit(s.charAt(6)))
                        s.insert(6, "-");
                }
                if (s.length() > 9) {
                    if (Character.isDigit(s.charAt(9)))
                        s.insert(9, "-");
                }
            }
        }
    };

    @Override
    public void onClickr4wardRealize(String name, String card, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewCustomUI = inflater.inflate(R.layout.check_phone_dialog, null);
        final AlertDialog alertDialog = builder.create();

        CButton cButtonYes = viewCustomUI.findViewById(R.id.dialog_yes_btn_check_phone);
        CButton cButtonNo = viewCustomUI.findViewById(R.id.dialog_no_btn_check_phone);
        CTextView title = viewCustomUI.findViewById(R.id.alert_dialog_title);
        CTextView textView = viewCustomUI.findViewById(R.id.alert_dialog_text);

        editTextPhone = viewCustomUI.findViewById(R.id.editTextPhoneNumber);
        title.setText(R.string.title_check_phone_and_rewards);
        textView.setText(R.string.check_rewards);

        editTextPhone.setVisibility(View.INVISIBLE);
        editTextPhone.setFocusableInTouchMode(false);
        editTextPhone.setFocusable(false);
        editTextPhone.setWidth(0);
        editTextPhone.setHeight(0);
        cButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), RewardsListActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("card", card);
                startActivity(intent);
                alertDialog.cancel();
            }
        });

        cButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        alertDialog.setView(viewCustomUI);
        alertDialog.show();
    }
}
