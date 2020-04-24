package com.humanid.filmreview.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.humanid.filmreview.R;
import com.humanid.filmreview.service.AlarmReceiver;
import com.humanid.filmreview.utils.Constants;
import com.humanid.filmreview.utils.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_upcoming)
    TextView tvUpcoming;

    @BindView(R.id.sw_upcoming)
    Switch swUpcoming;

    @BindView(R.id.ll_language)
    LinearLayout llLanguage;

    @BindView(R.id.tv_daily_reminder)
    TextView tvDailyReminder;

    @BindView(R.id.sw_daily_reminder)
    Switch swDailyReminder;

    private AlarmReceiver alarmReceiver;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        alarmReceiver = new AlarmReceiver();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.label_setting));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        llLanguage.setOnClickListener(this);

        if (getPreferences(Context.MODE_PRIVATE).getString(Keys.PREF_DAILY_NOTIF,
                getString(R.string.label_off)).equals(getString(R.string.label_on))) {
            swDailyReminder.setChecked(true);
        }

        if (getPreferences(Context.MODE_PRIVATE).getString(Keys.PREF_UPCOMING_NOTIF,
                getString(R.string.label_off)).equals(getString(R.string.label_on))) {
            swUpcoming.setChecked(true);
        }

        swDailyReminder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
            }
        });

        swUpcoming.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
            }
        });

        swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setDailyReminder(SettingActivity.this);
                    getPreferences(Context.MODE_PRIVATE).edit().putString(Keys.PREF_DAILY_NOTIF,
                            getString(R.string.label_on)).apply();
                } else {
                    alarmReceiver.cancelAlarm(SettingActivity.this, Constants.TYPE_DAILY);
                    getPreferences(Context.MODE_PRIVATE).edit().putString(Keys.PREF_DAILY_NOTIF,
                            getString(R.string.label_off)).apply();
                }
            }
        });


        swUpcoming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.setUpcomingReminder(SettingActivity.this);
                    getPreferences(Context.MODE_PRIVATE).edit().putString(Keys.PREF_UPCOMING_NOTIF,
                            getString(R.string.label_on)).apply();
                } else {
                    alarmReceiver.cancelAlarm(SettingActivity.this, Constants.TYPE_UPCOMING);
                    getPreferences(Context.MODE_PRIVATE).edit().putString(Keys.PREF_UPCOMING_NOTIF,
                            getString(R.string.label_off)).apply();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
    }
}


