package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.MedicineQuery;
import com.huiyi.medical.entity.Medicine;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MedicineMapper {
    List<Medicine> findPage(@Param("query") MedicineQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") MedicineQuery query);
    Medicine findById(@Param("id") Long id);
    int insert(Medicine entity);
    int update(Medicine entity);
    int logicalDelete(@Param("id") Long id);
    long activeCount();
}

