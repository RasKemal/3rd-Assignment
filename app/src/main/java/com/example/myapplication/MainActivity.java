package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView text,acc;
    View view;
    SensorManager sensorManager;
    Sensor sensorLight,sensorAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        acc = (TextView) findViewById(R.id.acc);
        view = (View) findViewById(R.id.view);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_FASTEST);
        sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }




    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Intent intent = new Intent("com.kemalselcuk.action");


        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(sensorEvent.values[0] == 0){
                acc.setText("Hareketsiz.");
            }else {
                acc.setText("Hareketli.");
                intent.putExtra("com.kemalselcuk.EXTRA_TEXT", "moving");
                sendBroadcast(intent);
            }
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if(sensorEvent.values[0] < 16000){
                text.setText("Akıllı telefon cepte.");
                acc.setTextColor(Color.parseColor("#ffffff"));
                text.setTextColor(Color.parseColor("#ffffff"));
                view.setBackgroundColor(Color.parseColor("#1d2671"));
            }else{
                intent.putExtra("com.kemalselcuk.EXTRA_TEXT", "on table");
                sendBroadcast(intent);
                text.setText("Akıllı telefon masada.");
                text.setTextColor(Color.parseColor("#000000"));
                acc.setTextColor(Color.parseColor("#000000"));
                view.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedText = intent.getStringExtra("com.kemalselcuk.EXTRA_TEXT");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.kemalselcuk.action");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}