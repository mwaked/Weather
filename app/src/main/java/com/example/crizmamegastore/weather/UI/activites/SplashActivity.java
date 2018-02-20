package com.example.crizmamegastore.weather.UI.activites;


import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.crizmamegastore.weather.Base.ParentActivity;
import com.example.crizmamegastore.weather.Model.Main;
import com.example.crizmamegastore.weather.R;

import butterknife.BindView;

public class SplashActivity extends ParentActivity {

    private Animation fade;

    @BindView(R.id.splash_layout)
    RelativeLayout splash_layout ;

    @Override
    protected void initializeComponents() {
        fade = AnimationUtils.loadAnimation(this, R.anim.alpha);
        splash_layout.clearAnimation();
        splash_layout.startAnimation(fade);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                    MainActivity.startActivity(SplashActivity.this);
            }
            @Override
            public void onAnimationRepeat(final Animation animation) {

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isEnableToolbar() {
        return false;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected boolean isEnableBack() {
        return false;
    }

    @Override
    protected boolean hideInputType() {
        return false;
    }
}
