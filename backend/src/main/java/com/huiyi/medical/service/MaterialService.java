package com.huiyi.medical.service;

import com.huiyi.medical.common.BusinessException;
import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.dto.MaterialQuery;
import com.huiyi.medical.entity.Material;
import com.huiyi.medical.mapper.MaterialMapper;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    private final MaterialMapper mapper;
    public MaterialService(MaterialMapper mapper) { this.mapper = mapper; }
    public PageResult<Material> page(MaterialQuery q) { return new PageResult<>(mapper.findPage(q, q.offset(), q.safePageSize()), mapper.count(q), q.safePage(), q.safePageSize()); }
    public Material detail(Long id) { Material e = mapper.findById(id); if (e == null) throw new BusinessException(404, "材料不存在"); return e; }
    public Long create(Material e) { validate(e); mapper.insert(e); return e.getId(); }
    public void update(Long id, Material e) { e.setId(id); validate(e); mapper.update(e); }
    public void delete(Long id) { mapper.logicalDelete(id); }
    private void validate(Material e) {
        if (e.getMaterialName() == null || e.getMaterialName().isBlank()) throw new BusinessException(400, "材料名称不能为空");
        if (e.getType() == null || e.getType().isBlank()) throw new BusinessException(400, "材料类型不能为空");
    }
}

