package com.humanid.filmreview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.humanid.filmreview.R;
import com.humanid.filmreview.adapter.ViewPagerAdapter;
import com.humanid.filmreview.domain.LoginHttpRequest;
import com.humanid.filmreview.domain.UserInteractor;
import com.humanid.filmreview.domain.UserUsecase;
import com.humanid.humanidui.presentation.LoginCallback;
import com.humanid.humanidui.presentation.LoginManager;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

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

    private LoginManager loginManager;

    private UserUsecase userUsecase;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        vpMain.setAdapter(viewPagerAdapter);
        tabMain.setupWithViewPager(vpMain);

        progressDialog = new ProgressDialog(this);


        userUsecase = new UserInteractor(this);

        imgProfile.setOnClickListener(view -> {
            if (UserInteractor.getInstance(this).isLoggedIn()){
                Toast.makeText(this, "You're logged in anonymously", Toast.LENGTH_SHORT).show();
            }else{
                loginManager.registerCallback(new LoginCallback() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "request cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(@NotNull String exchangeToken) {
                        Log.d("GotExchangeToken", exchangeToken);
                        authenticateUser(exchangeToken);
                    }

                    @Override
                    public void onError(@NotNull String errorMessage) {
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginManager = LoginManager.INSTANCE.getInstance(this);

        Log.d("UUID", UUID.randomUUID().toString());

    }

    private void authenticateUser(String exchangeToken) {
        userUsecase.login(exchangeToken, new LoginHttpRequest.OnLoginCallback() {
            @Override
            public void onLoading() {
                progressDialog.setMessage("Please wait");
                progressDialog.show();
            }

            @Override
            public void onLoginSuccess() {
                if (progressDialog != null){
                    progressDialog.dismiss();
                }
                setUpAvatar(userUsecase.isLoggedIn());
            }

            @Override
            public void onLoginFailed(String message) {
                if (progressDialog != null){
                    progressDialog.dismiss();
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpAvatar(userUsecase.isLoggedIn());
    }

    private void setUpAvatar(Boolean isLoggeIn) {
        int avatar = 0;
        if (isLoggeIn){
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        loginManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}