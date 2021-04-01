package ge.unicard.pos.preferences;

import android.content.SharedPreferences;

/**
 * Created by Akaki on 10/24/18.
 */
public class AppPreferencesImpl implements AppPreferences {

    private final PreferenceType<String> passCodePref;
    private final PreferenceType<String> passCodeSaltPref;
    private final PreferenceType<String> batchIdPref;

    public AppPreferencesImpl(SharedPreferences sharedPreferences) {
        passCodePref = new PreferenceType<>(sharedPreferences,
                "pass_code", String.class);
        passCodeSaltPref = new PreferenceType<>(sharedPreferences,
                "pass_code_salt", String.class);
        batchIdPref =  new PreferenceType<>(sharedPreferences,
                "batch_id", String.class);
    }

    @Override
    public PreferenceType<String> passCode() {
        return passCodePref;
    }

    @Override
    public PreferenceType<String> passCodeSalt() {
        return passCodeSaltPref;
    }

    @Override
    public PreferenceType<String> batchId() {
        return batchIdPref;
    }
}
