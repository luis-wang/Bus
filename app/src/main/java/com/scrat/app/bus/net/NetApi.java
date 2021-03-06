package com.scrat.app.bus.net;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by yixuanxuan on 16/4/19.
 */
public class NetApi {
    private static final String sKeyName = "name";
    private static final String sKeyReferer = "Referer";
    private static final String sKeyCookie = "Cookie";

    private static final String sUrlGetBusName
            = "http://wxbus.gzyyjt.net/wei-bus-app/route/getByName";
    private static final String sUrlGetBusStopName
            = "http://wxbus.gzyyjt.net/wei-bus-app/routeStation/getByRouteAndDirection/%s/%s";
    private static final String sUrlGetBusStopLocation
            = "http://wxbus.gzyyjt.net/wei-bus-app/runBus/getByRouteAndDirection/%s/%s";
    private static final String sHeaders
            = "http://wxbus.gzyyjt.net/wei-bus-app/route/monitor/%s/%s";
    private static final String sBusInfoHeaders
            = "http://wxbus.gzyyjt.net/wei-bus-app/route?openId=ouz9MsyNIpeYEMJEhI7E-peH3oOk";
    private static final String sCookie =
            "realOpenId=ouz9MsyNIpeYEMJEhI7E-peH3oOk; openId=ouz9MsyNIpeYEMJEhI7E-peH3oOk";

    public static Call getBusInfo(String busNo, Callback responseCallback) {
        Map<String, String> params = new OkHttpHelper.ParamsBuilder().put(sKeyName, busNo).build();

        Map<String, String> headers = new HashMap<>();
        headers.put(sKeyReferer, sBusInfoHeaders);
        headers.put(sKeyCookie, sCookie);

        try {
            return OkHttpHelper.getInstance().post(sUrlGetBusName, params, headers, responseCallback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseCallback.onFailure(null, e);
            return null;
        }
    }

    public static void getBusStopName(
            String busId, boolean defaultOrder, Callback responseCallback) {

        String order = defaultOrder ? "0" : "1";
        String url = String.format(sUrlGetBusStopName, busId, order);

        String referer = String.format(sHeaders, busId, order);
        Map<String, String> headers = new HashMap<>();
        headers.put(sKeyReferer, referer);
        headers.put(sKeyCookie, sCookie);

        try {
            OkHttpHelper.getInstance().get(url, null, headers, responseCallback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseCallback.onFailure(null, e);
        }
    }

    public static void getBusLocation(
            String busId, boolean defaultOrder, Callback responseCallback) {

        String order = defaultOrder ? "0" : "1";
        String url = String.format(sUrlGetBusStopLocation, busId, order);

        String referer = String.format(sHeaders, busId, order);
        Map<String, String> headers = new HashMap<>();
        headers.put(sKeyReferer, referer);
        headers.put(sKeyCookie, sCookie);

        try {
            OkHttpHelper.getInstance().get(url, null, headers, responseCallback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseCallback.onFailure(null, e);
        }
    }

}
