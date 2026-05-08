package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.LocationQuery;
import com.huiyi.medical.entity.Location;
import com.huiyi.medical.service.LocationService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService service;
    public LocationController(LocationService service) { this.service = service; }
    @GetMapping public Result<PageResult<Location>> page(LocationQuery query) { return Result.success(service.page(query)); }
    @GetMapping("/{id}") public Result<Location> detail(@PathVariable Long id) { return Result.success(service.detail(id)); }
    @PostMapping public Result<Map<String, Long>> create(@RequestBody Location entity) { return Result.success(Map.of("id", service.create(entity))); }
    @PutMapping("/{id}") public Result<Void> update(@PathVariable Long id, @RequestBody Location entity) { service.update(id, entity); return Result.success(); }
    @DeleteMapping("/{id}") public Result<Void> delete(@PathVariable Long id) { service.delete(id); return Result.success(); }
}

