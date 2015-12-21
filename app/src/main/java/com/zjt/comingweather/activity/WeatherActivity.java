package com.zjt.comingweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjt.comingweather.R;
import com.zjt.comingweather.util.HttpCallbackListener;
import com.zjt.comingweather.util.HttpUtil;
import com.zjt.comingweather.util.Utility;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2015/12/16.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;
    private TextView city;
    private TextView aqi;
    private TextView wendu;
    private TextView ganmao;
    private TextView fengxiang;
    private TextView fengli;
    private TextView high;
    private TextView low;
    private TextView type;
    private TextView date;

    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        initViews();
    }

    private void initViews() {
        weatherInfoLayout = (LinearLayout) findViewById(R.id.layout_weather_info);
        city = (TextView) findViewById(R.id.tv_city_name);
        aqi = (TextView) findViewById(R.id.tv_aqi);
        wendu = (TextView) findViewById(R.id.tv_wendu);
        ganmao = (TextView) findViewById(R.id.tv_ganmao);
        fengxiang = (TextView) findViewById(R.id.tv_fengxiang);
        fengli = (TextView) findViewById(R.id.tv_fengli);
        high = (TextView) findViewById(R.id.tv_wendu_high);
        low = (TextView) findViewById(R.id.tv_wendu_low);
        type = (TextView) findViewById(R.id.tv_type);
        date = (TextView) findViewById(R.id.tv_date);
        switchCity = (Button) findViewById(R.id.btn_switch);
        refreshWeather = (Button) findViewById(R.id.btn_refresh);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            date.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            city.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            showWeather();
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_refresh:
                date.setText("同步中...");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = prefs.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    queryWeatherCode(weatherCode);
                }
                break;
            default:
                break;
        }

    }

    //查询县级代号对应的天气代号
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    //根据传入的地址和类型去向服务器查询天气代号或者天气信息。
    private void queryFromServer(String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        date.setText("同步失败");
                    }
                });
            }
        });
    }

    private void queryWeatherInfo(String weatherCode) {
        String address = "";
        queryFromServer(address, "weatherCode");
    }

    //从sharedpreferces文件中读取存储的天气信息，显示天气到界面上
    private void showWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        city.setText(preferences.getString("city", ""));
        aqi.setText(preferences.getString("aqi", ""));
        date.setText(preferences.getString("date", ""));
        wendu.setText(preferences.getString("wendu", ""));
        high.setText(preferences.getString("high", ""));
        low.setText(preferences.getString("low", ""));
        type.setText(preferences.getString("type", ""));
        fengxiang.setText(preferences.getString("fengxiang", ""));
        fengli.setText(preferences.getString("fengli", ""));
        ganmao.setText(preferences.getString("ganmao", ""));
    }
}
