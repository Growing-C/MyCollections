package com.cgy.mycollections.listeners.swipedrag;

/**
 * 使用系统recyclerview自带的 ItemTouchHelper 实现的滑动删除和位置切换
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
