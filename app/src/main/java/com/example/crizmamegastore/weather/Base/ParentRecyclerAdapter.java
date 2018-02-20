package com.example.crizmamegastore.weather.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.crizmamegastore.weather.Listners.OnItemClickListener;
import com.example.crizmamegastore.weather.Listners.PaginationAdapterCallback;
import com.example.crizmamegastore.weather.Preferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ParentRecyclerAdapter<Item> extends RecyclerView.Adapter<ParentRecyclerViewHolder> {

    protected Context context;

    protected List<Item> data;

    protected int layoutId;

    protected boolean isLoadingAdded = false;

    protected boolean retryPageLoad = false;


    protected OnItemClickListener itemClickListener;

    protected PaginationAdapterCallback mPaginationAdapterCallback;

    protected SharedPrefManager mSharedPrefManager;


    public ParentRecyclerAdapter(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
        mSharedPrefManager = new SharedPrefManager(context);

    }

    public ParentRecyclerAdapter(Context context, List<Item> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
        mSharedPrefManager = new SharedPrefManager(context);
    }


    public void setOnPaginationClickListener(PaginationAdapterCallback onPaginationClickListener) {
        this.mPaginationAdapterCallback = onPaginationClickListener;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void InsertAll(List<Item> items) {
//        data.clear();
        notifyDataSetChanged();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void InsertAllWithTwoNotify(List<Item> items) {
        data.clear();
        notifyDataSetChanged();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void Insert(int position, Item item) {
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void Delete(int postion) {
        data.remove(postion);
        notifyDataSetChanged();
    }

    public void update(int position, Item item) {
        data.remove(position);
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void updateAll(List<Item> items) {
        data = new ArrayList<>();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public List<Item> getData() {
        return data;
    }

    public void addFooterProgress() {
        this.data.add(null);
        notifyItemInserted(data.size() - 1);
    }

    public void removeFooterProgress() {
        data.remove(data.size() - 1);
        notifyItemRemoved(data.size());
        Log.e("footer", "gone");
    }


    public void addLoadingFooter(Item item) {
        isLoadingAdded = true;
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = data.size() - 1;
        data.remove(position);
        notifyItemRemoved(position);
    }
}
