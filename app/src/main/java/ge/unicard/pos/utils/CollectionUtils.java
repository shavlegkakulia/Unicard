package ge.unicard.pos.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akaki on 11/6/18.
 */
public class CollectionUtils {

    public static <T> ArrayList<T> asArrayList(List<T> list) {
        if (list == null) {
            return null;
        }
        return list instanceof ArrayList<?> ? (ArrayList<T>) list : new ArrayList<>(list);
    }

    private CollectionUtils() {
    }
}
