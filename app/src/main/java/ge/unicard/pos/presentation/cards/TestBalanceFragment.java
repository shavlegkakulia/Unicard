package ge.unicard.pos.presentation.cards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ge.unicard.pos.App;
import ge.unicard.pos.R;


public class TestBalanceFragment extends CardsFragment {

    // TODO: Rename and change types and number of parameters
    public static TestBalanceFragment newInstance() {

        return new TestBalanceFragment();
    }

    @Inject
    TestBalancePresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_balance, container, false);
    }

    @Override
    protected String getActionLabel() {
        return null;
    }


    @NonNull
    @Override
    protected CardsContract.Presenter instantiatePresenter() {
        return null;
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
