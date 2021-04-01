package ge.unicard.pos.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Akaki on 10/24/18.
 */
public final class PreferenceType<T> {

    private final SharedPreferences mSharedPreferences;
    private final String mKey;
    private final Class<T> mClass;
    @Nullable
    private final T mDefaultValue;
    private T mValue;

    public PreferenceType(@NonNull SharedPreferences sharedPreferences,
                          @NonNull String key,
                          @NonNull Class<T> clazz) {

        this(sharedPreferences, key, clazz, null);
    }

    public PreferenceType(@NonNull SharedPreferences sharedPreferences,
                          @NonNull String key,
                          @NonNull Class<T> clazz,
                          @Nullable T defaultValue) {

        mSharedPreferences = sharedPreferences;
        mKey = key;
        mClass = clazz;
        mDefaultValue = defaultValue;
    }

    synchronized public void setValue(@NonNull T value) {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (mClass.isAssignableFrom(Boolean.class)) {
            editor.putBoolean(mKey, (Boolean) value);
        } else if (mClass.isAssignableFrom(Float.class)) {
            editor.putFloat(mKey, (Float) value);
        } else if (mClass.isAssignableFrom(Integer.class)) {
            editor.putInt(mKey, (Integer) value);
        } else if (mClass.isAssignableFrom(Long.class)) {
            editor.putLong(mKey, (Long) value);
        } else if (mClass.isAssignableFrom(String.class)) {
            editor.putString(mKey, (String) value);
        } else {
            throw new UnsupportedOperationException("Unsupported preference type");
        }
        editor.apply();
        mValue = value;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    synchronized public T getValue() {
        if (mValue == null) {
            final Object value;
            if (mClass.isAssignableFrom(Boolean.class)) {
                value = mSharedPreferences.getBoolean(mKey,
                        mDefaultValue != null ? (Boolean) mDefaultValue : false);
            } else if (mClass.isAssignableFrom(Float.class)) {
                value = mSharedPreferences.getFloat(mKey,
                        mDefaultValue != null ? (Float) mDefaultValue : 0f);
            } else if (mClass.isAssignableFrom(Integer.class)) {
                value = mSharedPreferences.getInt(mKey,
                        mDefaultValue != null ? (Integer) mDefaultValue : 0);
            } else if (mClass.isAssignableFrom(Long.class)) {
                value = mSharedPreferences.getLong(mKey,
                        mDefaultValue != null ? (Long) mDefaultValue : 0L);
            } else if (mClass.isAssignableFrom(String.class)) {
                value = mSharedPreferences.getString(mKey,
                        mDefaultValue != null ? (String) mDefaultValue : "");
            } else {
                throw new UnsupportedOperationException("Unsupported preference type");
            }
            mValue = (T) value;
        }
        return mValue;
    }

    synchronized public void remove() {
        mSharedPreferences.edit().remove(mKey).apply();
        mValue = null;
    }
}
