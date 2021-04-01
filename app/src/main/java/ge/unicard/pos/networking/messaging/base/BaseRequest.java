package ge.unicard.pos.networking.messaging.base;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.ApiConstants;

public class BaseRequest {

    @Nullable
    @SerializedName("app_source")
    @ApiConstants.Source
    public String appSource;

    @Nullable
    @SerializedName("lang")
    @ApiConstants.Lang
    public String lang;
}
