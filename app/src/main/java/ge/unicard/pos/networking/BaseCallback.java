package ge.unicard.pos.networking;

import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonParseException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import ge.unicard.pos.interactors.base.ActionHandler;
import ge.unicard.pos.networking.messaging.base.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseCallback<T extends BaseResponse> implements Callback<T> {

    public static final int UNKNOWN = 1;
    public static final int NETWORK_FAILURE = 2;
    public static final int SERVER_ERROR = 3;
    public static final int API_ERROR = 4;
    public static final int MAPPING_ERROR = 5;

    @IntDef({
            UNKNOWN,
            NETWORK_FAILURE,
            SERVER_ERROR,
            API_ERROR,
            MAPPING_ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    public BaseCallback() {
    }

    @Override
    public final void onResponse(@NonNull Call<T> call,
                                 @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            final T body = response.body();
            if (body != null && body.resultCode != null) {
                if (body.resultCode == ApiConstants.RESULT_CODE_OK) {
                    onComplete(false);
                    try {
                        onSuccess(body);
                    } catch (Exception e) {
                        Log.e("Response Processing", "Unhandled exception has occurred:", e);
                        onFailure(null, UNKNOWN, null);
                    }
                } else {
                    onComplete(true);
                    onFailure(body, API_ERROR, body.displayText);
                }
            } else {
                onComplete(true);
                onFailure(null, SERVER_ERROR, null);
            }
        } else {
            onComplete(true);
            onFailure(null, SERVER_ERROR, "" + response.errorBody());
        }
        onFinished();
    }

    @Override
    public final void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
        onComplete(true);
        final @FailureReason int reason;
        if (throwable instanceof SocketException
                || throwable instanceof UnknownHostException
                || throwable instanceof SocketTimeoutException) {
            reason = NETWORK_FAILURE;
        } else if (throwable instanceof JsonParseException) {
            reason = API_ERROR;
        } else {
            reason = UNKNOWN;
        }
        onFailure(null, reason, null);
        onFinished();
    }

    public abstract void onComplete(boolean failed);

    public abstract void onSuccess(@NonNull T result);

    public abstract void onFailure(@Nullable T result,
                          @FailureReason int reason,
                          @Nullable String description);

    public abstract void onFinished();
}
