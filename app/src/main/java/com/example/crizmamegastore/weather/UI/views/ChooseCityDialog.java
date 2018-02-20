package com.example.crizmamegastore.weather.UI.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.crizmamegastore.weather.App.AppController;
import com.example.crizmamegastore.weather.Listners.ChooseCityListener;
import com.example.crizmamegastore.weather.Listners.OnItemClickListener;
import com.example.crizmamegastore.weather.Model.CitiesModel;
import com.example.crizmamegastore.weather.Model.CitiesModel_;
import com.example.crizmamegastore.weather.R;
import com.example.crizmamegastore.weather.UI.activites.MainActivity;
import com.example.crizmamegastore.weather.UI.adapters.CitiesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;


public class ChooseCityDialog extends Dialog implements OnItemClickListener {


    @BindView(R.id.rv_recycle)
    RecyclerView rv_recycle;

    @BindView(R.id.lay_progress)
    RelativeLayout lay_progress;

    @BindView(R.id.lay_no_item)
    RelativeLayout lay_no_item;

    @BindView(R.id.lay_no_internet)
    RelativeLayout lay_no_internet;

    @BindView(R.id.iv_no_item)
    ImageView iv_no_item;

    @BindView(R.id.tv_no_content)
    TextView tv_no_content;

    LinearLayoutManager mLinearLayoutManager;
    Context mContext;
    List<CitiesModel> modelArrayList = new ArrayList<>();
    CitiesAdapter citiesAdapter;
    ChooseCityListener chooseCityListener;

    BoxStore mBoxStore;
    private Box<CitiesModel> notesBox;
    private Query<CitiesModel> notesQuery;

    public void onChooseCityListener(ChooseCityListener chooseCityListener) {
        this.chooseCityListener = chooseCityListener;
    }


    public ChooseCityDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_city);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        ButterKnife.bind(this);

        mBoxStore = ((AppController) ((MainActivity)mContext).getApplication()).getBoxStore();
        notesBox = mBoxStore.boxFor(CitiesModel.class);

        initializeComponents();

    }

    private void initializeComponents() {
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_recycle.setLayoutManager(mLinearLayoutManager);
        tv_no_content.setText(mContext.getString(R.string.no_cities_found));

        setCitiesAndRecycle();
    }

    @OnClick(R.id.iv_close)
    void onCloseClicked() {
        dismiss();
    }


    @Override
    public void onItemClick(View view, int position) {
        if(view.getId() == R.id.iv_delete){
            removeCity(modelArrayList.get(position));
            citiesAdapter.Delete(position);
            if(modelArrayList.size()==0){
             lay_no_item.setVisibility(View.VISIBLE);
            }
        }else {
            chooseCityListener.onCarTypeListener(modelArrayList.get(position));
            this.dismiss();
        }
    }

    @OnClick(R.id.lay_no_internet)
    public void retryGetData() {
        lay_no_internet.setVisibility(View.GONE);
    }

    private void setCitiesAndRecycle() {
        notesQuery = notesBox.query().order(CitiesModel_.id).build();
        modelArrayList = notesQuery.find();
        Collections.reverse(modelArrayList);
        citiesAdapter = new CitiesAdapter(mContext, modelArrayList,
                R.layout.recycle_city_row);
        citiesAdapter.setOnItemClickListener(this);
        rv_recycle.setAdapter(citiesAdapter);
    }


    void removeCity(CitiesModel citiesModel) {
        notesBox.remove(citiesModel);
    }

}

