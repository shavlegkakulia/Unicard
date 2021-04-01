package ge.unicard.pos.interactors.base;

/**
 * Created by Akaki on 10/23/18.
 */

public abstract class BaseServiceInteractor<T> extends BaseInteractor {

    private final T mServices;

    public BaseServiceInteractor(T services) {
        mServices = services;
    }

    protected final T getServices() {
        return mServices;
    }
}
