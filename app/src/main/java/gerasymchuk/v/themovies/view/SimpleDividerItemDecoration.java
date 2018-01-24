package gerasymchuk.v.themovies.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private boolean verticalOrientation = true;
    private int space;

    private boolean showLastDivider = false;

    public SimpleDividerItemDecoration(int value, boolean verticalOrientation) {
        this.space = value;
        this.verticalOrientation = verticalOrientation;
    }

    public SimpleDividerItemDecoration(int value, boolean verticalOrientation, boolean showLastDivider) {
        this.space = value;
        this.verticalOrientation = verticalOrientation;
        this.showLastDivider = showLastDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int left = 0, right = 0, top = 0, bottom = 0, size;
        int childCount = parent.getChildCount();
        // skip first item in the list
        if (parent.getChildAdapterPosition(view) != 0) {
            if (verticalOrientation) {
                outRect.set(space, 0, 0, 0);
            } else {
                outRect.set(0, space, 0, 0);
            }
        }

        if (showLastDivider && childCount > 0) {
            View child = parent.getChildAt(childCount - 1);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + space;
            outRect.bottom = bottom;
        }
    }
}