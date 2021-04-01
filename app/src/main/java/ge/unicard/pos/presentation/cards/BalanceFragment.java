package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;

/**
 * Created by Akaki on 10/31/18.
 */
public class BalanceFragment extends CardsFragment {

    public static BalanceFragment newInstance() {
        return new BalanceFragment();
    }

    @Inject
    BalancePresenter presenter;

    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);
    }

    protected String getActionLabel() {
        return getString(R.string.action_balance);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitButton.setVisibility(View.GONE);
        amountInputLayout.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    protected BalancePresenter instantiatePresenter() {
        return presenter;
    }

    @Override
    public void onShowGetInfo(AccountInfoResponse accountInfoResponse, boolean withError) {

    }

    @Override
    public void onCheckPhomeDialog(String codeRequest, String card) {

    }

    @Override
    public void onStartDialogRewards(String card) {

    }

    @Override
    public void onClickr4wardRealize(String name, String card, Context context) {

    }
}
