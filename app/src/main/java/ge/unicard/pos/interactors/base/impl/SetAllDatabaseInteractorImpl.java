package ge.unicard.pos.interactors.base.impl;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import ge.unicard.pos.db.GeneralModel;
import ge.unicard.pos.interactors.SetAllDatabaseInteractor;
import ge.unicard.pos.interactors.base.ApiServicesInteractor;
import ge.unicard.pos.networking.ApiServices;
import ge.unicard.pos.networking.messaging.all_database.AllDatabaseRequest;
import ge.unicard.pos.networking.messaging.all_database.AllDatabaseResponse;
import retrofit2.Call;
import retrofit2.Response;

public class SetAllDatabaseInteractorImpl extends ApiServicesInteractor implements SetAllDatabaseInteractor{

    List<GeneralModel> generalModels;
    String json;
    GeneralModel generalModel;

    @Inject
    public SetAllDatabaseInteractorImpl(ApiServices services) {
        super(services);
    }

    @Override
    public void sendAllDatabase(String batchID, String deviceID) {
        final AllDatabaseRequest request = new AllDatabaseRequest();

        generalModels = GeneralModel.listAll(GeneralModel.class);
        /*generalModel = new GeneralModel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", generalModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        for(int i=0;i<200;i++) {
            generalModel = new GeneralModel();
            generalModel.setAmount("Amount: " + String.valueOf(i));
            generalModel.setBatchID("BatchID: " + String.valueOf(i));
            generalModel.setBonus("Bonus: " + String.valueOf(i));
            generalModel.setCard("Card: " + String.valueOf(i));
            generalModel.setReceiptId("ReceiptID: " + String.valueOf(i));
            generalModel.setResponseCode("RespCode: " + String.valueOf(i));
            generalModel.setStan("Stan: " + String.valueOf(i));
            generalModel.setStatus("Status: " + String.valueOf(i));
            generalModel.setTranDate("Date: " + String.valueOf(i));
            generalModel.setType(i);
            generalModel.setId(1l);

            generalModels.add(generalModel);
        }

         json = new Gson().toJson(generalModels);

        request.batchID = batchID;
        request.deviceID = deviceID;
        request.batchData = json;

        getServices().sendAllDatabase(request).enqueue(new retrofit2.Callback<AllDatabaseResponse>() {
            @Override
            public void onResponse(Call<AllDatabaseResponse> call, Response<AllDatabaseResponse> response) {

            }

            @Override
            public void onFailure(Call<AllDatabaseResponse> call, Throwable t) {

            }
        });

    }
}
