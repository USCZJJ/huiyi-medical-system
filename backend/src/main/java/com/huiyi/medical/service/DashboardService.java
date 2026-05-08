package com.huiyi.medical.service;

import com.huiyi.medical.mapper.*;
import com.huiyi.medical.vo.DashboardVO;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final CompanyMapper companyMapper;
    private final MaterialMapper materialMapper;
    private final CityMapper cityMapper;
    private final LocationMapper locationMapper;
    private final MedicineMapper medicineMapper;
    private final DoctorMapper doctorMapper;
    private final PolicyMapper policyMapper;

    public DashboardService(CompanyMapper companyMapper, MaterialMapper materialMapper, CityMapper cityMapper, LocationMapper locationMapper, MedicineMapper medicineMapper, DoctorMapper doctorMapper, PolicyMapper policyMapper) {
        this.companyMapper = companyMapper;
        this.materialMapper = materialMapper;
        this.cityMapper = cityMapper;
        this.locationMapper = locationMapper;
        this.medicineMapper = medicineMapper;
        this.doctorMapper = doctorMapper;
        this.policyMapper = policyMapper;
    }

    public DashboardVO dashboard() {
        DashboardVO vo = new DashboardVO();
        // 仪表盘只做聚合读取，不修改任何业务数据。
        vo.setCompanyCount(companyMapper.activeCount());
        vo.setMaterialCount(materialMapper.activeCount());
        vo.setCityCount(cityMapper.activeCount());
        vo.setLocationCount(locationMapper.activeCount());
        vo.setMedicineCount(medicineMapper.activeCount());
        vo.setDoctorCount(doctorMapper.activeCount());
        vo.setDoctorTitleDistribution(doctorMapper.countByTitle());
        vo.setDepartmentDistribution(doctorMapper.countByDepartment());
        vo.setLatestPolicies(policyMapper.findLatest(5));
        return vo;
    }
}

