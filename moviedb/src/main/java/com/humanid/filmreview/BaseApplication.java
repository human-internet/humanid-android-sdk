package com.humanid.filmreview;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.humanid.filmreview.utils.ContextProvider;


/**
 * Created by Dimas Prakoso on 26/11/2019.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
        ContextProvider.initialize(this);
    }
}
