package com.humanid.filmreview.utils;

import android.content.Context;

public class ContextProvider {
    private static ContextProvider mInstance;
    private static Context mContext;

    private static ContextProvider getInstance(){
        if (mInstance == null){
            mInstance = new ContextProvider();
        }
        return mInstance;
    }


    private void init(Context context){
        mContext =  context;
    }

    private Context getContext(){
        if (mContext == null){
            throw new IllegalStateException("call init first");
        }
        return mContext;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public function
    ///////////////////////////////////////////////////////////////////////////

    public static void initialize (Context context){
        getInstance().init(context);
    }

    public static Context get (){
        return getInstance().getContext();
    }
}

