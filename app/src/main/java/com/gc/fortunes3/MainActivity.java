package com.gc.fortunes3;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    TextView xCoor; // declare X axis object
    TextView yCoor; // declare Y axis object
    TextView zCoor; // declare Z axis object
    TextView Fortune;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xCoor=(TextView)findViewById(R.id.xcoor); // create X axis object
        yCoor=(TextView)findViewById(R.id.ycoor); // create Y axis object
        zCoor=(TextView)findViewById(R.id.zcoor); // create Z axis object
        Fortune=(TextView)findViewById(R.id.fortune);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        // add listener. The listener will be HelloAndroid (this) class
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this,
        //        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        //        SensorManager.SENSOR_DELAY_NORMAL);

		/*	More sensor speeds (taken from api docs)
		    SENSOR_DELAY_FASTEST get sensor data as fast as possible
		    SENSOR_DELAY_GAME	rate suitable for games
		 	SENSOR_DELAY_NORMAL	rate (default) suitable for screen orientation changes
		*/
    }

    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    public void onSensorChanged(SensorEvent event){

        // check sensor type
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            // assign directions
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];

            if(lastUpdate == 0) {
//                xCoor.setText("X: " + x);
//                yCoor.setText("Y: " + y);
//                zCoor.setText("Z: " + z);
                last_x = x;
                last_y = y;
                last_z = z;

                AdView mAdView = (AdView) findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                lastUpdate = System.currentTimeMillis()-300;

            }

            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) >= 300) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {


//                    if ((curTime - lastUpdate) > 100) {
//                        xCoor.setText("XX: " + x);
//                        yCoor.setText("YY: " + y);
//                        zCoor.setText("ZZ: " + z);

//                        last_x = x;
//                        last_y = y;
//                        last_z = z;

                        //TOAST
                        int num = getRandomNumber(15);
                        String fortunetext = getFortuneText(num);
                        Fortune.setText(fortunetext);

                    //AdView mAdView = (AdView) findViewById(R.id.adView);
                    //AdRequest adRequest = new AdRequest.Builder().build();
                    //mAdView.loadAd(adRequest);
                    //try{ Thread.sleep(50000); }catch(InterruptedException e){ }
                        //SystemClock.sleep(7000);
                        //Toast.makeText(getApplicationContext(), "wait over",
                        //        Toast.LENGTH_LONG).show();



                    }
                }
//            }
        }
    }

    private String getFortuneText(int num) {
        String text = "";
        if(num == 1) {text = "Try again";}
        if(num == 2) {text = "What's the worst that could happen?";}
        if(num == 3) {text = "Outcome involves a hospital visit";}
        if(num == 4) {text = "Have a beer and think this over some more";}
        if(num == 5) {text = "Are you kidding?";}
        if(num == 6) {text = "You should already know why she's mad at you";}
        if(num == 7) {text = "Let somebody else handle that";}
        if(num == 8) {text = "It beats wasting two hours watching Anchorman 2";}
        if(num == 9) {text = "Repeat the question, except add \"under the sheets\" at the end";}
        if(num == 10) {text = "I'm not even going to dignify myself with a response to that";}
        if(num == 11) {text = "What would Tebow Do?";}
        if(num == 12) {text = "Blame Obama";}
        if(num == 13) {text = "Blame Bush";}
        if(num == 14) {text = "Whatever you decide, make sure you tell everyone on Facebook";}
        if(num == 15) {text = "Sounds like you need to get laid";}
        return text;
    }

    private int getRandomNumber(int max) {
            Random randNumber = new Random();
            int iNumber = randNumber.nextInt(max) + 1;
        return iNumber;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
