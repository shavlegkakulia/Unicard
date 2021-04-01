package ge.unicard.pos.presentation.alert_dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.OnClick;
import ge.unicard.pos.R;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.networking.messaging.base.RewersResponse;
import ge.unicard.pos.presentation.launcher.LauncherContract;
import ge.unicard.pos.presentation.report_action.ActionReportFragment;
import ge.unicard.pos.presentation.transactions.TransactionsFragment;
import ge.unicard.pos.ui.base.BaseDialogFragment;
import ge.unicard.pos.ui.widgets.CButton;
import ge.unicard.pos.utils.OutlineProvider;

import static ge.unicard.pos.presentation.report_action.ActionReportFragment.actionPresenter;
import static ge.unicard.pos.presentation.transactions.TransactionsFragment.transactionPresenter;


public class RewersFragment extends BaseDialogFragment {


    CButton cButtonYes, cButtonNo;
    RewersResponse rewersResponse;
    int numIten;
    GeneralModel model;
    View v;
    int type;




    public static RewersFragment newInstance(GeneralModel model, View v, int type) {


        RewersFragment fragment = new RewersFragment();
        fragment.v = v;
        fragment.model = model;
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            numIten = getArguments().getInt("position");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rewers, container, false);

        //List<GeneralModel> generalModels = GeneralModel.listAll(GeneralModel.class);

        cButtonYes = view.findViewById(R.id.rewers_dialog_yes);
        cButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewersResponse = new RewersResponse();
                rewersResponse.amount = model.amount;
                rewersResponse.tran_date = model.tranDate;
                rewersResponse.card = model.card;
                rewersResponse.bonus = model.bonus;
                rewersResponse.status = model.status;
                rewersResponse.tran_type = String.valueOf(model.type);
                rewersResponse.stan = model.stan;
                rewersResponse.respCode = model.responseCode;
                rewersResponse.batchID = model.batchID;
                rewersResponse.typeTransactionIDw = model.type;
                rewersResponse.receiptId = model.receiptId;
                rewersResponse.respCode = "400";

                if (type == 1){

                    actionPresenter.onRevers(rewersResponse, object -> {
                        Log.d("KaxaLog", object.toString());

                        model.status = "Reversed";
                        model.save();


                        //object.toString();


                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                                .setTitle("შეტყობინება")
                                .setMessage(object.toString())
                                .setCancelable(true)
                                .setPositiveButton("ok", (dialog, which) -> {

                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        if (type == 0) {

                            TransactionsFragment.globalSelf.redraw();
                        } else {
                            ActionReportFragment.globalSelf.redraw();
                        }


                    });
            }
            else {
                    transactionPresenter.onRevers(rewersResponse, object -> {
                        Log.d("KaxaLog", object.toString());

                        model.status = "Reversed";
                        model.save();

                        //object.toString();


                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                                .setTitle("შეტყობინება")
                                .setMessage(object.toString())
                                .setCancelable(true)
                                .setPositiveButton("ok", (dialog, which) -> {

                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        if (type == 0) {

                            TransactionsFragment.globalSelf.redraw();
                        } else {
                            ActionReportFragment.globalSelf.redraw();
                        }


                    });
                }



                onResult(Activity.RESULT_OK, null);
            }
        });
        cButtonNo = view.findViewById(R.id.rewers_dialog_no);
        cButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult(Activity.RESULT_CANCELED, null);
            }
        });


        OutlineProvider.ROUNDED.applyTo(view, R.dimen.dialog_corner_radius);
        return view;
    }


    @OnClick(R.id.rewers_dialog_yes)
    void onYesButtonClick() {
        onResult(Activity.RESULT_OK, null);
    }

    @OnClick(R.id.rewers_dialog_no)
    void onNoButtonClick() {
        onResult(Activity.RESULT_CANCELED, null);
        Toast toast = Toast.makeText(getContext(),
                "yes ", Toast.LENGTH_SHORT);
        toast.show();
    }

}
