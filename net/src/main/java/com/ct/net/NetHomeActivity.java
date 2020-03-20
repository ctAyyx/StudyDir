package com.ct.net;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NetHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_net);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_net_01:
                 
                break;
        }
    }
}
