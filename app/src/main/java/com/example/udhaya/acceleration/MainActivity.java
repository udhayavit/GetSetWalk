package com.example.udhaya.acceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

@SuppressWarnings("ConstantConditions")


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer,mGyro,mMagno;
    private String xAcc, yAcc,zAcc, xAcc2, yAcc2, zAcc2;
    private double dist;
    private double x,y,z;

    TextView xValue, yValue, zValue,xGyroValue, yGyroValue, zGyroValue,xMagnoValue, yMagnoValue, zMagnoValue, dist1;
    Button start,stop,calc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        xGyroValue = (TextView) findViewById(R.id.xGyroValue);
        yGyroValue = (TextView) findViewById(R.id.yGyroValue);
        zGyroValue = (TextView) findViewById(R.id.zGyroValue);

        xMagnoValue = (TextView) findViewById(R.id.xMagnoValue);
        yMagnoValue = (TextView) findViewById(R.id.yMagnoValue);
        zMagnoValue = (TextView) findViewById(R.id.zMagnoValue);

        dist1 = (TextView) findViewById(R.id.tvdist) ;


       Button start = findViewById(R.id.btStart);
       Button stop = findViewById(R.id.btStop);
       Button calc = findViewById(R.id.btcalc);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xAcc = xValue.getText().toString();
                yAcc = yValue.getText().toString();

//                zAcc = xValue.getText().toString();
            }
        });



        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xAcc2 = xValue.getText().toString();
                yAcc2 = yValue.getText().toString();
//                zAcc2 = xValue.getText().toString();
            }
        });


       calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              dist = Math.sqrt((Float.valueOf(xAcc)-Float.valueOf(xAcc2))*(Float.valueOf(xAcc)-Float.valueOf(xAcc2))+(Float.valueOf(yAcc)-Float.valueOf(yAcc2))*(Float.valueOf(yAcc)-Float.valueOf(yAcc2)));
             x = Math.pow((Float.valueOf(xAcc)-Float.valueOf(xAcc2)),2);
             y = Math.pow((Float.valueOf(yAcc)-Float.valueOf(yAcc2)),2);
             z = x + y;
             dist = Math.sqrt(z);
             Toast.makeText(MainActivity.this,""+ x,Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this,""+ yAcc,Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this,""+ xAcc2,Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this,""+ yAcc2,Toast.LENGTH_LONG).show();
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

        mMagno = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(mMagno != null) {
            sensorManager.registerListener(MainActivity.this, mMagno, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered Magno listener");
        }else{
            xMagnoValue.setText("Magno not Supported");
            yMagnoValue.setText("Magno not Supported");
            zMagnoValue.setText("Magno not Supported");
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

            xValue.setText("xValue" + sensorEvent.values[0]);
            yValue.setText("yValue" + sensorEvent.values[1]);
            zValue.setText("zValue" + sensorEvent.values[2]);
        }else if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
            xGyroValue.setText("xGyroValue" + sensorEvent.values[0]);
            yGyroValue.setText("yGyroValue" + sensorEvent.values[1]);
            zGyroValue.setText("zGyroValue" + sensorEvent.values[2]);
        } else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            xMagnoValue.setText("xMagnoValue" + sensorEvent.values[0]);
            yMagnoValue.setText("yMagnoValue" + sensorEvent.values[1]);
            zMagnoValue.setText("zMAgnoValue" + sensorEvent.values[2]);
        }
    }

}
