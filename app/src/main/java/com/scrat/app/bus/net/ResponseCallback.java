package com.scrat.app.bus.net;

import com.scrat.app.bus.utils.ActivityUtils;
import com.scrat.app.bus.utils.L;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yixuanxuan on 16/4/29.
 */
public abstract class ResponseCallback<T> implements Callback {
    protected abstract void onRequestSuccess(T t);

    protected abstract T parseResponse(Response response);

    protected abstract void onRequestFailure(Exception e);

    @Override
    public void onFailure(final Call call, final IOException e) {
        ActivityUtils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onRequestFailure(e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        L.d("code %s", response.code());

        if (!response.isSuccessful()) {
            onFailure(call, new IOException("Unexpected code " + response));
            return;
        }

        T t;
        try {
            t = parseResponse(response);
        } catch (Exception e) {
            onFailure(call, new IOException(e));
            return;
        }

        if (t == null) {
            onFailure(call, new IOException("解析出错"));
            return;
        }

        notifyResponseSuccess(t);
    }

    private void notifyResponseSuccess(final T t) {
        ActivityUtils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onRequestSuccess(t);
            }
        });
    }

}
