package com.pocketmessiah.messiah.activity;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.pocketmessiah.messiah.R;

public class RemindActivity extends Activity {

    TimePicker timePicker;
    Calendar calendar;
    ToggleButton button;

    int hours;
    int minutes;

    //static String quote = "...свет внутри...";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);


        timePicker = (TimePicker) findViewById(R.id.timePicker);
        button = (ToggleButton) findViewById(R.id.switcher);

        loadTimer(timePicker);


        /*Messiah messiah = new Messiah();
        try {
            quote = messiah.notificationQoute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                boolean on = ((ToggleButton) v).isChecked() ;

                if(on)
                {
                    setCalendar();
                    setAlarm(calendar);

                    SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                    editor.putInt("hour", timePicker.getCurrentHour());
                    editor.putInt("minute", timePicker.getCurrentMinute());
                    //editor.putString("quote", quote);
                    editor.apply();

                    Intent intent = new Intent(RemindActivity.this, Messiah.class);
                    startActivity(intent);

                }
                else
                {
                    cancelAlarm();
                }
            }

        });


    }

    /*@Override
    protected void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Remind Activity");
    }*/


    private void setCalendar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hours = timePicker.getHour();
            minutes = timePicker.getMinute();
        } else {
            hours = timePicker.getCurrentHour();
            minutes = timePicker.getCurrentMinute();
        }

        calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

    }

    private void setAlarm(Calendar calendar){
        Intent alarmIntent = new Intent(RemindActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(RemindActivity.this, 0, alarmIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelAlarm() {
        Intent alarmIntent = new Intent(RemindActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(RemindActivity.this, 0, alarmIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void  loadTimer(TimePicker timepicker) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        timepicker.setCurrentHour(prefs.getInt("hour", timePicker.getCurrentHour()));
        timepicker.setCurrentMinute(prefs.getInt("minute", timepicker.getCurrentMinute()));
    }

}
