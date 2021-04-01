package ge.unicard.pos.preferences;

/**
 * Created by Akaki on 10/24/18.
 */
public interface AppPreferences {

    PreferenceType<String> passCode();

    PreferenceType<String> passCodeSalt();

    PreferenceType<String> batchId();
}
