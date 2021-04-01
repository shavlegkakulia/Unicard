package ge.unicard.pos.presentation.paying;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import ge.unicard.pos.ui.widgets.CTextInputEditText;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;
import ge.unicard.pos.R;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.presentation.send_otp.SendOtpActivity;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.utils.ActionModeUtils;

/**
 * Created by Akaki on 10/31/18.
 */
public class PayingFragment
        extends BaseMvpFragment<PayingContract.View, PayingContract.Presenter>
        implements PayingContract.View {

    public static PayingFragment newInstance() {
        return new PayingFragment();
    }

    @BindView(R.id.card_input)
    CTextInputEditText cardInput;

    @BindView(R.id.amount_input)
    CTextInputEditText amountInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("paymentpayment", "Yes");
        return bindView(inflater, R.layout.card_fragment, true,
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

        ActionModeUtils.removeSelectionAction(cardInput);
        ActionModeUtils.removeSelectionAction(amountInput);
    }

    @OnClick(R.id.submit_btn)
    public void onSubmitButtonClick() {
        getPresenter().onSubmitButtonClicked();
    }

    @Override
    public void openSmsVerificationPage() {

    }

    @NonNull
    @Override
    protected PayingContract.Presenter instantiatePresenter() {
        return new PayingPresenter();
    }
}
