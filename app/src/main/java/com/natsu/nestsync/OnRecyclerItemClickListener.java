package com.natsu.nestsync;

public interface OnRecyclerItemClickListener {
    void onRecItemClick(int pos, String id);
    void onRecItemBtnClick(int pos, String id);
    void onRecItemTextClick(String id, String text);
}
