package ge.unicard.pos.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

/**
 * Created by Akaki on 11/9/18.
 */
public class CTextInputEditText extends TextInputEditText {
    public CTextInputEditText(Context context) {
        super(context);
    }

    public CTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public int getAutofillType() {
        return AUTOFILL_TYPE_NONE;
    }
}
