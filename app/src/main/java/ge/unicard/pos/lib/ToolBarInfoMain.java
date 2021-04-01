package ge.unicard.pos.lib;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

public class ToolBarInfoMain {


    private final  ActionTitle actionTitle;
    private final ToolBarInfoMain.ActionButton actionButton12;
    private final ToolBarInfoMain.ActionButton actionButton22;
    private final int titleGravity1;

    private ToolBarInfoMain(Builder1 builder) {
        this.actionTitle = builder.title;
        this.actionButton12 = builder.actionButton11;
        this.actionButton22 = builder.actionButton22;
        this.titleGravity1 = builder.titleGravity1;
    }

    public ActionTitle getActionTitle() {
        return actionTitle;
    }

    public int getTitleGravity() {
        return titleGravity1;
    }

    @Nullable
    public ToolBarInfoMain.ActionButton getActionButton1() {
        return actionButton12;
    }

    @Nullable
    public ToolBarInfoMain.ActionButton getActionButton2() {
        return actionButton22;
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


    public static final class Builder1 {

        private ActionTitle title;
        private ActionButton actionButton11;
        private ActionButton actionButton22;
        private int titleGravity1;

        public Builder1() {
            this.titleGravity1 = Gravity.NO_GRAVITY;
        }

        public ToolBarInfoMain.Builder1 setActionTitle(ToolBarInfoMain.ActionTitle title) {
            this.title = title;
            return this;
        }

        public ToolBarInfoMain.Builder1 setActionButton1(ToolBarInfoMain.ActionButton actionButton) {
            this.actionButton11 = actionButton;
            return this;
        }

        public ToolBarInfoMain.Builder1 setActionButton2(ToolBarInfoMain.ActionButton actionButton) {
            this.actionButton22 = actionButton;
            return this;
        }

        public ToolBarInfoMain.Builder1 setTitleGravity(int titleGravity) {
            this.titleGravity1 = titleGravity;
            return this;
        }

        public ToolBarInfoMain build() {
            return new ToolBarInfoMain(this);
        }
    }
}
