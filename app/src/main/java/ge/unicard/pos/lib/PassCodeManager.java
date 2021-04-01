package ge.unicard.pos.lib;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import ge.unicard.pos.preferences.PreferenceType;
import ge.unicard.pos.utils.MD5Utils;

/**
 * Created by Akaki on 10/24/18.
 */
public final class PassCodeManager {

    static final String TAG = "PassCodeManager";

    private final PreferenceType<String> mPassCodePref;
    private final PreferenceType<String> mPassCodeSaltPref;

    private MessageDigest mDigest;

    public PassCodeManager(@NonNull PreferenceType<String> passCodePref,
                           @NonNull PreferenceType<String> passCodeSaltPref) {
        mPassCodePref = passCodePref;
        mPassCodeSaltPref = passCodeSaltPref;
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Instantiate error", e);
        }
    }

    public void setPassCode(@NonNull String passCode) {
        if (TextUtils.isEmpty(passCode)) {
            throw new IllegalArgumentException("PassCode must be non-empty");
        }
        final String hashStr = passCodeToHash(passCode);
        mPassCodePref.setValue(hashStr);
    }

    public void removePassCode() {
        mPassCodePref.remove();
    }

    public boolean checkPassCode(String passCode) {
        final String hashStr = passCodeToHash(passCode);
        return hashStr.equalsIgnoreCase(mPassCodePref.getValue());
    }

    private String passCodeToHash(String passCode) {
        final byte[] inputBytes = (passCode + getSalt()).getBytes();
        mDigest.reset();
        final byte[] finalBytes = mDigest.digest(inputBytes);
        return MD5Utils.hashBytesToStr(finalBytes);
    }

    private String getSalt() {
        String saltHex = mPassCodeSaltPref.getValue();
        if (TextUtils.isEmpty(saltHex)) {
            try {
                final long salt = SecureRandom.getInstance("SHA1PRNG").nextLong();
                saltHex = Long.toHexString(salt);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Failed to get random salt", e);
                saltHex = "eb32a1";
            }
            mPassCodeSaltPref.setValue(saltHex);
        }
        return saltHex;
    }
}