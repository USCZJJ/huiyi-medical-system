package com.huiyi.medical.mapper;

import com.huiyi.medical.dto.CityQuery;
import com.huiyi.medical.entity.City;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CityMapper {
    List<City> findPage(@Param("query") CityQuery query, @Param("offset") int offset, @Param("pageSize") int pageSize);
    long count(@Param("query") CityQuery query);
    City findById(@Param("id") Long id);
    City findByCode(@Param("code") String code);
    int insert(City entity);
    int update(City entity);
    int logicalDelete(@Param("id") Long id);
    long activeCount();
}

