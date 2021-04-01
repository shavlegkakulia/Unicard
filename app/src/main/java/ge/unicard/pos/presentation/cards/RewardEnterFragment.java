package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import ge.unicard.pos.App;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;


public class RewardEnterFragment extends CardsFragment {

    public static RewardEnterFragment newInstance() {
        return new RewardEnterFragment();
    }

    @Inject
    RewardEnterPresenter presenter;
    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    protected RewardEnterPresenter instantiatePresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitButton.setVisibility(View.GONE);
        amountInputLayout.setVisibility(View.GONE);
    }

    @Override
    protected String getActionLabel() {
        return null;
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
