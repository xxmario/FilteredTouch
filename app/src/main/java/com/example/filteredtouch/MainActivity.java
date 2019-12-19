package com.example.filteredtouch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button drawOverBtn;
    private Button enableA11yServiceBtn;
    private Button startBtn;
    private TextView textDescriptionTV;

    private Singleton mSingleton = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawOverBtn = findViewById(R.id.alwaysOnTopBtn);
        enableA11yServiceBtn = findViewById(R.id.a11ySettingsBtn);
        startBtn = findViewById(R.id.startBtn);
        textDescriptionTV = findViewById(R.id.textDescriptionTV);

        drawOverBtn.setOnClickListener(this);
        enableA11yServiceBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtonsVisibilityState();
    }

    private void setButtonsVisibilityState() {

        if(AndroidSettings.isA11yServiceEnabled(this, MyAccessibilityService.class)){
            mSingleton.isA11yServiceEnabled = true;
            enableA11yServiceBtn.setVisibility(View.GONE);
        } else {
            mSingleton.isA11yServiceEnabled = false;
            enableA11yServiceBtn.setVisibility(View.VISIBLE);
        }

        if(AndroidSettings.isDrawOnTopEnabled(this)){
            drawOverBtn.setVisibility(View.GONE);
            mSingleton.isDrawOnTopEnabled = true;
        } else {
            drawOverBtn.setVisibility(View.VISIBLE);
            mSingleton.isDrawOnTopEnabled = false;
        }


        if((mSingleton.isA11yServiceEnabled) && mSingleton.isDrawOnTopEnabled){
            startBtn.setVisibility(View.VISIBLE);
            textDescriptionTV.setVisibility(View.GONE);
        } else {
            startBtn.setVisibility(View.GONE);
            textDescriptionTV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alwaysOnTopBtn:
                AndroidSettings.goToDrawOverOtherApps(this, getApplicationContext());
                break;
            case R.id.a11ySettingsBtn:
                AndroidSettings.openAccessibilityServiceSetting(this);
                break;
            case R.id.startBtn:
                launchTransparentWindow();
                finish();
                break;
        }
    }

    private void launchTransparentWindow() {

        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
}
