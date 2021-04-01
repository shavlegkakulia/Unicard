package ge.unicard.pos.presentation.report_action;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.lib.ToolbarInfo;
import ge.unicard.pos.presentation.alert_dialogs.RewersFragment;
import ge.unicard.pos.presentation.transactions.TransactionsFragment;
import ge.unicard.pos.ui.adapters.ActionsAdapter;
import ge.unicard.pos.ui.adapters.Item_decorations.StickyHeaderDecoration;
import ge.unicard.pos.ui.adapters.TransactionsAdapter;
import ge.unicard.pos.ui.base.BaseDialogFragment;
import ge.unicard.pos.ui.base.BaseMvpFragment;
import ge.unicard.pos.ui.listener.RecyclerViewClickListener;
import ge.unicard.pos.ui.widgets.CEditText;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.ActionModeUtils;

import static ge.unicard.pos.db.CodeTransactions.TYPE_BONUS_ACCUMULATION;
import static ge.unicard.pos.db.CodeTransactions.TYPE_MALE_PAYMENT;


public class ActionReportFragment extends BaseMvpFragment<ActionReportContract.View,
        ActionReportContract.Presenter>
        implements ActionReportContract.View{

    @Inject
    ActionReportPresenter presenter;

    FragmentTransaction ft ;
    public static ActionReportFragment newInstance() {
        return new ActionReportFragment();
    }
    //TransactionsAdapter transactionsAdapter;

    @BindView(R.id.btn_print_all)
    Button btnPrintAll;

    @BindView(R.id.linearTransactionsAll)
    LinearLayout linearLayout;

    CTextView idView, amountView, descriptionView, timeView, dateTimeView, statusView;
    ImageView btnPrintAgain, btnRevers;

    FragmentTransaction fragmentTransaction;
    RewersFragment rewersFragment;

    static int testPosition;


    List<GeneralModel> generalModels;

    // for send link on track in MainActivity
    //private RecyclerViewClickListener onSendData;


    public static  ActionReportContract.Presenter actionPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    protected ActionReportPresenter instantiatePresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        actionPresenter = getPresenter();


        return bindView(inflater, R.layout.action_report_fragment, false, new ToolbarInfo.Builder()
                .setTitle(new ToolbarInfo.ActionTitle(getString(R.string.operations) , new View.OnClickListener() {
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
                       /* .setActionButton2(new ToolbarInfo.ActionButton(R.mipmap.tm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }))*/
                        .build());
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

        btnPrintAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onSubmitPrinter();
            }
        });


        generalModels = GeneralModel.listAll(GeneralModel.class);
        //Collections.reverse(generalModels);
        //transactionsAdapter = new TransactionsAdapter(requireContext(), generalModels,getActivity());
        //recyclerView.setAdapter(transactionsAdapter);
        //recyclerView.addItemDecoration(decoration);
        //recyclerView.setLayoutManager(linearLayoutManager);


    }


    public static ActionReportFragment globalSelf;
    public void redraw() {


       // int typeToLoad = -1;
      /*  if (ContextCompat.getColor(requireContext(), R.color.orange) == ActionsAdapter.color) {
            typeToLoad = 0;
        }
        if (ContextCompat.getColor(requireContext(), R.color._8E82EB) == ActionsAdapter.color) {
            typeToLoad = 2;
        } */

        generalModels = GeneralModel.listAll(GeneralModel.class);
        Collections.reverse(generalModels);
        linearLayout.removeAllViews();
        for (GeneralModel generalModel : generalModels) {


                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.transaction_item_view,
                        linearLayout, false);
                idView = view1.findViewById(R.id.transaction_id);
                amountView = view1.findViewById(R.id.transaction_amount);
                descriptionView = view1.findViewById(R.id.transaction_description);
                timeView = view1.findViewById(R.id.transaction_date);
                dateTimeView = view1.findViewById(R.id.transaction_date_time);
                statusView = view1.findViewById(R.id.transaction_status);
                btnPrintAgain = view1.findViewById(R.id.btn_print_again);
                btnRevers = view1.findViewById(R.id.btn_revers);


                String[] words = generalModel.getTranDate().split(" ");

                idView.setText(String.valueOf(generalModel.getCard()));
                statusView.setText(generalModel.getStatus().equals("Reversed") ? "გაუქმებული" : "დადასტურებული");
                dateTimeView.setText(words[0]);
                timeView.setText(words[1]);
                amountView.setText(generalModel.getAmount() + " GEL");
                if (generalModel.getType() == TYPE_BONUS_ACCUMULATION) {
                    descriptionView.setText("დაგროვებული");
                    //rowHolder.linearLayout.setBackgroundResource(R.color._FF0000);
                } else if (generalModel.getType() == TYPE_MALE_PAYMENT) {
                    descriptionView.setText("ქულებით გადახდა");
                } else {
                    descriptionView.setText("გადახდა");
                }
                if (generalModel.status.equals("Reversed")) {
                    statusView.setTextColor(view1.getContext().getResources().getColor(R.color._FF0000));
                }

            if(generalModel.getStatus().equals("Reversed")) {
                    btnRevers.setVisibility(View.GONE);
            }

            btnPrintAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getContext(),
                            "Print again in report fragment", Toast.LENGTH_SHORT);
                    toast.show();
                    //generalModel.
                    getPresenter().onPrintAgain(generalModel);
                }
            });

            btnRevers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!generalModel.getStatus().equals("Reversed")) {
                            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            rewersFragment = RewersFragment.newInstance(generalModel, view1, 1);

                            rewersFragment.show(fragmentTransaction, "RewersAlertDialog");
                        }
                    }
                });


                linearLayout.addView(view1);



        }
    }

    @Override
    public void onResume() {
        globalSelf = this;
        super.onResume();
        redraw();
    }


    @OnClick(R.id.btn_print_all)
    void onPrintAll() {
        Toast toast = Toast.makeText(getContext(),
                "print", Toast.LENGTH_SHORT);
        toast.show();
    }



    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);

    }
}
