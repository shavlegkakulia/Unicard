package ge.unicard.pos.interactors.base;

import ge.unicard.pos.networking.ApiServices;

/**
 * Created by Akaki on 10/23/18.
 */

public abstract class ApiServicesInteractor extends BaseServiceInteractor<ApiServices> {

    public ApiServicesInteractor(ApiServices services) {
        super(services);
    }
}
