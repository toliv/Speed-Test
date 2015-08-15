package com.speedtest;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.math.*;
import java.lang.*;
import java.util.*;


public class Main extends Activity implements SensorEventListener {


    private SensorManager mSensorManager;

    private Sensor mAccelerometer;
    double[] accelerationList = new double[5];
    double accelerationAverage;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }

    public void updateText()
    {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tvX= (TextView)findViewById(R.id.x_axis);
//        TextView tvY= (TextView)findViewById(R.id.y_axis);
        TextView tvZ= (TextView)findViewById(R.id.z_axis);
        TextView TVhighMedLow= (TextView)findViewById(R.id.highMedLow);

        float x= event.values[0];
        float y= event.values[1];
        float z= event.values[2];

        double temp = sumAcceleration(x,y,z);

        tvX.setText(String.valueOf(temp));
//        tvY.setText(String.valueOf(y));
//        tvZ.setText(String.valueOf(z));

        addToList(temp);
        double averageAcceleration=accelerationListAverage();

        tvZ.setText(String.valueOf(averageAcceleration));
        String t = highMedLow(averageAcceleration);
        TVhighMedLow.setText(t);


    }

    public double sumAcceleration(float i,float j,float k)
    {
        float temp= i*i+j*j+k*k;


        double linearAcceleration= Math.sqrt(temp)-9.8;
        return linearAcceleration;

    }

    public double accelerationListAverage()
    {
        double sum=0;
        for(int i=0;i<5;i++)
        {
            sum+=accelerationList[i];
        }

        return (sum/5);
    }

    public void addToList( double a)
    {
        double b= accelerationList[0];
        double c = accelerationList[1];
        double d = accelerationList[2];
        double e = accelerationList[3];
        accelerationList[0]=a;
        accelerationList[1]=b;
        accelerationList[2]=c;
        accelerationList[3]=d;
        accelerationList[4]=e;
    }

    public String highMedLow(double accelAverage)
    {
        String t= "No Acceleration";

        EditText fast= (EditText)findViewById(R.id.fast);
        EditText med= (EditText)findViewById(R.id.med);
        EditText slow= (EditText)findViewById(R.id.slow);

        double fastDouble = Double.parseDouble((fast.getText().toString()));
        double medDouble = Double.parseDouble((med.getText().toString()));
        double slowDouble = Double.parseDouble((slow.getText().toString()));

        if(slowDouble<=accelAverage)
        {
            t="slow";
        }
        if(medDouble<=accelAverage)
        {
            t="med";
        }
        if(fastDouble<=accelAverage)
        {
            t="fast";
        }

        return t;
    }
}