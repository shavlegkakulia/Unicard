package ge.unicard.pos.lib;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Akaki on 10/23/18.
 */

public final class RequestCode {

    private static final AtomicInteger sNextGeneratedRequestCode = new AtomicInteger(1);

    public static int newRequestCode() {
        for (; ; ) {
            final int result = sNextGeneratedRequestCode.get();
            int newValue = result + 1;
            if (newValue > Integer.MAX_VALUE / 2) newValue = 1;
            if (sNextGeneratedRequestCode.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    private RequestCode(){}
}
