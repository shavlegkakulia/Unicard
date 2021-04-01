package ge.unicard.pos.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Akaki on 10/24/18.
 */
public class CButton extends CTextView {

    public CButton(Context context) {
        this(context, null);
    }

    public CButton(Context context,
                   AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CButton(Context context,
                   AttributeSet attrs,
                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setEnabled(isEnabled());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1f : 0.4f);
    }
}
