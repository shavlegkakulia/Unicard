package ge.unicard.pos.utils;

import android.support.design.widget.TextInputLayout;

public class ViewUtils {

    public static void setTextWithoutHintAnimation(TextInputLayout til,
                                                   CharSequence text) {
        til.setHintAnimationEnabled(false);
        if (til.getEditText() != null) {
            til.getEditText().setText(text);
        }
        til.setHintAnimationEnabled(true);
    }

    private ViewUtils() {
    }
}
