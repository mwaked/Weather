package com.example.crizmamegastore.weather.Base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.crizmamegastore.weather.Listners.OnItemClickListener;

import butterknife.ButterKnife;


public class ParentRecyclerViewHolder extends RecyclerView.ViewHolder {
    private View clickableRootView; // this is used to change the default onItemClickListener

    public ParentRecyclerViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        if (clickableRootView != null) {
            clickableRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        } else {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setClickableRootView(View clickableRootView) {
        this.clickableRootView = clickableRootView;
    }

    public View findViewById(int viewId) {
        if (itemView != null) {
            return itemView.findViewById(viewId);
        } else {
            return null;
        }
    }
}
