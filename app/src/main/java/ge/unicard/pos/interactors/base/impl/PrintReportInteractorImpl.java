package ge.unicard.pos.interactors.base.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ge.unicard.pos.R;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.di.qualifiers.ApplicationContext;
import ge.unicard.pos.interactors.PrintReportInteractor;
import ge.unicard.pos.interactors.base.BaseDeviceEngineInteractor;
import ge.unicard.pos.lib.PosSession;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.ui.widgets.PrinterLayout;

import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.addressReward;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchID;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.merchName;
import static ge.unicard.pos.presentation.launcher.LauncherFragment.terminalID;

public class PrintReportInteractorImpl  extends BaseDeviceEngineInteractor implements PrintReportInteractor{

    private final LayoutInflater mLayoutInflater;
    private final PosSession mPosSession;

    List<GeneralModel> generalModel;

    String amountPurchaseTran, amountBonusTran , amountBonusRevers, amountMakePaymentTran, amountMakePaymentRevers, amountPurchaseRevers;


    int cAccomulatedCountTRAN = 0, cAccomulatedCountREVERS = 0;
    double cAccomulatedAmountTRAN = 0, cAccomulatedAmountREVERS = 0;

    int cMakePaymentCountTRAN = 0, cMakePaymentCountREVERS = 0;
    double cMakePaymentAmountTRAN = 0, cMakePaymentAmountREVERS = 0;

    int cGiftCardCountTRAN = 0, cGiftCardCountREVERS = 0;
    double cGiftCardAmountTRAN = 0, cGiftCardAmountREVERS = 0;

    static class ViewHolderReport {

    @BindView(R.id.unicard_bonus_layout)
    LinearLayout linearLayoutUNICARDBONUS;

    @BindView(R.id.unicard_payment_layout)
    LinearLayout linearLayoutUNICARD_PAYMENT;

    @BindView(R.id.giftcard_purchase_layout)
    LinearLayout linearLayoutGIFT_PURCHASE;

    @BindView(R.id.printer_view_report)
    PrinterLayout printerLayoutReport;

    @BindView(R.id.reconcilatation)
    CTextView cTextView;

    @BindView(R.id.datetime_report)
    CTextView cTextDate;

    @BindView(R.id.device_id_report)
    CTextView cTextDeviceID;

    @BindView(R.id.terminal_id_report)
    CTextView cTextTerminalID;

    @BindView(R.id.batch_id_report)
    CTextView cTextBatchID;

    @BindView(R.id.merchant_id_report)
    CTextView cTextViewMerchantIDreport;

    @BindView(R.id.address)
    CTextView cTextViewAddress;

    @BindView(R.id.merchant_name_report)
    CTextView cTextViewMercantNameReport;

    /*=================== UNICARD BONUS ACCOMULATED ===================*/

    @BindView(R.id.bonus_accomulated_count_report)
    CTextView cTextAccomulatedCountTRAN;
    @BindView(R.id.bonus_accomulated_amount_report)
    CTextView cTextAccomulatedAmountTRAN;
    @BindView(R.id.reversed_count_report)
    CTextView cTextAccomulatedCountREVERS;
    @BindView(R.id.reversed_amount_report)
    CTextView cTextAccomulatedAmountREVERS;



    /*=================== UNICARD MAKE PAYMENT ===================*/

    @BindView(R.id.make_payment_count_report)
    CTextView cTextMakePaymentCountTRAN;
    @BindView(R.id.amount__make_payment_report)
    CTextView cTextMakePaymentAmountTRAN;
    @BindView(R.id.transaction_reversed_make_payment_count_report)
    CTextView cTextMakePaymentCountREVERS;
    @BindView(R.id.reversed_amount_make_payment_report)
    CTextView cTextMakePaymentAmountREVERS;


    /*=================== GIFT CARD PURCHASE ===================*/

    @BindView(R.id.gift_card_count_report)
    CTextView cTextGiftCardCountTRAN;
    @BindView(R.id.transaction_gift_card_report)
    CTextView cTextGiftCardAmountTRAN;
    @BindView(R.id.transaction_reversed_gift_card_count_report)
    CTextView cTextGiftCardCountREVERS;
    @BindView(R.id.reversed_gift_card_report)
    CTextView cTextGiftCardAmountREVERS;

    }

    @Inject
    public PrintReportInteractorImpl(DeviceEngine deviceEngine, @ApplicationContext Context context, PosSession posSession) {
        super(deviceEngine);
        mLayoutInflater = LayoutInflater.from(context);
        mPosSession = posSession;
    }

