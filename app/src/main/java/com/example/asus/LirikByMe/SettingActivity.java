package com.example.asus.LirikByMe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    SeekBar seekbar;
    TextView textView;
    int progress;
    private Switch switch1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //switch
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        switch1 = (Switch) findViewById(R.id.switch1);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            switch1.setChecked(true);
        }
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        //seekbar
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setMax(100);
        seekbar.setProgress(progress);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("" + textView.getText().toString());
        textView.setTextSize(progress);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                textView.setText("" + textView.getText().toString());
                textView.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("night_mode", AppCompatDelegate.getDefaultNightMode());
        setResult(RESULT_OK, intent);
        finish();
    }
}
