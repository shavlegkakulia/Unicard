package ge.unicard.pos.utils;

import android.support.annotation.Nullable;

/**
 * Created by Akaki on 10/23/18.
 */

public final class ObjectUtils {

    public static <T> T requireNonNull(T object,
                                       @Nullable String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    private ObjectUtils() {
    }
}
