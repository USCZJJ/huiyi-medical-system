package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.CityQuery;
import com.huiyi.medical.entity.City;
import com.huiyi.medical.service.CityService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService service;
    public CityController(CityService service) { this.service = service; }
    @GetMapping public Result<PageResult<City>> page(CityQuery query) { return Result.success(service.page(query)); }
    @GetMapping("/{id}") public Result<City> detail(@PathVariable Long id) { return Result.success(service.detail(id)); }
    @PostMapping public Result<Map<String, Long>> create(@RequestBody City entity) { return Result.success(Map.of("id", service.create(entity))); }
    @PutMapping("/{id}") public Result<Void> update(@PathVariable Long id, @RequestBody City entity) { service.update(id, entity); return Result.success(); }
    @DeleteMapping("/{id}") public Result<Void> delete(@PathVariable Long id) { service.delete(id); return Result.success(); }
}

