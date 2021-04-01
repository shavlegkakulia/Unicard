package ge.unicard.pos.networking.messaging.bonus_accumulation;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import ge.unicard.pos.networking.messaging.base.TransactionResponse;

public class BonusAccumulationResponse extends TransactionResponse {

    @Nullable
    @SerializedName("bonus")
    public String bonus_accomulation_test;
}
