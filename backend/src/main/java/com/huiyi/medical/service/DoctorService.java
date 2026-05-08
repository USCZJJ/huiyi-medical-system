package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.DoctorQuery;
import com.huiyi.medical.entity.Doctor;
import com.huiyi.medical.mapper.DoctorMapper;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final DoctorMapper mapper;
    public DoctorService(DoctorMapper mapper) { this.mapper = mapper; }
    public PageResult<Doctor> page(DoctorQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public Doctor detail(Long id) { Doctor e = mapper.findById(id); if (e == null) throw new BusinessException(404, "医生不存在"); return e; }
    public Long create(Doctor e) { validate(e); mapper.insert(e); return e.getId(); }
    public void update(Long id, Doctor e) { e.setId(id); validate(e); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(Doctor e) {
        if (e.getDoctorName() == null || e.getDoctorName().isBlank()) throw new BusinessException(400, "医生姓名不能为空");
        if (e.getDepartment() == null || e.getDepartment().isBlank()) throw new BusinessException(400, "科室不能为空");
        if (e.getTitle() == null || e.getTitle().isBlank()) throw new BusinessException(400, "职称不能为空");
        if (e.getPhone() == null || e.getPhone().isBlank()) throw new BusinessException(400, "联系电话不能为空");
        if (e.getEmail() == null || !e.getEmail().contains("@")) throw new BusinessException(400, "邮箱格式不正确");
    }
}

