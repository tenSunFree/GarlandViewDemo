package com.tensun.garlandviewdemo.details;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.tensun.garlandviewdemo.R;

class DetailsItem extends RecyclerView.ViewHolder {

    DetailsItem(View itemView) {
        super(itemView);
    }

    void setContent(DetailsData data) {
        ((TextView)itemView.findViewById(R.id.tv_title)).setText(data.title);
        ((TextView)itemView.findViewById(R.id.tv_text)).setText(data.text);
    }
}
