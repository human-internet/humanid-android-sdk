package com.ericjohnson.moviecatalogue.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.fragment.NowPlayingFragment;
import com.ericjohnson.moviecatalogue.fragment.UpcomingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new NowPlayingFragment();
        }
        else if (position == 1)
        {
            fragment = new UpcomingFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = context.getString(R.string.label_now_playing);
        }
        else if (position == 1)
        {
            title = context.getString(R.string.label_upcoming_movies);
        }
        return title;
    }
}
