package com.huiyi.medical.dto;

public class LocationQuery extends PageQuery {
    private String locationName;
    private String city;
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}

