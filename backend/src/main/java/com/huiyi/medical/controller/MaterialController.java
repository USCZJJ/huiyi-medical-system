package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.MaterialQuery;
import com.huiyi.medical.entity.Material;
import com.huiyi.medical.service.MaterialService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    private final MaterialService service;
    public MaterialController(MaterialService service) { this.service = service; }
    @GetMapping public Result<PageResult<Material>> page(MaterialQuery query) { return Result.success(service.page(query)); }
    @GetMapping("/{id}") public Result<Material> detail(@PathVariable Long id) { return Result.success(service.detail(id)); }
    @PostMapping public Result<Map<String, Long>> create(@RequestBody Material entity) { return Result.success(Map.of("id", service.create(entity))); }
    @PutMapping("/{id}") public Result<Void> update(@PathVariable Long id, @RequestBody Material entity) { service.update(id, entity); return Result.success(); }
    @DeleteMapping("/{id}") public Result<Void> delete(@PathVariable Long id) { service.delete(id); return Result.success(); }
}

