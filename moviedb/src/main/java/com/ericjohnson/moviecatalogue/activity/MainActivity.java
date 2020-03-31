package com.ericjohnson.moviecatalogue.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.nbs.humanidui.presentation.HumanIDUI;
import com.nbs.humanidui.util.LoginEvent;
import com.nbs.humanidui.util.LogoutEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbarMain)
    Toolbar toolbarMain;

    @BindView(R.id.vpMain)
    ViewPager vpMain;

    @BindView(R.id.tabMain)
    TabLayout tabMain;

    @BindView(R.id.imgProfile)
    ImageView imgProfile;

    @BindView(R.id.edtSearch)
    EditText edtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        vpMain.setAdapter(viewPagerAdapter);
        tabMain.setupWithViewPager(vpMain);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent profileIntent = new Intent(MainActivity.this,  ProfileActivity.class);
//                startActivity(profileIntent);
                HumanIDUI.Companion.getInstance()
                        .verifyLogin(getSupportFragmentManager());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAvatar();
    }

    private void setUpAvatar() {
        int avatar = 0;
        if (HumanIDUI.Companion.getInstance().isLoggedIn()){
            avatar = R.drawable.wolverine;
        }else{
            avatar = R.drawable.ic_person_black_24dp;
        }

        Glide.with(this)
                .load(avatar)
                .into(imgProfile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_setting) {
            Intent searchIntent = new Intent(this, SettingActivity.class);
            startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent e){
        setUpAvatar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent e){
        setUpAvatar();
    }
}