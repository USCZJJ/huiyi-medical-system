package com.huiyi.medical.dto;

public class CityQuery extends PageQuery {
    private String cityName;
    private String province;
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
}

