package ge.unicard.pos.bus;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.ui.adapters.TransactionsAdapter;

public class DeleteItem {

    TransactionsAdapter recyclerView;
    List<GeneralModel> generalModelList;
    int position;


    public TransactionsAdapter getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(TransactionsAdapter recyclerView) {
        this.recyclerView = recyclerView;
    }

    public List<GeneralModel> getGeneralModelList() {
        return generalModelList;
    }

    public void setGeneralModelList(List<GeneralModel> generalModelList) {
        this.generalModelList = generalModelList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DeleteItem(TransactionsAdapter recyclerView, List<GeneralModel> generalModelList, int position) {
        this.recyclerView = recyclerView;
        this.generalModelList = generalModelList;
        this.position = position;

    }
}
