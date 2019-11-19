package com.cgy.mycollections.listeners.swipedrag;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
