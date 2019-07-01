package com.kiran.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import CreateChannel.CreateChannel;

public class MainActivity extends AppCompatActivity {

    Ringtone ringtoneSound;
    Button playAlarm, stopAlarm;
    private NotificationManagerCompat notificationManagerCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAlarm = findViewById(R.id.playAlarm);
        stopAlarm = findViewById(R.id.stopAlarm);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();
        playAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    playAlarm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarm();
            }
        });
    }

    public void playAlarm() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String oeStartDateStr = "06/01/2019";
        String oeEndDateStr = "07/01/2019";

        Date startDate = sdf.parse(oeStartDateStr);
        Date endDate = sdf.parse(oeEndDateStr);
        Date d = new Date();
        String currDt = sdf.format(d);

        if((d.after(startDate) && (d.before(endDate))) || (currDt.equals(sdf.format(startDate)) ||currDt.equals(sdf.format(endDate)))){
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtoneSound = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);
            ringtoneSound.play();
            DisplayNotification();
        }
    }
    int countID=1;
    private void DisplayNotification() {

        Notification notification = new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)

                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("My Alaram")
                .setContentText("Mediciene time")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(countID, notification);
        countID++;
    }
    public void stopAlarm() {
        ringtoneSound.stop();
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show();
    }

    public static boolean isDateBetween(Date min, Date max, Date date) {
        return !(date.before(min) || date.after(max));
    }
}