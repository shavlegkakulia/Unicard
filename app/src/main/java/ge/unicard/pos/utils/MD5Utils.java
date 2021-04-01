package ge.unicard.pos.utils;

import java.math.BigInteger;

/**
 * Created by Akaki on 10/24/18.
 */
public final class MD5Utils {

    public static String hashBytesToStr(byte[] hashBytes){
            final BigInteger bigInt = new BigInteger(1, hashBytes);
            final String output = bigInt.toString(16);
            return String.format("%32s", output).replace(' ', '0');
    }

    private MD5Utils() {
    }
}
