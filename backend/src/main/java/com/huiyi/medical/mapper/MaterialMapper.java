package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.MaterialQuery;
import com.huiyi.medical.entity.Material;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MaterialMapper {
    List<Material> findPage(@Param("query") MaterialQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") MaterialQuery query);
    Material findById(@Param("id") Long id);
    int insert(Material entity);
    int update(Material entity);
    int logicalDelete(@Param("id") Long id);
    long activeCount();
}

