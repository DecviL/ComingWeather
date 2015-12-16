package com.zjt.comingweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.zjt.comingweather.db.ComingWeatherDB;
import com.zjt.comingweather.model.City;
import com.zjt.comingweather.model.County;
import com.zjt.comingweather.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2015/12/15.
 */
public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvincesResponse(ComingWeatherDB comingWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    comingWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public synchronized static boolean handleCitiesResponse(ComingWeatherDB comingWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    comingWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public synchronized static boolean handleCountiesResponse(ComingWeatherDB comingWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String a : allCounties) {
                    String[] array = a.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    comingWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地
     */
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            String city = data.getString("city");
            String aqi = data.getString("aqi");
            String wendu = data.getString("wendu");
            String ganmao = data.getString("ganmao");
            JSONArray forecast = data.getJSONArray("forecast");
            String fengxiang = null;
            String fengli = null;
            String high = null;
            String low = null;
            String type = null;
            String date = null;
            for (int i = 0; i < forecast.length(); i++) {
                JSONObject weatherinfo = forecast.getJSONObject(i);
                fengxiang = weatherinfo.getString("fengxiang");
                fengli = weatherinfo.getString("fengli");
                high = weatherinfo.getString("high");
                low = weatherinfo.getString("low");
                type = weatherinfo.getString("type");
                date = weatherinfo.getString("date");
            }
            saveWeatherInfo(context, city, aqi, wendu, ganmao, fengxiang, fengli, high, low, type, date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的所有天气信息存储到sharedpreferences文件中。
     */
    public static void saveWeatherInfo(Context context, String city, String aqi, String wendu, String ganmao, String fengxiang, String fengli, String high, String low, String type, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("city", city);
        editor.putString("aqi", aqi);
        editor.putString("wendu", wendu);
        editor.putString("ganmao", ganmao);
        editor.putString("fengxiang", fengxiang);
        editor.putString("fengli", fengli);
        editor.putString("high", high);
        editor.putString("low", low);
        editor.putString("type", type);
        editor.putString("date", date);
        editor.commit();
    }

}
