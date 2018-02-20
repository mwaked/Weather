package com.example.crizmamegastore.weather.UI.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.crizmamegastore.weather.Base.ParentRecyclerAdapter;
import com.example.crizmamegastore.weather.Base.ParentRecyclerViewHolder;
import com.example.crizmamegastore.weather.Model.CitiesModel;
import com.example.crizmamegastore.weather.R;

import java.util.List;
import butterknife.BindView;


public class CitiesAdapter extends ParentRecyclerAdapter<CitiesModel> {

    public CitiesAdapter(final Context context, final List<CitiesModel> data, final int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public ParentRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        holder.setOnItemClickListener(itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ParentRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        CitiesModel item = data.get(position);

        if (position % 2 == 1) {
            viewHolder.layout_content.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            viewHolder.layout_content.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        }

        viewHolder.tv_city_name.setText(item.getCityName());

        viewHolder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view , position);
            }
        });

        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view , position);
            }
        });
    }


    public class ViewHolder extends ParentRecyclerViewHolder {

        @BindView(R.id.tv_city_name)
        TextView tv_city_name;

        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        @BindView(R.id.layout_content)
        LinearLayout layout_content;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
