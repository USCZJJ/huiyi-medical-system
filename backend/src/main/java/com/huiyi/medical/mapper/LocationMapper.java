package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.LocationQuery;
import com.huiyi.medical.entity.Location;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface LocationMapper {
    List<Location> findPage(@Param("query") LocationQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") LocationQuery query);
    Location findById(@Param("id") Long id);
    int insert(Location entity);
    int update(Location entity);
    int logicalDelete(@Param("id") Long id);
    long activeCount();
}

