package ge.unicard.pos.presentation.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ge.unicard.pos.App;
import ge.unicard.pos.BuildConfig;
import ge.unicard.pos.R;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.di.module.ApplicationModule;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.model.ActionItem;
import ge.unicard.pos.networking.messaging.get_device_info.GetDeviceInfoResponce;
import ge.unicard.pos.networking.messaging.get_device_info.TypeFunctionBtn;
import ge.unicard.pos.presentation.MrzActivity;
import ge.unicard.pos.presentation.cards.CardsActivity;
import ge.unicard.pos.presentation.cards.CardsContract;
import ge.unicard.pos.presentation.cards.Mode;
import ge.unicard.pos.presentation.close.ClosDayDialogFragment;
import ge.unicard.pos.presentation.configuration_info.ConfigurationInfoDialogFragment;
import ge.unicard.pos.presentation.register_new_user.RegisterMRZActivity;
import ge.unicard.pos.presentation.report_action.ActionReportActivity;
import ge.unicard.pos.presentation.reward.RewardEnterActivity;
import ge.unicard.pos.presentation.transactions.TransactionsActivity;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.ui.common.ActionListActivity;
import ge.unicard.pos.ui.common.MessageDialogFragment;
import ge.unicard.pos.utils.RegisterHelper;

import static ge.unicard.pos.presentation.cards.CardsFragment.numColor;
import static ge.unicard.pos.presentation.launcher.LauncherActivity.testDeviceID;

/**
 * Created by Akaki on 10/23/18.
 */

