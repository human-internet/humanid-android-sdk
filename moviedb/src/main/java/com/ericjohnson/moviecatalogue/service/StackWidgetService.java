package com.ericjohnson.moviecatalogue.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.ericjohnson.moviecatalogue.widget.StackRemoteViewsFactory;

public class StackWidgetService  extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
