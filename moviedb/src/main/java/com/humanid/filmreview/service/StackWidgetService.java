package com.humanid.filmreview.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.humanid.filmreview.widget.StackRemoteViewsFactory;

public class StackWidgetService  extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
