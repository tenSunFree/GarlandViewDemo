package com.tensun.garlandviewdemo.main.inner;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tensun.garlandviewdemo.R;
import com.tensun.garlandviewdemo.databinding.InnerItemBinding;

import java.util.ArrayList;
import java.util.List;


public class InnerAdapter extends com.ramotion.garlandview.inner.InnerAdapter<InnerItem> {

    private final List<InnerData> mData = new ArrayList<>();

    @Override
    public InnerItem onCreateViewHolder(ViewGroup parent, int viewType) {
        final InnerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new InnerItem(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InnerItem holder, int position) {
        holder.setContent(mData.get(position));
    }

    @Override
    public void onViewRecycled(InnerItem holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.inner_item;
    }

    public void addData(@NonNull List<InnerData> innerDataList) {
        final int size = mData.size();
        mData.addAll(innerDataList);
        notifyItemRangeInserted(size, innerDataList.size());
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }
}
