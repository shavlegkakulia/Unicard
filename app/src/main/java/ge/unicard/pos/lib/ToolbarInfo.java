package ge.unicard.pos.lib;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Akaki on 10/25/18.
 */

public class ToolbarInfo {

    private final ActionTitle title;
    private final ActionButton actionButton1;
    private final ActionButton actionButton2;
    private final int titleGravity;

    private ToolbarInfo(Builder builder) {
        this.title = builder.title;
        this.actionButton1 = builder.actionButton1;
        this.actionButton2 = builder.actionButton2;
        this.titleGravity = builder.titleGravity;
    }

    public ActionTitle getTitle() {
        return title;
    }

    public int getTitleGravity() {
        return titleGravity;
    }

    @Nullable
    public ActionButton getActionButton1() {
        return actionButton1;
    }

    @Nullable
    public ActionButton getActionButton2() {
        return actionButton2;
    }

    public static class ActionButton {

        public final @DrawableRes
        int iconResId;
        public final View.OnClickListener onClickListener;

        public ActionButton(int iconResId,
                            View.OnClickListener onClickListener) {
            this.iconResId = iconResId;
            this.onClickListener = onClickListener;
        }
    }

    public static class ActionTitle {

        public String title;
        public final View.OnClickListener onClickListener;

        public ActionTitle(String title,
                           View.OnClickListener onClickListener) {
            this.title = title;
            this.onClickListener = onClickListener;
        }
    }



    public static final class Builder {

        private ActionTitle title;
        private ActionButton actionButton1;
        private ActionButton actionButton2;
        private int titleGravity;

        public Builder() {
            this.titleGravity = Gravity.NO_GRAVITY;
        }

        public Builder setTitle(ActionTitle title) {
            this.title = title;
            return this;
        }

        public Builder setActionButton1(ActionButton actionButton) {
            this.actionButton1 = actionButton;
            return this;
        }

        public Builder setActionButton2(ActionButton actionButton) {
            this.actionButton2 = actionButton;
            return this;
        }

        public Builder setTitleGravity(int titleGravity) {
            this.titleGravity = titleGravity;
            return this;
        }

        public ToolbarInfo build() {
            return new ToolbarInfo(this);
        }
    }
}
