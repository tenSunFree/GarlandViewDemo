package com.tensun.garlandviewdemo.main.outer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.garlandview.TailAdapter;
import com.tensun.garlandviewdemo.R;
import com.tensun.garlandviewdemo.main.inner.InnerData;

import java.util.List;

public class OuterAdapter extends TailAdapter<OuterItem> {

    private final int POOL_SIZE = 16;

    private final List<List<InnerData>> mData;
    private final RecyclerView.RecycledViewPool mPool;

    public OuterAdapter(List<List<InnerData>> data) {
        this.mData = data;

        mPool = new RecyclerView.RecycledViewPool();                                                // 让你在多个不同的RecyclerViews之间, 共享itemViews
        mPool.setMaxRecycledViews(0, POOL_SIZE);                                          // 關於第1個參數: RecycledViewPool是依據ItemViewType來索引ViewHolder的, 關於第2個參數: 控制需要緩存的ViewHolder數量
    }

    /** 創建Item的View */
    @Override
    public OuterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OuterItem(view, mPool);
    }

    @Override
    public void onBindViewHolder(OuterItem holder, int position) {
        holder.setContent(mData.get(position));
    }

    @Override
    public void onViewRecycled(OuterItem holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.outer_item;
    }
}
