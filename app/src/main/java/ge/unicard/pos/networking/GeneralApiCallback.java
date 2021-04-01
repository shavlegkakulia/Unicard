package ge.unicard.pos.networking;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.BaseResponse;
import ge.unicard.pos.networking.messaging.base.BaseResponseMapper;
import ge.unicard.pos.networking.messaging.get_account_info.GetAccountInfoResponse;

public abstract class GeneralApiCallback<T extends BaseResponse, R, M extends BaseResponseMapper<T, R>>
        extends BaseCallback<T> {

    private final ActionHandler mActionHandler;
    private final M mResultMapper;
    private final M mErrorMapper;

    public GeneralApiCallback() {
        this(null);
    }

    public GeneralApiCallback(ActionHandler actionHandler) {
        this(actionHandler, null);
    }

    public GeneralApiCallback(ActionHandler actionHandler,
                              M resultMapper) {
        this(actionHandler, resultMapper, null);
    }

    public GeneralApiCallback(ActionHandler actionHandler,
                              M resultMapper,
                              M errorMapper) {
        mActionHandler = actionHandler;
        mResultMapper = resultMapper;
        mErrorMapper = errorMapper;
    }

    @CallSuper
    @Override
    public void onSuccess(@NonNull T result) {
        if (mResultMapper != null) {
            try {
                final R r = mResultMapper.map(result);
                onMappingSuccess(r);
            } catch (Exception e) {
                onFailure(null, MAPPING_ERROR, null);
            }
        }
    }

    @CallSuper
    @Override
    public void onFailure(@Nullable T result,
                          @FailureReason int reason,
                          @Nullable String description) {
        if (reason != -111) {
            switch (reason) {
                case BaseCallback.NETWORK_FAILURE: {
                    if (mActionHandler != null) {
                        mActionHandler.onNoInternetConnection();
                    }
                }
                break;
                case BaseCallback.API_ERROR: {
                    if (mErrorMapper != null) {
                        try {
                            final R r = mErrorMapper.map(result);
                            onErrorMappingSuccess(r);
                        } catch (Exception e) {
                            onFailure(null, MAPPING_ERROR, null);
                        }
                    } else {

                        if (mActionHandler != null) {
                            mActionHandler.onAnErrorOccurred(description);
                        }
                    }
                }
                break;
                case BaseCallback.MAPPING_ERROR:
                case BaseCallback.SERVER_ERROR:
                case BaseCallback.UNKNOWN: {
                    if (mActionHandler != null) {
                        mActionHandler.onAnErrorOccurred("An Error Occurred");
                    }
                }
                break;
            }
        }
        else{

        }

    }

    @CallSuper
    @Override
    public void onComplete(boolean failed) {
        if (mActionHandler != null) {
            mActionHandler.onFinished();
        }
    }

    @Override
    public void onFinished() {
    }

    public void onMappingSuccess(R result) {
    }


    public void onErrorMappingSuccess(R result) {
    }
}
