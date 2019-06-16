package com.example.udhaya.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SuppressWarnings("ConstantConditions")


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer,mGyro;
    public Float xAcc, yAcc,zAcc, xAcc2, yAcc2, zAcc2;
    private double dist;
    private double x,y,z,final_dist;
    private float fb,td,s;
    private Chronometer chronometer;
    private boolean running;
    private long stopoffset;


    TextView xValue, yValue, zValue,xGyroValue, yGyroValue, zGyroValue, dist1, CounterText;
    Button start,stop,calc,reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        xValue = (TextView) findViewById(R.id.txtxValue);
        yValue = (TextView) findViewById(R.id.txtyValue);
        zValue = (TextView) findViewById(R.id.txtzValue);
        CounterText = (TextView) findViewById(R.id.mTextField);


        xGyroValue = (TextView) findViewById(R.id.xGyroValue);
        yGyroValue = (TextView) findViewById(R.id.yGyroValue);
        zGyroValue = (TextView) findViewById(R.id.zGyroValue);

        dist1 = (TextView) findViewById(R.id.tvdist) ;


       Button start = findViewById(R.id.btStart);
       Button stop = findViewById(R.id.btStop);
       Button calc = findViewById(R.id.btcalc);
        Button reset = findViewById(R.id.reset);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                xAcc = fb;
                yAcc = td;
                zAcc = s;
                if(!running){
                    chronometer.setBase(SystemClock.elapsedRealtime() - stopoffset);
                    chronometer.start();
                    running = true;
                }


            }
        });



        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xAcc2 = fb;
                yAcc2 = td;
                zAcc2 = s;

                if(running){
                    chronometer.stop();
                    stopoffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });


       calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = Math.pow((xAcc-xAcc2),2);
                y = Math.pow((yAcc-yAcc2),2);
                z = Math.pow((zAcc-zAcc2),2);
                final_dist = Math.sqrt(x + y+ z);
                dist1.setText(""+final_dist);
            }
        });

       reset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               chronometer.setBase(SystemClock.elapsedRealtime());
               stopoffset = 0;
           }
       });



        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if(accelerometer != null) {
            sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered accelerometer listener");
        }else{
            xValue.setText("Accelerometer not Supported");
            yValue.setText("Accelerometer not Supported");
            zValue.setText("Accelerometer not Supported");
        }



        mGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(mGyro != null) {
            sensorManager.registerListener(MainActivity.this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered Gyro listener");
        }else{
            xGyroValue.setText("Gyroscope not Supported");
            yGyroValue.setText("Gyroscope not Supported");
            zGyroValue.setText("Gyroscope not Supported");
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z :" + sensorEvent.values[2]);

           xValue.setText("Front and Back Movement:    " + sensorEvent.values[0]);
           fb = sensorEvent.values[0];
            yValue.setText("Top and Down Movement:      " + sensorEvent.values[1]);
            td = sensorEvent.values[1];
            zValue.setText("Slope Movement:             " + sensorEvent.values[2]);
            s = sensorEvent.values[2];
        }
        else if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
            xGyroValue.setText("X - Axis         " + sensorEvent.values[0]);
            yGyroValue.setText("Y - Axis         " + sensorEvent.values[1]);
            zGyroValue.setText("Z - Axis         " + sensorEvent.values[2]);
        }
    }

}
