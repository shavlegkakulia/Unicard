package ge.unicard.pos.presentation.close;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ge.unicard.pos.R;
import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.ui.base.BaseDialogFragment;
import ge.unicard.pos.ui.widgets.CTextView;
import ge.unicard.pos.utils.OutlineProvider;

/**
 * Created by Akaki on 11/5/18.
 */

public class ClosDayDialogFragment extends BaseDialogFragment {

    static final String TOTAL_TRANSACTIONS_EXTRA = "total_transactions";


    public static ClosDayDialogFragment newInstance(int totalTransactions
                                                   ) {
        final ClosDayDialogFragment fr = new ClosDayDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(TOTAL_TRANSACTIONS_EXTRA, totalTransactions);
        fr.setArguments(args);
        return fr;
    }

    @BindView(R.id.description_view)
    CTextView descriptionView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = bindView(inflater, R.layout.close_dialog_fragment);
        OutlineProvider.ROUNDED.applyTo(view, R.dimen.dialog_corner_radius);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            descriptionView.setText(getString(R.string.day_close_formated_d,
                    getArguments().getInt(TOTAL_TRANSACTIONS_EXTRA, 0)
            ));
        }
    }

    @OnClick(R.id.dialog_yes_btn)
    void onYesButtonClick() {
        onResult(Activity.RESULT_OK, null);
    }

    @OnClick(R.id.dialog_no_btn)
    void onNoButtonClick() {
        onResult(Activity.RESULT_CANCELED, null);
    }
}
