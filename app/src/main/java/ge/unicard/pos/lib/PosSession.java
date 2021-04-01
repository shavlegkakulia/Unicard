package ge.unicard.pos.lib;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.UUID;

import ge.unicard.pos.preferences.PreferenceType;

public class PosSession {

    private final PreferenceType<String> mBatchIdPref;

    public PosSession(PreferenceType<String> batchIdPref){
        mBatchIdPref = batchIdPref;
    }

    @NonNull
    public String getOrCreateBatchId(){
        String uuid = mBatchIdPref.getValue();
        if(TextUtils.isEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            mBatchIdPref.setValue(uuid);
        }
        return uuid;
    }

    @NonNull
    public String createStan(){
        final String stan = UUID.randomUUID().toString();
        return stan;
    }

    public void clearBatchID(){
        mBatchIdPref.remove();
    }
}
