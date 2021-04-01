package ge.unicard.pos.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Akaki on 10/23/18.
 */
public class CTextView extends AppCompatTextView {

    public CTextView(Context context) {
        super(context);
    }

    public CTextView(Context context,
                     AttributeSet attrs) {
        super(context, attrs);
    }

    public CTextView(Context context,
                     AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAllCaps(boolean allCaps) {
        super.setAllCaps(allCaps);
        setFontFeatureSettings(allCaps ? "case" : "");
    }
}
