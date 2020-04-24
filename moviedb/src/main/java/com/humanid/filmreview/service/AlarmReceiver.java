package com.humanid.filmreview.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import com.humanid.filmreview.R;
import com.humanid.filmreview.activity.MainActivity;
import com.humanid.filmreview.utils.Constants;
import com.humanid.filmreview.utils.Keys;

import java.util.Calendar;

import static android.content.Intent.FLAG_EXCLUDE_STOPPED_PACKAGES;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        int type = intent.getStringExtra(Keys.KEY_TYPE).equals(Constants.TYPE_DAILY) ?
                Constants.DAILY_REMINDER_CODE : Constants.UPCOMING_REMINDER_CODE;

        if (type == Constants.DAILY_REMINDER_CODE) {
            showDailyNotification(context, Constants.DAILY_REMINDER_CODE);
        }

        if (type == Constants.UPCOMING_REMINDER_CODE) {
            context.startService(new Intent(context, UpcomingMovieServices.class));
        }
    }


    public void setDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Keys.KEY_TYPE, Constants.TYPE_DAILY);
        Calendar calender = Calendar.getInstance();
        intent.addFlags(FLAG_EXCLUDE_STOPPED_PACKAGES);
        calender.set(Calendar.HOUR_OF_DAY, 7);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.DAILY_REMINDER_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void setUpcomingReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Keys.KEY_TYPE, Constants.TYPE_UPCOMING);
        intent.addFlags(FLAG_EXCLUDE_STOPPED_PACKAGES);
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY, 8);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Constants.UPCOMING_REMINDER_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(Constants.TYPE_DAILY) ? Constants.DAILY_REMINDER_CODE : Constants.UPCOMING_REMINDER_CODE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showDailyNotification(Context context, int code) {
        Intent notifIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(notifIntent)
                .getPendingIntent(code, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification;
        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(String.format("%s %s", context.getString(R.string.app_name),
                        context.getString(R.string.label_missing_you)))
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(code, notification);
        }
    }
}
