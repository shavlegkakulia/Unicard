package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ge.unicard.pos.App;
import ge.unicard.pos.R;
import ge.unicard.pos.networking.messaging.base.AccountInfoResponse;

/**
 * Created by Akaki on 10/31/18.
 */
public class PurchaseFragment extends CardsFragment {

    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Inject
    PurchasePresenter presenter;

    @Override
    public void onAttach(Context context) {
        App.getApplicationComponent(context).inject(this);
        super.onAttach(context);
    }

    protected String getActionLabel() {
        return getString(R.string.action_purchase);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @NonNull
    @Override
    protected PurchasePresenter instantiatePresenter() {
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
