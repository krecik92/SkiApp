package com.pieprzyca.dawid.skiapp.service;

import com.pieprzyca.dawid.skiapp.data.Channel;

/**
 * Created by Dawid on 01.06.2016.
 */
public interface WheatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
