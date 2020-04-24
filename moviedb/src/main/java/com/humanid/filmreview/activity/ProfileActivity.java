package com.humanid.filmreview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.humanid.filmreview.R;
import com.humanid.filmreview.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, DialogUtils.OnButtonNegativeListener, DialogUtils.OnButtonPositiveListener {
    @BindView(R.id.toolbarProfile)
    Toolbar toolbar;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.tvPhone)
    TextView tvPhone;

    @BindView(R.id.llSetting)
    LinearLayout llSetting;

    @BindView(R.id.llFavorite)
    LinearLayout llFavorite;

    @BindView(R.id.btnLogout)
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.label_profile);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        llSetting.setOnClickListener(this);
        llFavorite.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llSetting:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.llFavorite:
                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                startActivity(favoriteIntent);
                break;
            case R.id.btnLogout:
                DialogUtils.showCancelableDialogWithoutIcon(
                        this,
                        getString(R.string.label_logout),
                        getString(R.string.message_logout),
                        getString(R.string.action_logout),
                        getString(R.string.action_cancel),
                        this,
                        this
                        );
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onButtonNegativeClicked() {

    }

    @Override
    public void onButtonPositiveClicked() {

    }
}
