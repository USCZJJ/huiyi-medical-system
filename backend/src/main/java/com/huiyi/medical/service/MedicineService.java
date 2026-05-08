package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.MedicineQuery;
import com.huiyi.medical.entity.Medicine;
import com.huiyi.medical.mapper.MedicineMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class MedicineService {
    private final MedicineMapper mapper;
    public MedicineService(MedicineMapper mapper) { this.mapper = mapper; }
    public PageResult<Medicine> page(MedicineQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public Medicine detail(Long id) { Medicine e = mapper.findById(id); if (e == null) throw new BusinessException(404, "药品不存在"); return e; }
    public Long create(Medicine e) { validate(e); mapper.insert(e); return e.getId(); }
    public void update(Long id, Medicine e) { e.setId(id); validate(e); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(Medicine e) {
        if (e.getMedicineName() == null || e.getMedicineName().isBlank()) throw new BusinessException(400, "药品名称不能为空");
        if (e.getType() == null || e.getType().isBlank()) throw new BusinessException(400, "药品类型不能为空");
        if (e.getSpecification() == null || e.getSpecification().isBlank()) throw new BusinessException(400, "规格不能为空");
        if (e.getManufacturer() == null || e.getManufacturer().isBlank()) throw new BusinessException(400, "生产厂家不能为空");
        if (e.getPrice() == null || e.getPrice().compareTo(BigDecimal.ZERO) < 0) throw new BusinessException(400, "价格必须为非负数");
    }
}

