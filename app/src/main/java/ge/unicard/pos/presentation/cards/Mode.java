package ge.unicard.pos.presentation.cards;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        Mode.MODE_BONUS_ACCUMULATION,
        Mode.MODE_MAKE_PAYMENT,
        Mode.MODE_PURCHASE,
        Mode.MODE_BALANCE,
        Mode.MODE_ACTION_REPORT


})
@Retention(RetentionPolicy.SOURCE)
public @interface Mode {
     int MODE_BONUS_ACCUMULATION = 1;
     int MODE_MAKE_PAYMENT = 2;
     int MODE_PURCHASE = 3;
     int MODE_BALANCE = 4;

     int MODE_ACTION_REPORT = 5;

}