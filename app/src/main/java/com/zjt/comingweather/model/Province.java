package com.zjt.comingweather.model;

/**
 * Created by Decvil on 2015/12/2.
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                '}';
    }
}