public class LauncherFragment
        extends BaseMvpFragment<LauncherContract.View, LauncherContract.Presenter>
        implements LauncherContract.View {

    Bundle bundle;
    public GetDeviceInfoResponce deviceInfoResponce;

    private static final int CLOSE_DAY_DIALOG_RC = 100;
    ApplicationModule applicationModule;
    List<GeneralModel> generalModels;
    EventBus eventBusLauncher;

    public static FragmentManager fragmentManager;

    @Inject
    LauncherPresenter presenter;

    List<TypeFunctionBtn> typeFunctionBtnList;
    List<ActionItem> actionItemList;
    ActionItem actionItem;

    // block count for view function
    int countUnicard = 0, countGiftCard = 0, countCampainManagment = 0,
            countCloseDay = 0, countReportsTransaction = 0, countRegistrationNewUser = 0;


    // block count for UNICARD options
    int countBonusAccumulation = 0, countBonusPayment = 0, countUnicardReport = 0;
    // block count for GIFTCARD options
    int countGiftcardPayment = 0, countGiftcardGetBalance = 0, countGiftcardReports = 0;

    // block count for 4 activities form func ""Registration New Customer"
    int CR_ScanMRZ = 0, CR_ScanMyFare = 0, CR_ReceivePhone = 0, CR_SwipeCard = 0;

    public static String ARG_DEFAULT_PAGE = "";

    @BindView(R.id.bonus_accumulation_or_make_payment_btn)
    ConstraintLayout btnUnicard;

    @BindView(R.id.buying_and_balance_action_btn)
    ConstraintLayout btnGiftCard;
   /* @BindView(R.id.mrz_btn)
    ConstraintLayout mrzButton;*/

    @BindView(R.id.campaign_management_action_btn)
    ConstraintLayout btnCampainManagment;

    @BindView(R.id.close_action_btn)
    ConstraintLayout btnCloseDay;

    @BindView(R.id.reports_action_btn)
    ConstraintLayout btnReportsTransaction;

    @BindView(R.id.register_new_user)
    ConstraintLayout registrationNewUser;

    @BindView(R.id.image_test)
    ImageView imageGif;


    public static String ARG_BonusAccumulation = "BonusAccumulation";
    public static String ARG_BonusPayment = "BonusPayment";
    public static String ARG_GiftcardPayment = "GiftcardPayment";


    // param check permission for "check Phone" and "check Rewards"
    public static boolean checkPermisionFuncPhone = false;
    public static boolean checkPermisionFuncRewards = false;

    public static String addressReward, merchID, merchName, terminalID;
    public static int urlLogoToolBar;

    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);


        // это вариант рабочий, только нужно проверить приходит ли переменная default в этот метод
        // Intent myIntent = new Intent(getContext(), RewardEnterActivity.class);
        // startActivity(myIntent);


        Log.d("getDefaultPage111", "" + ARG_DEFAULT_PAGE);
        // рварпа


        super.onAttach(context);
    }

    public static LauncherFragment newInstance() {


        return new LauncherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getPresenter().onGetDeviceInfo();
        eventBusLauncher = EventBus.getDefault();

        View view = bindView(inflater, R.layout.launcher_fragment, false,
                new ToolbarInfo.Builder()
                        .setTitle(new ToolbarInfo.ActionTitle(getString(R.string.unicard_merchant_app) + testDeviceID, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Device Config");   //"შეიყვანეთ პაროლი"

                                // Set up the input
                                final EditText input = new EditText(getContext());
                                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                builder.setView(input);

                                // Set up the buttons
                                builder.setPositiveButton("OK", (dialog, which) -> {
                                    if (input.getText().toString().equals("2107")) {
                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                                    }
                                    dialog.cancel();
                                });
                                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                                builder.show();


                            }
                        }))
                        .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                        .setActionButton2(new ToolbarInfo.ActionButton(
                                R.drawable.ic_info,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getPresenter().onInfoButtonClicked();
                                    }
                                }))
                        .build()
        );


        return view;
    }


    private void startDefaultPage() {
        Intent myIntent = new Intent(getActivity(), RewardEnterActivity.class);
        startActivity(myIntent);

        // if (ARG_DEFAULT_PAGE.equals("TEST")){
        // getPresenter().onDefaultPage();
        // }

    }

    @OnClick({R.id.register_new_user})
    void onMrzClicked(View view) {
        // open screen for MRZ data or MRZ scanner
       /* Intent i = new Intent(getContext(), MrzActivity.class);
        startActivity(i); */

        RegisterHelper registerHelper = new RegisterHelper(CR_ScanMRZ, CR_ScanMyFare, CR_ReceivePhone, CR_SwipeCard, view.getContext());
        registerHelper.filterActivitiesRegNewCustomer();
        //Intent i = new Intent(getContext(), RegisterMRZActivity.class);
       // startActivity(i);
    }

    @OnClick({
            R.id.bonus_accumulation_or_make_payment_btn,
            R.id.buying_and_balance_action_btn,
            R.id.campaign_management_action_btn,
            R.id.close_action_btn,
            R.id.reports_action_btn
    })
    void onActionButtonClick(View view) {
        switch (view.getId()) {
            case R.id.bonus_accumulation_or_make_payment_btn:
                getPresenter().onBonusAccumulationOrMakePaymentButtonClick();
                break;
            case R.id.buying_and_balance_action_btn:
                getPresenter().onBuyingAndBalanceActionButtonClick();
                break;
            case R.id.campaign_management_action_btn:
                getPresenter().onCampaignManagementActionButtonClick();
                break;
            case R.id.close_action_btn:
                getPresenter().onCloseActionButtonClick();
                break;
            case R.id.reports_action_btn:
                getPresenter().onReportsActionButtonClick();
                break;
        }
    }

    @NonNull
    @Override
    protected LauncherContract.Presenter instantiatePresenter() {
        return presenter;
    }


    @Override
    public void onDeviceInfoView(GetDeviceInfoResponce responce) {
        // get list for view buttons and info application


        //if(responce.resultCode == 200) {
        deviceInfoResponce = responce;
        typeFunctionBtnList = responce.getData();
        Date date = new Date();
        // add GIF from server
        Glide.with(this)
                .load(responce.getBannerURL() + "?" + date.toString())
                .into(imageGif);


        for (int i = 0; i < typeFunctionBtnList.size(); i++) {
            if (typeFunctionBtnList.get(i).getResourcename().equals("Unicard")) {
                countUnicard++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("Giftcard")) {
                countGiftCard++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("Campaign")) {
                countCampainManagment++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("CloseDay")) {
                countCloseDay++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("MainReports")) {
                countReportsTransaction++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("BonusAccumulation")) {
                countBonusAccumulation++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("BonusPayment")) {
                countBonusPayment++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("UnicardReport")) {
                countUnicardReport++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("GiftcardPayment")) {
                countGiftcardPayment++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("GiftcardGetBalance")) {
                countGiftcardGetBalance++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("GiftcardReports")) {
                countGiftcardReports++;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("CheckMobilePhoneForm")) {
                checkPermisionFuncPhone = true;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("CheckRewardsForm")) {
                checkPermisionFuncRewards = true;
            } else if (typeFunctionBtnList.get(i).getResourcename().equals("ClientRegistration")) {
                countRegistrationNewUser++;
            }
            else if(typeFunctionBtnList.get(i).getResourcename().equals("CR_ScanMRZ")){
                CR_ScanMRZ++;
            }
            else  if(typeFunctionBtnList.get(i).getResourcename().equals("CR_ScanMyFare")){
                CR_ScanMyFare++;
            }
            else  if(typeFunctionBtnList.get(i).getResourcename().equals("CR_ReceivePhone")){
                CR_ReceivePhone++;
            }
            else  if(typeFunctionBtnList.get(i).getResourcename().equals("CR_SwipeCard")){
                CR_SwipeCard++;
            }
            else {
            }
        }


        if (countUnicard != 0) {
            btnUnicard.setVisibility(View.VISIBLE);
        } else {
            btnUnicard.setVisibility(View.GONE);
        }
        if (countGiftCard != 0) {
            btnGiftCard.setVisibility(View.VISIBLE);
        } else {
            btnGiftCard.setVisibility(View.GONE);
        }
        if (countCampainManagment != 0) {
            btnCampainManagment.setVisibility(View.VISIBLE);
        } else {
            btnCampainManagment.setVisibility(View.GONE);
        }
        if (countCloseDay != 0) {
            btnCloseDay.setVisibility(View.VISIBLE);
        } else {
            btnCloseDay.setVisibility(View.GONE);
        }
        if (countReportsTransaction != 0) {
            btnReportsTransaction.setVisibility(View.VISIBLE);
        } else {
            btnReportsTransaction.setVisibility(View.GONE);
        }
        if(countRegistrationNewUser !=0){
            registrationNewUser.setVisibility(View.VISIBLE);
        }
        else {
            registrationNewUser.setVisibility(View.GONE);
        }


        if (responce.getDefaultPage().equals(ARG_BonusAccumulation)) {
            numColor = 3;
            Intent myIntent = new Intent(getActivity(), CardsActivity.class);
            myIntent.putExtra(CardsContract.EXTRA_MODE, Mode.MODE_BONUS_ACCUMULATION);
            startActivity(myIntent);
        } else if (responce.getDefaultPage().equals(ARG_BonusPayment)) {
            numColor = 1;
            Intent myIntent = new Intent(getActivity(), CardsActivity.class);
            myIntent.putExtra(CardsContract.EXTRA_MODE, Mode.MODE_MAKE_PAYMENT);
            startActivity(myIntent);
        } else if (responce.getDefaultPage().equals(ARG_GiftcardPayment)) {
            numColor = 2;
            Intent myIntent = new Intent(getActivity(), CardsActivity.class);
            myIntent.putExtra(CardsContract.EXTRA_MODE, Mode.MODE_PURCHASE);
            startActivity(myIntent);
        } else {
        }
    }

    @Override
    public void onOpenConfigurationInfoPage() {
        final ConfigurationInfoDialogFragment fr = ConfigurationInfoDialogFragment.newInstance();

        if (deviceInfoResponce != null) {


            bundle = new Bundle();
            bundle.putString("MerchantName", deviceInfoResponce.getMerchantName());
            bundle.putString("AddressAdditionalValue", deviceInfoResponce.getAddressAdditionalValue());
            bundle.putString("DeviceID", testDeviceID);
            bundle.putString("MerchantCode", deviceInfoResponce.getMerchantCode());
            bundle.putString("TerminalCode", deviceInfoResponce.getTerminalCode());
            bundle.putString("SimCardNumber", deviceInfoResponce.getSimCardNumber());
            bundle.putString("APIVersion", deviceInfoResponce.getAPIVersion());
            bundle.putString("AppVersion", deviceInfoResponce.getAppVersion());
            fr.setArguments(bundle);
            fr.show(requireFragmentManager(), fr.getTag());
        } else {
            if (isNetworkConnected() == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Internet not found!")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setNegativeButton("Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {

            }
        }
    }

    // Method for check status network(true/false)
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            Log.d("isNetworkConnected", "true");
            return true;

        } else {
            Log.d("isNetworkConnected", "false");
            return false;
        }
    }

    @Override
    public void onOpenBonusAccumulationOrMakePaymentPage() {

        urlLogoToolBar = R.mipmap.logo;

        addressReward = deviceInfoResponce.getAddressAdditionalValue();
        merchID = deviceInfoResponce.getMerchantCode();
        merchName = deviceInfoResponce.getMerchantName();
        terminalID = deviceInfoResponce.getTerminalCode();

        actionItemList = new ArrayList<>();
        if (countUnicard != 0) {
            if (countBonusAccumulation != 0) {
                actionItem = new ActionItem(getString(R.string.action_bonus_accumulation),
                        ContextCompat.getColor(requireContext(), R.color.green),
                        CardsActivity.buildIntent(
                                requireContext(),
                                Mode.MODE_BONUS_ACCUMULATION),
                        null, R.style.PageTheme_DarkGreen, R.mipmap.bonus);
                actionItemList.add(actionItem);
            } else {
            }
            if (countBonusPayment != 0) {
                actionItem = new ActionItem(getString(R.string.action_make_payment),
                        ContextCompat.getColor(requireContext(), R.color.yellow),
                        CardsActivity.buildIntent(
                                requireContext(),
                                Mode.MODE_MAKE_PAYMENT),
                        null, 0, R.mipmap.purchase);
                actionItemList.add(actionItem);
            } else {
            }
            if (countUnicardReport != 0) {
                actionItem = new ActionItem(getString(R.string.action_operations),
                        ContextCompat.getColor(requireContext(), R.color.orange),
                        new Intent(requireContext(), TransactionsActivity.class),
                        null, 0, R.mipmap.history);
                actionItemList.add(actionItem);
            } else {
            }
        } else {
        }

        startActivity(ActionListActivity.buildIntent(
                requireContext(), actionItemList
                /*Arrays.asList(
                        new ActionItem(getString(R.string.action_bonus_accumulation),
                                ContextCompat.getColor(requireContext(), R.color.green),
                                CardsActivity.buildIntent(
                                        requireContext(),
                                        Mode.MODE_BONUS_ACCUMULATION),
                                null, R.style.PageTheme_DarkGreen, R.mipmap.bonus),
                        new ActionItem(getString(R.string.action_make_payment),
                                ContextCompat.getColor(requireContext(), R.color.yellow),
                                CardsActivity.buildIntent(
                                        requireContext(),
                                        Mode.MODE_MAKE_PAYMENT),
                                null, 0, R.mipmap.purchase),
                        new ActionItem(getString(R.string.action_operations),
                                ContextCompat.getColor(requireContext(), R.color.orange),
                                new Intent(requireContext(), TransactionsActivity.class),
                                null, 0, R.mipmap.history)
                )*/, R.style.PageTheme_DarkGreen));
    }

    @Override
    public void onOpenBuyingAndBalancePage() {

        urlLogoToolBar = R.mipmap.tm;
        addressReward = deviceInfoResponce.getAddressAdditionalValue();
        merchID = deviceInfoResponce.getMerchantCode();
        merchName = deviceInfoResponce.getMerchantName();
        terminalID = deviceInfoResponce.getTerminalCode();

        // block count for GIFTCARD options
        //int countGiftcardPayment = 0, countGiftcardGetBalance = 0, countGiftcardReports = 0;
        actionItemList = new ArrayList<>();
        if (countGiftCard != 0) {
            if (countGiftcardPayment != 0) {
                actionItem = new ActionItem(getString(R.string.action_purchase),
                        ContextCompat.getColor(requireContext(), R.color._497FA1),
                        CardsActivity.buildIntent(
                                requireContext(),
                                Mode.MODE_PURCHASE),
                        null, 0, R.mipmap.tm);
                actionItemList.add(actionItem);
            } else {
            }
            if (countGiftcardGetBalance != 0) {
                actionItem = new ActionItem(getString(R.string.action_balance),
                        ContextCompat.getColor(requireContext(), R.color._7A46B5),
                        CardsActivity.buildIntent(
                                requireContext(),
                                Mode.MODE_BALANCE),
                        null, 0, R.mipmap.purchase);
                actionItemList.add(actionItem);
            } else {
            }
            if (countGiftcardReports != 0) {
                actionItem = new ActionItem("ოპერაციები", ContextCompat.getColor(requireContext(), R.color._8E82EB),
                        new Intent(requireContext(), TransactionsActivity.class), null, 0, R.mipmap.history);
                actionItemList.add(actionItem);
            } else {
            }
        }


        startActivity(ActionListActivity.buildIntent(
                requireContext(), actionItemList
                /*Arrays.asList(
                        new ActionItem(getString(R.string.action_purchase),
                                ContextCompat.getColor(requireContext(), R.color._497FA1),
                                CardsActivity.buildIntent(
                                        requireContext(),
                                        Mode.MODE_PURCHASE),
                                null, 0, R.mipmap.tm),
                        new ActionItem(getString(R.string.action_balance),
                                ContextCompat.getColor(requireContext(), R.color._7A46B5),
                                CardsActivity.buildIntent(
                                        requireContext(),
                                        Mode.MODE_BALANCE),
                                null, 0,  R.mipmap.purchase),
                        new ActionItem("ოპერაციები",   ContextCompat.getColor(requireContext(), R.color._8E82EB),
                                new Intent(requireContext(), TransactionsActivity.class), null, 0,R.mipmap.history)
                )*/, R.style.PageTheme_Blue));
    }

    @Override
    public void onOpenCampaignManagementPage() {
        Log.d("OpenCampaignManagement", "onOpenCampaignManagementPage");
        Intent myIntent = new Intent(getContext(), RewardEnterActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        startActivity(myIntent);

        addressReward = deviceInfoResponce.getAddressAdditionalValue();
        merchID = deviceInfoResponce.getMerchantCode();
        merchName = deviceInfoResponce.getMerchantName();
        terminalID = deviceInfoResponce.getTerminalCode();
    }

    @Override
    public void onOpenCloseActionPage(int totalTransactions,
                                      int canceledTransactions,
                                      int successTransactions) {

        // for get size all transaction
        generalModels = GeneralModel.listAll(GeneralModel.class);

        addressReward = deviceInfoResponce.getAddressAdditionalValue();
        merchID = deviceInfoResponce.getMerchantCode();
        merchName = deviceInfoResponce.getMerchantName();
        terminalID = deviceInfoResponce.getTerminalCode();

        final ClosDayDialogFragment fr = ClosDayDialogFragment.newInstance(generalModels.size());
        fr.setTargetFragment(this, CLOSE_DAY_DIALOG_RC);
        fr.show(requireFragmentManager(), fr.getTag());
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CLOSE_DAY_DIALOG_RC && resultCode == Activity.RESULT_OK) {
            getPresenter().onCloseActionConfirmedButtonClick();
            //Log.d("CLOSE_DAY_DIALOG_RC", "test");
        }
    }

    @Override
    public void onOpenReportsActionPage() {
        Intent myIntent = new Intent(getContext(), ActionReportActivity.class);
        addressReward = deviceInfoResponce.getAddressAdditionalValue();
        merchID = deviceInfoResponce.getMerchantCode();
        merchName = deviceInfoResponce.getMerchantName();
        terminalID = deviceInfoResponce.getTerminalCode();
        startActivity(myIntent);
    }

    @Override
    public void onShowDayCloseSuccessMessage() {


        fragmentManager = requireFragmentManager();

        final MessageDialogFragment fr = MessageDialogFragment.newSuccessMessageInstance("დღე წარმატებით დაიხურა");
        fr.show(requireFragmentManager(), fr.getTag());
        getPresenter().onPrintReport();
        /*List<GeneralModel> generalModelList = GeneralModel.listAll(GeneralModel.class);
        if(generalModelList.size() !=0 ) {
            GeneralModel.deleteAll(GeneralModel.class);
        }*/


        GeneralModel.deleteAll(GeneralModel.class);
    }

    public void updateApp() {
        // String android_id = Settings.Secure.getString(getContext().getContentResolver(),
        //    Settings.Secure.ANDROID_ID);

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(getContext());

        appUpdaterUtils.setUpdateJSON("http://unipos.unicard.ge:9000/UniProcessingPrivate.UniPosSVC.svc/GetDeviceRelease/" + testDeviceID)
                .setUpdateFrom(UpdateFrom.JSON)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean aBoolean) {
//                                DownloadApk downloadApk = new DownloadApk(MainActivity.this);
//
//                                downloadApk.startDownloadingApk(update.getUrlToDownload().toString());
                        if (update.getLatestVersionCode() > BuildConfig.VERSION_CODE) {

                            //final MessageDialogFragment fr = MessageDialogFragment.newSuccessMessageInstance("Download update?");
                            //fr.show(requireFragmentManager(), fr.getTag());


                            //get destination to update file and set Uri
                            //TODO: First I wanted to store my update .apk file on internal storage for my app but apparently android does not allow you to open and install
                            //aplication with existing package from there. So for me, alternative solution is Download directory in external storage. If there is better
                            //solution, please inform us in comment
                         /*   String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                            String fileName = "AppName.apk";
                            destination += fileName;
                            final Uri uri = Uri.parse("file://" + destination);

                            //Delete update file if exists
                            File file = new File(destination);
                            if (file.exists())
                                //file.delete() - test this, I think sometimes it doesnt work
                                file.delete();

                            //get url of app on server
                            String url = update.getUrlToDownload().toString();

                            //set downloadmanager
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setDescription("Update");
                            request.setTitle("Update");

                            //set destination
                            request.setDestinationUri(uri);

                            // get download service and enqueue file
                            final DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            final long downloadId = manager.enqueue(request);

                            //set BroadcastReceiver to install app when .apk is downloaded
                            BroadcastReceiver onComplete = new BroadcastReceiver() {
                                public void onReceive(Context ctxt, Intent intent) {
                                    Intent install = new Intent(Intent.ACTION_VIEW);
                                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    install.setDataAndType(uri,
                                            manager.getMimeTypeForDownloadedFile(downloadId));
                                    startActivity(install);
                                    ctxt.unregisterReceiver(this);

                                    //startActivity(getContext().getPackageManager().getLaunchIntentForPackage("ge.unicard.pos"));
                                    //finish();
                                }
                            };
                            //register receiver for when .apk download is compete
                            getContext().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)); */

                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError appUpdaterError) {

                    }
                }).start();
    }

}
