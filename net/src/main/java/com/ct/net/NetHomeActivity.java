package com.ct.net;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ct.net.test.BannerPresenter;
import com.ct.net.view.Contract;

public class NetHomeActivity extends AppCompatActivity implements Contract.IView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_net);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_net_01:
                BannerPresenter presenter = new BannerPresenter(this);
                //presenter.subscribe(this);
                presenter.getBanner(200);
                break;
        }
    }

    @Override
    public void showProgress(int code, String msg) {
        Log.e("TAG", "showProgress" + code + "---" + msg);
    }

    @Override
    public void hideProgress(int code) {
        Log.e("TAG", "hideProgress" + code);
    }

    @Override
    public <T> void onSuccess(int code, T response) {
        Log.e("TAG", "onSuccess" + code + "---" + response);
    }

    @Override
    public void onError(int code, String msg) {
        Log.e("TAG", "onError" + code + "---" + msg);
    }

    @Override
    public void onException(int code, Throwable throwable) {
        Log.e("TAG", "onException" + code + "---" + throwable.getMessage());
    }

    @Override
    public void onBefore(int code) {
        Log.e("TAG", "onBefore" + code);
    }

    @Override
    public void onAfter(int code, boolean isCancelByUser) {
        Log.e("TAG", "onAfter" + code + "---" + isCancelByUser);
    }

    @Override
    public void onNoNet(int code) {
        Log.e("TAG", "onNoNet" + code);
    }
}