    @Override
    public void printReport(@NonNull Callback callback) {
        final View view = mLayoutInflater.inflate(R.layout.print_report,
                null);

        generalModel = GeneralModel.listAll(GeneralModel.class);
        if(generalModel != null || generalModel.size() != 0) {

            final ViewHolderReport viewHolder = new ViewHolderReport();
            ButterKnife.bind(viewHolder, view);

            //String date = currentTime.toString();


            for (int i = 0; i < generalModel.size(); i++) {
                if (generalModel.get(i).type == 0) {
                    if (!generalModel.get(i).status.equals("Reversed")) {
                        cAccomulatedCountTRAN++;
                        cAccomulatedAmountTRAN += Double.parseDouble(generalModel.get(i).amount);
                    } else {
                        cAccomulatedCountREVERS++;
                        cAccomulatedAmountREVERS += Double.parseDouble(generalModel.get(i).amount);
                    }

                }

                if (generalModel.get(i).type == 1) {
                    if (!generalModel.get(i).status.equals("Reversed")) {
                        cMakePaymentCountTRAN++;
                        cMakePaymentAmountTRAN += Double.parseDouble(generalModel.get(i).amount);
                    } else {
                        cMakePaymentCountREVERS++;
                        cMakePaymentAmountREVERS += Double.parseDouble(generalModel.get(i).amount);
                    }
                }
                if(generalModel.get(i).type == 2) {
                    if (!generalModel.get(i).status.equals("Reversed")) {
                        cGiftCardCountTRAN++;
                        cGiftCardAmountTRAN += Double.parseDouble(generalModel.get(i).amount);
                    } else {
                        cGiftCardCountREVERS++;
                        cGiftCardAmountREVERS += Double.parseDouble(generalModel.get(i).amount);
                    }
                }
            }


            //   int cGiftCardCountTRAN = 0, cGiftCardCountREVERS = 0;
            //   double cGiftCardAmountTRAN = 0, cGiftCardAmountREVERS = 0;

        /*if(cAccomulatedCountTRAN == 0){
            viewHolder.linearLayoutUNICARDBONUS.setVisibility(View.GONE);
        }
        else {viewHolder.linearLayoutUNICARDBONUS.setVisibility(View.VISIBLE);}

        if(cMakePaymentCountTRAN == 0){
            viewHolder.linearLayoutUNICARD_PAYMENT.setVisibility(View.GONE);
        }
        else {  viewHolder.linearLayoutUNICARD_PAYMENT.setVisibility(View.VISIBLE);}

        if(cGiftCardAmountTRAN == 0){
            viewHolder.linearLayoutUNICARDBONUS.setVisibility(View.GONE);
        }
        else { viewHolder.linearLayoutUNICARDBONUS.setVisibility(View.INVISIBLE);} */

             amountPurchaseTran = new DecimalFormat("#0.00").format(cGiftCardAmountTRAN);
             amountPurchaseRevers = new DecimalFormat("#0.00").format(cGiftCardAmountREVERS);

            amountBonusTran = new DecimalFormat("#0.00").format(cAccomulatedAmountTRAN);
            amountBonusRevers = new DecimalFormat("#0.00").format(cAccomulatedAmountREVERS);

            amountMakePaymentTran = new DecimalFormat("#0.00").format(cMakePaymentAmountTRAN);
            amountMakePaymentRevers = new DecimalFormat("#0.00").format(cMakePaymentAmountREVERS);

            viewHolder.cTextView.setText("RECONCILATATION");


            android.text.format.DateFormat df = new android.text.format.DateFormat();
            String s = df.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()).toString();

            viewHolder.cTextDate.setText(s);
            viewHolder.cTextDeviceID.setText(testDeviceID);
            viewHolder.cTextBatchID.setText(mPosSession.getOrCreateBatchId());

            //String s = mPosSession.getOrCreateBatchId().toString();

            viewHolder.cTextViewMerchantIDreport.setText(merchID);
            viewHolder.cTextViewAddress.setText(addressReward);
            viewHolder.cTextViewMercantNameReport.setText(merchName);
            viewHolder.cTextTerminalID.setText(terminalID);


            // UNICARD ACCOMULATION BONUS
            viewHolder.cTextAccomulatedAmountTRAN.setText(amountBonusTran);
            viewHolder.cTextAccomulatedCountTRAN.setText(String.valueOf(cAccomulatedCountTRAN));
            viewHolder.cTextAccomulatedCountREVERS.setText(String.valueOf(cAccomulatedCountREVERS));
            viewHolder.cTextAccomulatedAmountREVERS.setText(amountBonusRevers);


            viewHolder.cTextMakePaymentAmountTRAN.setText(amountMakePaymentTran);
            viewHolder.cTextMakePaymentCountTRAN.setText(String.valueOf(cMakePaymentCountTRAN));
            viewHolder.cTextMakePaymentCountREVERS.setText(String.valueOf(cMakePaymentCountREVERS));
            viewHolder.cTextMakePaymentAmountREVERS.setText(amountMakePaymentRevers);

            viewHolder.cTextGiftCardCountTRAN.setText(String.valueOf(cGiftCardCountTRAN));
            viewHolder.cTextGiftCardAmountTRAN.setText(amountPurchaseTran);
            viewHolder.cTextGiftCardCountREVERS.setText(String.valueOf(cGiftCardCountREVERS));
            viewHolder.cTextGiftCardAmountREVERS.setText(amountPurchaseRevers);


            final Bitmap bitmap = viewHolder.printerLayoutReport.getAsBitmap();

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


             cAccomulatedCountTRAN = 0;
            cAccomulatedCountREVERS = 0;
            cAccomulatedAmountTRAN = 0;
            cAccomulatedAmountREVERS = 0;
             cMakePaymentCountTRAN = 0;
             cMakePaymentCountREVERS = 0;
             cMakePaymentAmountTRAN = 0;
             cMakePaymentAmountREVERS = 0;
             cGiftCardCountTRAN = 0;
             cGiftCardCountREVERS = 0;
             cGiftCardAmountTRAN = 0;
            cGiftCardAmountREVERS = 0;
        }
        else {
            Toast toast = Toast.makeText(view.getContext(),
                    "Transactions not found!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
