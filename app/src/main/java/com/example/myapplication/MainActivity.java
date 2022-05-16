package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        Intent intent = new Intent();


        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(sensorEvent.values[0] == 0){
                acc.setText("Hareketsiz.");
            }else {
                acc.setText("Hareketli.");
                intent.setAction("com.kemalselcuk.moving");
                intent.putExtra("message","phone is moving.");
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);
            }
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if(sensorEvent.values[0] < 16000){
                text.setText("Akıllı telefon cepte.");
                acc.setTextColor(Color.parseColor("#ffffff"));
                text.setTextColor(Color.parseColor("#ffffff"));
                view.setBackgroundColor(Color.parseColor("#1d2671"));
                intent.setAction("com.kemalselcuk.dark");
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.putExtra("message","phone is in pocket.");
                sendBroadcast(intent);
            }else{
                text.setText("Akıllı telefon masada.");
                text.setTextColor(Color.parseColor("#000000"));
                acc.setTextColor(Color.parseColor("#000000"));
                view.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}