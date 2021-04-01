package ge.unicard.pos.ui.adapters.Item_decorations;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Akaki on 11/2/18.
 */

public class StickyHeaderDecoration extends RecyclerView.ItemDecoration {

    private static final long NO_HEADER_ID = -1L;

    private final LongSparseArray<RecyclerView.ViewHolder> headerCache;
    private final StickyHeaderAdapter adapter;
    private final boolean renderInline;
    private boolean sticky;

    public StickyHeaderDecoration(StickyHeaderAdapter adapter,
                                  boolean renderInline,
                                  boolean sticky) {
        this.adapter = adapter;
        this.headerCache = new LongSparseArray<>();
        this.renderInline = renderInline;
        this.sticky = sticky;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int headerHeight = 0;

        if (position != RecyclerView.NO_POSITION && hasHeader(parent, adapter, position)) {
            View header = getHeader(parent, adapter, position).itemView;
            headerHeight = getHeaderHeightForLayout(header);
        }

        outRect.set(0, headerHeight, 0, 0);
    }

    private boolean hasHeader(RecyclerView parent,
                              StickyHeaderAdapter adapter,
                              int adapterPos) {
        boolean isReverse = isReverseLayout(parent);
        int itemCount = ((RecyclerView.Adapter) adapter).getItemCount();

        if ((isReverse && adapterPos == itemCount - 1 && adapter.getHeaderId(adapterPos) != -1) ||
                (!isReverse && adapterPos == 0)) {
            return true;
        }

        int previous = adapterPos + (isReverse ? 1 : -1);
        long headerId = adapter.getHeaderId(adapterPos);
        long previousHeaderId = adapter.getHeaderId(previous);

        return headerId != NO_HEADER_ID && previousHeaderId != NO_HEADER_ID && headerId != previousHeaderId;
    }

    private RecyclerView.ViewHolder getHeader(RecyclerView parent,
                                              StickyHeaderAdapter adapter,
                                              int position) {
        final long key = adapter.getHeaderId(position);
        @Nullable RecyclerView.ViewHolder holder = headerCache.get(key);
        if (holder != null) {
            return holder;
        } else {
            holder = adapter.onCreateHeaderViewHolder(parent);
            final View header = holder.itemView;

            //noinspection unchecked
            adapter.onBindHeaderViewHolder(holder, position);

            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);

            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());

            headerCache.put(key, holder);

            return holder;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c,
                           @NonNull RecyclerView parent,
                           @NonNull RecyclerView.State state) {
        final int count = parent.getChildCount();

        for (int layoutPos = 0; layoutPos < count; layoutPos++) {
            final View child = parent.getChildAt(translatedChildPosition(parent, layoutPos));

            final int adapterPos = parent.getChildAdapterPosition(child);

            if (adapterPos != RecyclerView.NO_POSITION && ((layoutPos == 0 && sticky) || hasHeader(parent, adapter, adapterPos))) {
                View header = getHeader(parent, adapter, adapterPos).itemView;
                c.save();
                final int left = child.getLeft();
                final int top = getHeaderTop(parent, child, header, adapterPos, layoutPos);
                c.translate(left, top);
                header.draw(c);
                c.restore();
            }
        }
    }

    private int getHeaderTop(RecyclerView parent, View child, View header, int adapterPos,
                             int layoutPos) {
        int headerHeight = getHeaderHeightForLayout(header);
        int top = getChildY(parent, child) - headerHeight;
        if (sticky && layoutPos == 0) {
            final int count = parent.getChildCount();
            final long currentId = adapter.getHeaderId(adapterPos);
            // find next view with header and compute the offscreen push if needed
            for (int i = 1; i < count; i++) {
                int adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(translatedChildPosition(parent, i)));
                if (adapterPosHere != RecyclerView.NO_POSITION) {
                    long nextId = adapter.getHeaderId(adapterPosHere);
                    if (nextId != currentId) {
                        final View next = parent.getChildAt(translatedChildPosition(parent, i));
                        final int offset = getChildY(parent, next) - (headerHeight + getHeader(parent, adapter, adapterPosHere).itemView.getHeight());
                        if (offset < 0) {
                            return offset;
                        } else {
                            break;
                        }
                    }
                }
            }

            if (sticky) top = Math.max(0, top);
        }

        return top;
    }

    private int translatedChildPosition(RecyclerView parent, int position) {
        return isReverseLayout(parent) ? parent.getChildCount() - 1 - position : position;
    }

    private int getChildY(RecyclerView parent, View child) {
        return (int) ViewCompat.getY(child);
    }

    private int getHeaderHeightForLayout(View header) {
        return renderInline ? 0 : header.getHeight();
    }

    private boolean isReverseLayout(final RecyclerView parent) {
        return (parent.getLayoutManager() instanceof LinearLayoutManager) &&
                ((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout();
    }

    public void invalidateLayouts() {
        headerCache.clear();
    }

    public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {

        long getHeaderId(int position);

        T onCreateHeaderViewHolder(ViewGroup parent);

        void onBindHeaderViewHolder(T holder, int position);
    }
}
