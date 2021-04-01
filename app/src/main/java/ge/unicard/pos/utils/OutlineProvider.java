package ge.unicard.pos.utils;

import android.graphics.Outline;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by Akaki on 11/5/18.
 */
public enum OutlineProvider {
    ROUNDED {
        @Override
        public void applyTo(@NonNull View view,
                            int radiusDp) {
            final int radius = view.getResources().getDimensionPixelSize(radiusDp);
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(
                            0,
                            0,
                            view.getWidth(),
                            view.getHeight(),
                            radius);
                }
            });
            view.setClipToOutline(true);
        }
    };

    public abstract void applyTo(@NonNull View view,
                                 @DimenRes int radiusDp);
}
