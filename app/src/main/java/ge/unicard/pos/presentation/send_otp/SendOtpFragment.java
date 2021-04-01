package ge.unicard.pos.presentation.send_otp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.bus.OTPReceviedMessage;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.ui.widgets.CEditText;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.ActionModeUtils;

/**
 * Created by Akaki on 11/1/18.
 */
public class SendOtpFragment
        extends BaseMvpFragment<SendOtpContract.View, SendOtpContract.Presenter>
        implements SendOtpContract.View {

    private static final int[] ENABLE_STATE_SET = {android.R.attr.state_enabled};
    private static final int[] DISABLE_STATE_SET = {-android.R.attr.state_enabled};

    public static SendOtpFragment newInstance() {
        return new SendOtpFragment();
    }

    @BindViews({
            R.id.input_box_1,
            R.id.input_box_2,
            R.id.input_box_3,
            R.id.input_box_4
    })
    CEditText[] inputBoxes;

    @BindView(R.id.resend_code_btn)
    CTextView resendCodeButton;

    @BindView(R.id.timer_view)
    CTextView timerView;

    @Nullable
    private CountDownTimerHandler mTimerHandler;

    @Nullable
    private Animator mResendButtonAnimator;

    @Inject
    SendOtpPresenter presenter;

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
        return bindView(inflater, R.layout.sms_verification_fragment, true,
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSmsReceived(OTPReceviedMessage event){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Info")
                .setMessage("SMS კოდი გიგზავნა ბარათის მფლობელთან!")
                .setCancelable(true)
                .setPositiveButton("დახურვა", (dialog, which) -> {

                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // input boxes
        ButterKnife.apply(inputBoxes, new ButterKnife.Action<CEditText>() {
            @Override
            public void apply(final @NonNull CEditText view,
                              final int index) {
                view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
                    @Override
                    public CharSequence filter(final CharSequence source,
                                               int start,
                                               int end,
                                               final Spanned dest,
                                               int dstart,
                                               int dend) {
                        final CharSequence result = super.filter(source, start, end, dest, dstart,
                                dend);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                final int nextFocusedViewIndex = (TextUtils.isEmpty(source) &&
                                        TextUtils.isEmpty(dest) && result == null)
                                        ? index - 1 : index + 1;
                                if (nextFocusedViewIndex >= 0
                                        && nextFocusedViewIndex < inputBoxes.length) {
                                    CEditText view = inputBoxes[nextFocusedViewIndex];
                                    view.requestFocus();
                                    view.selectAll();
                                }
                            }
                        });
                        return result;
                    }
                }});
                //disable copy paste
                ActionModeUtils.removeSelectionAction(view);
            }
        });

        resendCodeButton.setEnabled(false);
        view.post(new Runnable() {
            @Override
            public void run() {
                startTimer();
            }
        });
    }

    @SuppressWarnings("unused")
    private void fillInputBoxes(final String input) {
        ButterKnife.apply(inputBoxes, new ButterKnife.Action<CEditText>() {
            @Override
            public void apply(@NonNull CEditText view,
                              int index) {
                if (index < input.length()) {
                    final InputFilter[] filters = view.getFilters();
                    view.setFilters(new InputFilter[]{});
                    view.setText(String.valueOf(input.charAt(index)));
                    view.setFilters(filters);
                }
            }
        });
    }

    private void startTimer() {
        stopTimer();

        final long duration = TimeUnit.MINUTES.toMillis(1);
        mTimerHandler = new CountDownTimerHandler(
                duration,
                TimeUnit.SECONDS.toMillis(1),
                new CountDownTimerHandler.RemainingCallback() {
                    @Override
                    public void onTick(long durationMs,
                                       long remainingMs) {

                        final String msStr = String.format(
                                Locale.getDefault(),
                                "%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(remainingMs) % TimeUnit.HOURS.toMinutes(1),
                                TimeUnit.MILLISECONDS.toSeconds(remainingMs) % TimeUnit.MINUTES.toSeconds(1));
                        timerView.setText(msStr);
                    }

                    @Override
                    public void onTimeOut() {
                        resendCodeButton.setEnabled(true);
                    }
                });

        final int color1 = resendCodeButton.getTextColors()
                .getColorForState(ENABLE_STATE_SET, 0);
        final int color2 = resendCodeButton.getTextColors()
                .getColorForState(DISABLE_STATE_SET, 0);

        mResendButtonAnimator = ValueAnimator.ofObject(
                new IntEvaluator() {
                    @Override
                    public Integer evaluate(float fraction,
                                            Integer startValue,
                                            Integer endValue) {
                        resendCodeButton.getPaint().setShader(
                                new LinearGradient(
                                        0,
                                        0,
                                        endValue,
                                        0,
                                        new int[]{color1, color2},
                                        new float[]{fraction, fraction + 0.01f},
                                        Shader.TileMode.CLAMP));
                        resendCodeButton.invalidate();

                        return super.evaluate(fraction, startValue, endValue);
                    }
                },
                0, resendCodeButton.getLayout().getWidth());
        mResendButtonAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimationEnd(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resendCodeButton.getPaint().setShader(null);
                resendCodeButton.invalidate();
            }
        });
        mResendButtonAnimator.setDuration(duration);
        mResendButtonAnimator.start();
    }

    private void stopTimer() {
        if (mTimerHandler != null) {
            mTimerHandler.removeCallbacksAndMessages(null);
            mTimerHandler = null;
        }
        if (mResendButtonAnimator != null) {
            mResendButtonAnimator.cancel();
            mResendButtonAnimator = null;
        }
    }

    @Override
    public void onDestroyView() {
        stopTimer();
        super.onDestroyView();
    }

    @OnClick(R.id.verify_btn)
    void onVerifyButtonButtonClicked() {
        final StringBuilder digits = new StringBuilder();
        for (CEditText input : inputBoxes) {
            digits.append(input.getText());
        }
        getPresenter().onSubmitButtonCLicked(digits.toString());
    }

    @OnClick(R.id.resend_code_btn)
    void onResendVerificationCodeButtonClicked() {
        getPresenter().onResendButtonCLicked();
    }

    @NonNull
    @Override
    protected SendOtpContract.Presenter instantiatePresenter() {
        return presenter;
    }

    @Override
    public void onResult(String stan,
                         String otp, String amount) {
        requireActivity().setResult(Activity.RESULT_OK, new Intent()
                .putExtra(SendOtpContract.STAN_EXTRA, stan)
                .putExtra(SendOtpContract.OTP_EXTRA, otp)
        .putExtra(SendOtpContract.AMOUNT_EXTRA, amount));
        requireActivity().finish();
    }

    static class CountDownTimerHandler extends Handler {

        interface RemainingCallback {

            void onTick(long durationMs,
                        long remainingMs);

            void onTimeOut();
        }

        final long durationMs;
        final long delayMs;
        final long endAt;
        final RemainingCallback callback;

        CountDownTimerHandler(long durationMs,
                              long delayMs,
                              RemainingCallback callback) {
            super(Looper.getMainLooper());
            if (durationMs < delayMs) {
                throw new IllegalArgumentException("Duration must be greater than delay");
            }
            this.durationMs = durationMs;
            this.delayMs = delayMs;
            this.callback = callback;

            endAt = SystemClock.elapsedRealtime() + durationMs;
            sendEmptyMessage(0);
        }

        @Override
        public void handleMessage(Message msg) {
            final long remaining = endAt - SystemClock.elapsedRealtime();
            callback.onTick(durationMs, remaining);
            if (remaining > 0) {
                sendEmptyMessageDelayed(0, delayMs);
            } else {
                callback.onTimeOut();
            }
        }
    }
}
