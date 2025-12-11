package org.woheller69.lavatories.api.openstreetmap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import org.woheller69.lavatories.BuildConfig;
import org.woheller69.lavatories.http.HttpRequestType;
import org.woheller69.lavatories.http.VolleyHttpRequest;
import org.woheller69.lavatories.api.IHttpRequest;

/**
 * This class provides the functionality for making and processing HTTP requests to
 * OpenStreetMap to retrieve the list of lavatories.
 */
public class OSMHttpRequestForToilets implements IHttpRequest {

    /**
     * Member variables.
     */
    private final Context context;

    /**
     * @param context The context to use.
     */
    public OSMHttpRequestForToilets(Context context) {
        this.context = context;
    }

    /**
     * @see IHttpRequest#perform(float, float,int)
     */
    @Override
    public void perform(float lat, float lon, int cityId) {
        org.woheller69.lavatories.http.IHttpRequest httpRequest = new VolleyHttpRequest(context, cityId);
        final String URL = getUrlForQueryingLavatories(context, lat, lon);
        httpRequest.make(URL, HttpRequestType.GET, new OSMProcessHttpRequestToilets(context));
    }

    protected String getUrlForQueryingLavatories(Context context, float lat, float lon) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String url = String.format(
                "%s/%s/%s/%s",
                BuildConfig.BASE_URL,
                lat,
                lon,
                Double.parseDouble(sharedPreferences.getString("pref_searchRadius","30000"))/1000
        );

        Log.d("url",url);
        return url;
    }
}
