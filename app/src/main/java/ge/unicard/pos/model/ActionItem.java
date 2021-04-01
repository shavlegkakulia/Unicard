package ge.unicard.pos.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ge.unicard.pos.utils.CollectionUtils;

/**
 * Created by Akaki on 10/29/18.
 */
public class ActionItem implements Parcelable {

    private String label;
    private @ColorInt
    int color;
    @Nullable
    private Intent componentIntent;
    @Nullable
    private ArrayList<ActionItem> childItems;
    private int themeResId;

    private int Image;


    public ActionItem(String label,
                      @ColorInt int color,
                      @Nullable Intent componentIntent,
                      @Nullable List<ActionItem> childItems,
                      @StyleRes int themeResId,
                      int Image) {
        this.label = label;
        this.color = color;
        this.componentIntent = componentIntent;
        this.childItems = CollectionUtils.asArrayList(childItems);
        this.themeResId = themeResId;
        this.Image = Image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        this.Image = image;
    }

    @Nullable
    public Intent getIntent() {
        return componentIntent;
    }

    public void setComponentIntent(@Nullable Intent componentIntent) {
        this.componentIntent = componentIntent;
    }

    @Nullable
    public List<ActionItem> getChildItems() {
        return childItems;
    }

    public void setChildItems(@Nullable List<ActionItem> childItems) {
        this.childItems = CollectionUtils.asArrayList(childItems);
    }

    public void setThemeResId(int themeResId) {
        this.themeResId = themeResId;
    }

    @StyleRes
    public int getThemeResId() {
        return themeResId;
    }

   @SuppressWarnings("all")
    public ActionItem(Parcel in) {
        this.label = in.readString();
        this.color = in.readInt();
        this.componentIntent = in.readParcelable(Intent.class.getClassLoader());
        this.childItems = in.readArrayList(ActionItem.class.getClassLoader());
        this.themeResId = in.readInt();
        this.Image = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeInt(this.color);
        dest.writeParcelable(this.componentIntent, flags);
        dest.writeList(this.childItems);
        dest.writeInt(this.themeResId);
        dest.writeInt(this.Image);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ActionItem createFromParcel(Parcel in) {
            return new ActionItem(in);
        }

        public ActionItem[] newArray(int size) {
            return new ActionItem[size];
        }
    };
}
