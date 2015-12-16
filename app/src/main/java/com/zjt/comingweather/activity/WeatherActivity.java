package com.zjt.comingweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjt.comingweather.R;

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
    private TextView fegnxiang;
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
        fegnxiang = (TextView) findViewById(R.id.tv_fengxiang);
        fengli = (TextView) findViewById(R.id.tv_fengli);
        high = (TextView) findViewById(R.id.tv_wendu_high);
        low = (TextView) findViewById(R.id.tv_wendu_low);
        type = (TextView) findViewById(R.id.tv_type);
        date = (TextView) findViewById(R.id.tv_date);

    }

    @Override
    public void onClick(View v) {

    }
}
