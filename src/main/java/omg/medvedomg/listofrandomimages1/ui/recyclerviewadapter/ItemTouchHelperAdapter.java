package omg.medvedomg.listofrandomimages1.ui.recyclerviewadapter;

/**
 * Created by medvedomg on 22.12.16.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
