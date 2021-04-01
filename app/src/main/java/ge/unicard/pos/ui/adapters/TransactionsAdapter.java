package ge.unicard.pos.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import ge.unicard.pos.R;
import ge.unicard.pos.bus.DeleteItem;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.model.TransactionItem;
import ge.unicard.pos.presentation.alert_dialogs.RewersFragment;
import ge.unicard.pos.ui.adapters.Item_decorations.StickyHeaderDecoration;
import ge.unicard.pos.ui.listener.RecyclerViewClickListener;
import ge.unicard.pos.ui.widgets.CTextView;

import static ge.unicard.pos.db.CodeTransactions.TYPE_BONUS_ACCUMULATION;
import static ge.unicard.pos.db.CodeTransactions.TYPE_MALE_PAYMENT;

/**
 * Created by Akaki on 11/2/18.
 */
public class TransactionsAdapter
        extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>
        implements StickyHeaderDecoration.StickyHeaderAdapter<TransactionsAdapter.HeaderViewHolder> {

    private final LayoutInflater mLayoutInflater;
    FragmentActivity fragmentActivity;
    FragmentTransaction fragmentTransaction;
    RewersFragment rewersFragment;


    View view;
    public static RecyclerView.Adapter transactionsAdapter;


    private static final int CLOSE_DAY_DIALOG_RC = 100;

    public static int   testPosition;;

   /* private final DecimalFormat mAmountFormatter;
    private final SimpleDateFormat mDateFormatter;
    private final SimpleDateFormat mTimeFormatter;*/

    GeneralModel generalModelItem;

    public static List<GeneralModel> generalModels;



    public TransactionsAdapter(Context context, List<GeneralModel> _generalModels, FragmentActivity activity) {
        mLayoutInflater = LayoutInflater.from(context);
        generalModels = _generalModels;
        fragmentActivity = activity;

        //  Log.d("SIZESIZESIZESIZE", generalModels.get(0).getTranDate());//String.valueOf(generalModels.size()));
       /* mAmountFormatter = new DecimalFormat("0.00");
        mDateFormatter = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        mTimeFormatter = new SimpleDateFormat("hh:mm", Locale.getDefault()); */
    }

    public void updateData(List<GeneralModel> dataset) {
        //generalModels.clear();
        //generalModels.addAll(dataset);
       // notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        transactionsAdapter = TransactionsAdapter.this;
        view = mLayoutInflater.inflate(R.layout.transaction_item_view,
                parent, false);

        return new TransactionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder,
                                 int position) {
        if (holder instanceof TransactionViewHolder) {
            TransactionViewHolder rowHolder = (TransactionViewHolder) holder;
            //set values of data here

            List<GeneralModel> generalModelTest  = GeneralModel.listAll(GeneralModel.class);
            if (generalModelTest.get(position).status.equals("Reversed")) {
                ((LinearLayout) view.findViewById(R.id.linear)).setBackgroundColor(view.getContext().getResources().getColor(R.color.red));
            } else {}
            rowHolder.idView.setText(String.valueOf(generalModels.get(position).getCard()));
            rowHolder.amountView.setText(generalModels.get(position).getStatus().equals("Reversed") ? "გაუქმებული" : "დადასტურებული");
            rowHolder.descriptionView.setText(generalModels.get(position).getAmount());
            if (generalModels.get(position).getType() == TYPE_BONUS_ACCUMULATION) {
                rowHolder.dateView.setText("დაგროვებული");
                //rowHolder.linearLayout.setBackgroundResource(R.color._FF0000);
            } else if (generalModels.get(position).getType() == TYPE_MALE_PAYMENT) {
                rowHolder.dateView.setText("ქულებით გადახდა");
            } else {
                rowHolder.dateView.setText("გადახდა");
            }

            Log.d("generalModel_adapter", String.valueOf(generalModels.size()));


            TransactionsAdapter globalThis = this;

            rowHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    //rewersFragment = RewersFragment.newInstance(generalModels.get(position), view);

                    rewersFragment.show(fragmentTransaction, "RewersAlertDialog");
                    testPosition = position;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return generalModels.size();
    }

    @Override
    public long getHeaderId(int position) {
        return position; /*TimeUnit.MILLISECONDS.toDays(mItems.get(position).getDate().getTime());*/
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.transaction_header_item_view,
                parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder,
                                       int position) {
        //holder.dateView.setText(mDateFormatter.format(mItems.get(position).getDate()));

        holder.dateView.setText(generalModels.get(position).getTranDate());

    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.divider)
        View divider;

        @BindView(R.id.transaction_id)
        CTextView idView;

        @BindView(R.id.transaction_amount)
        CTextView amountView;

        @BindView(R.id.transaction_description)
        CTextView descriptionView;

        @BindView(R.id.transaction_date)
        CTextView dateView;

        @BindView(R.id.linear)
        LinearLayout linearLayout;

        TransactionViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.transaction_date)
        CTextView dateView;

        @BindView(R.id.transaction_status)
        CTextView statusView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}