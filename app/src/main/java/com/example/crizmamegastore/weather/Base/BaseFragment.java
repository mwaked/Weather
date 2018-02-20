package com.example.crizmamegastore.weather.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crizmamegastore.weather.UI.views.Toaster;
import com.example.crizmamegastore.weather.Preferences.SharedPrefManager;
import com.example.crizmamegastore.weather.Utils.DialogUtil;

import butterknife.ButterKnife;



public abstract class BaseFragment extends Fragment {

    public SharedPrefManager mSharedPrefManager;
    public Bundle mSavedInstanceState;
    public Toaster mToaster;
    protected Context mContext;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        mContext = getActivity();
        mSharedPrefManager = new SharedPrefManager(mContext);
        mToaster = new Toaster(getActivity());
        ButterKnife.bind(this, view);
        initializeComponents(view);
        return view;
    }

    protected abstract int getLayoutResource();

    protected abstract void initializeComponents(View view);

    protected void showProgressDialog(String message) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity(), message, false);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
