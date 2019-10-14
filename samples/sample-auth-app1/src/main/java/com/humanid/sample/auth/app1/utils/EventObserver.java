package com.humanid.sample.auth.app1.utils;

import androidx.lifecycle.Observer;

public class EventObserver <T> implements Observer<Event<? extends T>> {

    public interface EventUnhandledContent<T> {
        void onEventUnhandledContent(T t);
    }

    private EventUnhandledContent<T> content;

    public EventObserver(EventUnhandledContent<T> content) {
        this.content = content;
    }

    @Override
    public void onChanged(Event<? extends T> event) {
        if (event != null) {
            T result = event.getContentIfNotHandled();
            if (result != null && content != null) {
                content.onEventUnhandledContent(result);
            }
        }
    }
}
