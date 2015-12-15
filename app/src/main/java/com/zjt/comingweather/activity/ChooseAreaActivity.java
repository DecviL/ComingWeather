package com.zjt.comingweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zjt.comingweather.db.ComingWeatherDB;
import com.zjt.comingweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class ChooseAreaActivity extends Activity {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ComingWeatherDB comingWeatherDB;
    private List<String> dataList = new ArrayList<String>();
    /**
     * 省列表
     * */
    private List<Province> provinceList;
    /**
     * 市列表
     * */
    private List<Province> cityList;
    /**
     * 县列表
     * */
    private List<Province> countyList;


}
