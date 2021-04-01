package ge.unicard.pos.interactors.base.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;

import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ge.unicard.pos.R;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.base.BaseDeviceEngineInteractor;
import ge.unicard.pos.interactors.PrinterInteractor;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.networking.messaging.base.TestResponse;
import ge.unicard.pos.networking.messaging.base.TransactionResponse;
import ge.unicard.pos.networking.messaging.purchase.PurchaseResponse;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.ui.widgets.PrinterLayout;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.activity;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.addressReward;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.terminalID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchName;
import static ge.unicard.pos.presentation.launcher.LauncherPresenter.merchNamePaymentFotPrint;

public class PrinterInteractorImpl
        extends BaseDeviceEngineInteractor
        implements PrinterInteractor {

    static class ViewHolder {


        @BindView(R.id.logotype_check)
        ImageView imageLogoCheck;

        @BindView(R.id.text_title_amount)
        CTextView titleAmount;

        @BindView(R.id.logo_text)
        CTextView logoText;

        @BindView(R.id.linear_bonus)
        LinearLayout linearLayoutBonus;

        @BindView(R.id.linear_auto_code)
        LinearLayout linearLayoutAutoCode;

        @BindView(R.id.linear_receipt_code)
        LinearLayout linearLayoutReceiptCode;

        @BindView(R.id.linear_resp_code)
        LinearLayout linearLayoutRespCode;

        @BindView(R.id.printer_view)
        PrinterLayout printerLayout;

        @BindView(R.id.type_card)
        CTextView typeCard;

        @BindView(R.id.org_name)
        CTextView orgNameView;

        @BindView(R.id.address)
        CTextView addressView;

        @BindView(R.id.terminal_id)
        CTextView terminalIdView;

        @BindView(R.id.merchant_id)
        CTextView merchantIdView;

        @BindView(R.id.receipt_id)
        CTextView receiptIdView;

        @BindView(R.id.tran_type)
        CTextView tranTypeView;

        @BindView(R.id.status)
        CTextView statusView;

        @BindView(R.id.amount)
        CTextView amountView;

        @BindView(R.id.points)
        CTextView pointsView;

        @BindView(R.id.card)
        CTextView cardView;

        @BindView(R.id.resp_code)
        CTextView respCodeView;

        @BindView(R.id.date)
        CTextView dateView;

        @BindView(R.id.text_paid)
        CTextView textPaid;

        @BindView(R.id.text_point)
        CTextView textPoints;

        @BindView(R.id.stan)
        CTextView stan;
    }

    private final LayoutInflater mLayoutInflater;

    @Inject
    public PrinterInteractorImpl(DeviceEngine deviceEngine,
                                 @ApplicationContext Context context) {
        super(deviceEngine);
        mLayoutInflater = LayoutInflater.from(context);
    }

    // for print BONUS_ACCOMULATION
    @Override
    public void print(@NonNull TransactionResponse transactionResponse,
                      @NonNull final Callback callback) {
        final View view = mLayoutInflater.inflate(R.layout.print_layout,
                null);
        final ViewHolder viewHolder = new ViewHolder();
        ButterKnife.bind(viewHolder, view);

        viewHolder.logoText.setVisibility(View.GONE);
        viewHolder.orgNameView.setText(Objects.toString(transactionResponse.merchName, "-"));
        viewHolder.addressView.setText(Objects.toString(transactionResponse.address, "-"));
        viewHolder.terminalIdView.setText(Objects.toString(transactionResponse.terminalId, "-"));
        viewHolder.merchantIdView.setText(Objects.toString(transactionResponse.merchId, "-"));
        viewHolder.receiptIdView.setText(Objects.toString(transactionResponse.receiptId, "-"));
        viewHolder.tranTypeView.setText(Objects.toString(transactionResponse.tran_type, "-"));
        viewHolder.statusView.setText(Objects.toString(transactionResponse.status, "-"));
        viewHolder.amountView.setText(Objects.toString(transactionResponse.amount, "-"));
        viewHolder.pointsView.setText(Objects.toString(transactionResponse.AccumulatedBonus, "-"));
        viewHolder.stan.setText(Objects.toString(transactionResponse.stan, "-"));
        viewHolder.textPaid.setText(R.string.print_layout_paid_accomularion);

       // viewHolder.receiptIdView.setText(Objects.toString(transactionResponse.receiptId, "-"));

        String cardStr = transactionResponse.card;
        final int SHOW_ONLY = 4;
        if (cardStr != null && cardStr.length() > SHOW_ONLY) {
            char[] chars = cardStr.toCharArray();
            Arrays.fill(chars, SHOW_ONLY,
                    Math.max(SHOW_ONLY, cardStr.length() - SHOW_ONLY), '*');
            cardStr = new String(chars);
        }
        viewHolder.cardView.setText(Objects.toString(cardStr, "-"));
        viewHolder.respCodeView.setText(Objects.toString(transactionResponse.respCode, "-"));
        viewHolder.dateView.setText(Objects.toString(transactionResponse.tran_date, "-"));

        final Bitmap bitmap = viewHolder.printerLayout.getAsBitmap();

        final Printer printer = getDeviceEngine().getPrinter();
        printer.initPrinter();
        printer.appendImage(bitmap, AlignEnum.CENTER);

        printer.startPrint(true, new OnPrintListener() {
            @Override
            public void onPrintResult(final int retCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (retCode == SdkResult.Success) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }
                    }
                });
            }
        });
    }

    // for print MAKE_PAYMENT
    @Override
    public void printMakePayment(@NonNull TransactionResponse transactionResponse, @NonNull Callback callback) {
        final View view = mLayoutInflater.inflate(R.layout.print_layout,
                null);
        final ViewHolder viewHolder = new ViewHolder();
        ButterKnife.bind(viewHolder, view);

        viewHolder.logoText.setVisibility(View.GONE);

        viewHolder.orgNameView.setText(Objects.toString(transactionResponse.merchName, "-"));
        viewHolder.addressView.setText(Objects.toString(transactionResponse.address, "-"));
        viewHolder.terminalIdView.setText(Objects.toString(transactionResponse.terminalId, "-"));
        viewHolder.merchantIdView.setText(Objects.toString(transactionResponse.merchId, "-"));
        viewHolder.receiptIdView.setText(Objects.toString(transactionResponse.receiptId, "-"));
        viewHolder.tranTypeView.setText(Objects.toString(transactionResponse.tran_type, "-"));
        viewHolder.statusView.setText(Objects.toString(transactionResponse.status, "-"));
        viewHolder.amountView.setText(Objects.toString(transactionResponse.amount, "-"));
        viewHolder.pointsView.setText(Objects.toString(transactionResponse.bonus, "-"));
        viewHolder.textPaid.setText(R.string.print_layout_paid_make_payment);
        viewHolder.stan.setText(Objects.toString(transactionResponse.stan, "-"));
        //viewHolder.receiptIdView.setText(Objects.toString(transactionResponse.receiptId, "-"));

        String cardStr = transactionResponse.card;
        final int SHOW_ONLY = 4;
        if (cardStr != null && cardStr.length() > SHOW_ONLY) {
            char[] chars = cardStr.toCharArray();
            Arrays.fill(chars, SHOW_ONLY,
                    Math.max(SHOW_ONLY, cardStr.length() - SHOW_ONLY), '*');
            cardStr = new String(chars);
        }
        viewHolder.cardView.setText(Objects.toString(cardStr, "-"));
        viewHolder.respCodeView.setText(Objects.toString(transactionResponse.respCode, "-"));
        viewHolder.dateView.setText(Objects.toString(transactionResponse.tran_date, "-"));

        final Bitmap bitmap = viewHolder.printerLayout.getAsBitmap();

        final Printer printer = getDeviceEngine().getPrinter();
        printer.initPrinter();
        printer.appendImage(bitmap, AlignEnum.CENTER);

        printer.startPrint(true, new OnPrintListener() {
            @Override
            public void onPrintResult(final int retCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (retCode == SdkResult.Success) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }
                    }
                });
            }
        });
    }

    // for print PURCHASE
    @Override
    public void printPurchase(@NonNull PurchaseResponse purchaseResponse, @NonNull Callback callback) {


        final View view = mLayoutInflater.inflate(R.layout.print_layout,
                null);
        final ViewHolder viewHolder = new ViewHolder();
        ButterKnife.bind(viewHolder, view);


        viewHolder.typeCard.setText("GIFTCARD");

        viewHolder.imageLogoCheck.setImageResource(R.mipmap.logoprint_tm);
        viewHolder.logoText.setText("TBILISI MALL");

        viewHolder.orgNameView.setText(Objects.toString(purchaseResponse.merchName, "-"));
        viewHolder.addressView.setText(Objects.toString(purchaseResponse.address, "-"));
        viewHolder.terminalIdView.setText(Objects.toString(purchaseResponse.terminalId, "-"));
        viewHolder.merchantIdView.setText(Objects.toString(purchaseResponse.merchId, "-"));
        viewHolder.receiptIdView.setText(Objects.toString(purchaseResponse.receiptId, "-"));
        viewHolder.tranTypeView.setText(Objects.toString(purchaseResponse.tran_type, "-"));
        viewHolder.statusView.setText(Objects.toString(purchaseResponse.status, "-"));
        viewHolder.amountView.setText(Objects.toString(purchaseResponse.amount, "-"));
        viewHolder.pointsView.setText(Objects.toString("", "-"));
        //viewHolder.receiptIdView.setText(Objects.toString(purchaseResponse.nt, "-"));
        viewHolder.textPoints.setText("");
        viewHolder.textPaid.setText(R.string.print_layout_paid_purchase);
        viewHolder.stan.setText(Objects.toString(purchaseResponse.stan, "-"));
        //viewHolder.receiptIdView.setText(Objects.toString(purchaseResponse.receiptId, "-"));

        String cardStr = purchaseResponse.card;
        final int SHOW_ONLY = 4;
        if (cardStr != null && cardStr.length() > SHOW_ONLY) {
            char[] chars = cardStr.toCharArray();
            Arrays.fill(chars, SHOW_ONLY,
                    Math.max(SHOW_ONLY, cardStr.length() - SHOW_ONLY), '*');
            cardStr = new String(chars);
        }
        viewHolder.cardView.setText(Objects.toString(cardStr, "-"));
        viewHolder.respCodeView.setText(Objects.toString(purchaseResponse.respCode, "-"));
        viewHolder.dateView.setText(Objects.toString(purchaseResponse.tranDate, "-"));

        final Bitmap bitmap = viewHolder.printerLayout.getAsBitmap();

        final Printer printer = getDeviceEngine().getPrinter();
        printer.initPrinter();
        printer.appendImage(bitmap, AlignEnum.CENTER);

        printer.startPrint(true, new OnPrintListener() {
            @Override
            public void onPrintResult(final int retCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (retCode == SdkResult.Success) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void printReward(@NonNull TestResponse testResponse, @NonNull Callback callback, int resultCode) {
        final View view = mLayoutInflater.inflate(R.layout.print_layout,
                null);
        final ViewHolder viewHolder = new ViewHolder();
        ButterKnife.bind(viewHolder, view);

        //viewHolder.typeCard.setText("GIFTCARD");

        viewHolder.terminalIdView.setText(Objects.toString(testResponse.terminalId, "-"));
        viewHolder.merchantIdView.setText(Objects.toString(testResponse.merchId, "-"));
        viewHolder.orgNameView.setText(Objects.toString(testResponse.merchName, "-"));
        viewHolder.tranTypeView.setText(Objects.toString(testResponse.status, "-"));
        viewHolder.textPaid.setVisibility(View.GONE);
        viewHolder.addressView.setText(Objects.toString(testResponse.address, "-"));

        viewHolder.titleAmount.setText("");
        viewHolder.amountView.setText(Objects.toString(testResponse.reward_name, "-"));

        viewHolder.textPoints.setText("ვაუჩერი #");
        viewHolder.pointsView.setText(Objects.toString(testResponse.reward_code, "-"));

        viewHolder.cardView.setText(Objects.toString(testResponse.card, "-"));

        viewHolder.terminalIdView.setText(Objects.toString(testResponse.terminalId, "-"));

        viewHolder.linearLayoutAutoCode.setVisibility(View.GONE);
        viewHolder.linearLayoutRespCode.setVisibility(View.GONE);
        viewHolder.linearLayoutReceiptCode.setVisibility(View.GONE);

        if(resultCode == 200) {
            viewHolder.statusView.setText("დადახტურებული");
        }
        else {
            viewHolder.statusView.setText("უარყოფილი");
        }

        viewHolder.dateView.setText(Objects.toString(testResponse.tranDate, "-"));


        final Bitmap bitmap = viewHolder.printerLayout.getAsBitmap();

        final Printer printer = getDeviceEngine().getPrinter();
        printer.initPrinter();
        printer.appendImage(bitmap, AlignEnum.CENTER);

        printer.startPrint(true, new OnPrintListener() {
            @Override
            public void onPrintResult(final int retCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (retCode == SdkResult.Success) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void printRewers(@NonNull RewersResponse rewersResponse, @NonNull Callback callback) {
        final View view = mLayoutInflater.inflate(R.layout.print_layout,
                null);
        final ViewHolder viewHolder = new ViewHolder();
        ButterKnife.bind(viewHolder, view);


        viewHolder.orgNameView.setText(Objects.toString(merchName, "-"));
        viewHolder.addressView.setText(Objects.toString(addressReward, "-"));
        viewHolder.terminalIdView.setText(Objects.toString(terminalID, "-"));
        viewHolder.merchantIdView.setText(Objects.toString(merchID, "-"));
        viewHolder.receiptIdView.setText(Objects.toString(rewersResponse.receiptId, "-"));
        //viewHolder.receiptIdView.setVisibility(View.GONE);

            viewHolder.tranTypeView.setText(Objects.toString("Revers", "-"));

        viewHolder.statusView.setText(Objects.toString(rewersResponse.status, "-"));
        viewHolder.amountView.setText(Objects.toString(rewersResponse.amount, "-"));

        if(rewersResponse.typeTransactionIDw == 2){
            viewHolder.linearLayoutBonus.setVisibility(View.GONE);
            viewHolder.imageLogoCheck.setImageResource(R.mipmap.logoprint_tm);
            viewHolder.logoText.setText("TBILISI MALL");
        }
        else {
            viewHolder.pointsView.setText(Objects.toString(rewersResponse.bonus, "-"));
        }

        viewHolder.textPaid.setText(R.string.print_layout_paid_make_payment);
        viewHolder.stan.setText(Objects.toString(rewersResponse.stan, "-"));
        //viewHolder.receiptIdView.setText(Objects.toString(transactionResponse.receiptId, "-"));

        String cardStr = rewersResponse.card;
        final int SHOW_ONLY = 4;
        if (cardStr != null && cardStr.length() > SHOW_ONLY) {
            char[] chars = cardStr.toCharArray();
            Arrays.fill(chars, SHOW_ONLY,
                    Math.max(SHOW_ONLY, cardStr.length() - SHOW_ONLY), '*');
            cardStr = new String(chars);
        }
        viewHolder.cardView.setText(Objects.toString(cardStr, "-"));
        viewHolder.respCodeView.setText(Objects.toString(rewersResponse.respCode, "-"));
        viewHolder.dateView.setText(Objects.toString(rewersResponse.tran_date, "-"));

        final Bitmap bitmap = viewHolder.printerLayout.getAsBitmap();

        final Printer printer = getDeviceEngine().getPrinter();
        printer.initPrinter();
        printer.appendImage(bitmap, AlignEnum.CENTER);

        printer.startPrint(true, new OnPrintListener() {
            @Override
            public void onPrintResult(final int retCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (retCode == SdkResult.Success) {
                            callback.onSuccess();
                        } else {
                            callback.onFailed();
                        }
                    }
                });
            }
        });
    }


}
