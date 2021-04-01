package ge.unicard.pos.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class PrinterLayout extends FrameLayout {

    public PrinterLayout(@NonNull Context context) {
        super(context);
    }

    public PrinterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Bitmap getAsBitmap() {
        final int wrapContentSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED);
        measure(wrapContentSpec, wrapContentSpec);
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        final Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }
}
