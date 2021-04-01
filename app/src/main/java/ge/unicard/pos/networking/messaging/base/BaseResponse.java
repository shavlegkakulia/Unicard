package ge.unicard.pos.networking.messaging.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.ApiConstants;

public class BaseResponse {

    @NonNull
    @SerializedName(value = "ResultCode", alternate = {"Result"})
    @ApiConstants.ResultCode
    public Integer resultCode;

    @Nullable
    @SerializedName(value = "DisplayText", alternate = {"ResultMsg"})
    public String displayText;

    @Nullable
    @SerializedName(value = "ErrorMessage", alternate = {"ErrorMsg"})
    public String errorMessage;
}
