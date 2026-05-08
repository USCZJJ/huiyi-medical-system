package com.huiyi.medical.entity;

public class City extends BaseEntity {
    private String cityName;
    private String province;
    private String code;

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}

