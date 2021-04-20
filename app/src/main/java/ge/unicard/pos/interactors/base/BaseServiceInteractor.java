package ge.unicard.pos.interactors.base;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Akaki on 10/23/18.
 */

public abstract class BaseServiceInteractor<T> extends BaseInteractor {

    private final T mServices;

    public BaseServiceInteractor(T services) {
        mServices = services;
        Gson gson = new Gson();
        Log.d("BaseServiceInteractor",gson.toJson(services));
    }

    protected final T getServices() {
        Gson gson = new Gson();
        Log.d("getServices",gson.toJson(mServices));
        return mServices;
    }
}
