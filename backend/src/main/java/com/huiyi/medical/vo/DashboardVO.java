package com.huiyi.medical.vo;

import com.huiyi.medical.entity.Policy;
import java.util.List;

public class DashboardVO {
    private long companyCount;
    private long materialCount;
    private long cityCount;
    private long locationCount;
    private long medicineCount;
    private long doctorCount;
    private List<DistributionVO> doctorTitleDistribution;
    private List<DistributionVO> departmentDistribution;
    private List<Policy> latestPolicies;

    public long getCompanyCount() { return companyCount; }
    public void setCompanyCount(long companyCount) { this.companyCount = companyCount; }
    public long getMaterialCount() { return materialCount; }
    public void setMaterialCount(long materialCount) { this.materialCount = materialCount; }
    public long getCityCount() { return cityCount; }
    public void setCityCount(long cityCount) { this.cityCount = cityCount; }
    public long getLocationCount() { return locationCount; }
    public void setLocationCount(long locationCount) { this.locationCount = locationCount; }
    public long getMedicineCount() { return medicineCount; }
    public void setMedicineCount(long medicineCount) { this.medicineCount = medicineCount; }
    public long getDoctorCount() { return doctorCount; }
    public void setDoctorCount(long doctorCount) { this.doctorCount = doctorCount; }
    public List<DistributionVO> getDoctorTitleDistribution() { return doctorTitleDistribution; }
    public void setDoctorTitleDistribution(List<DistributionVO> doctorTitleDistribution) { this.doctorTitleDistribution = doctorTitleDistribution; }
    public List<DistributionVO> getDepartmentDistribution() { return departmentDistribution; }
    public void setDepartmentDistribution(List<DistributionVO> departmentDistribution) { this.departmentDistribution = departmentDistribution; }
    public List<Policy> getLatestPolicies() { return latestPolicies; }
    public void setLatestPolicies(List<Policy> latestPolicies) { this.latestPolicies = latestPolicies; }
}

