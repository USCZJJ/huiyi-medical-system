package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.MedicineQuery;
import com.huiyi.medical.entity.Medicine;
import com.huiyi.medical.service.MedicineService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {
    private final MedicineService service;
    public MedicineController(MedicineService service) { this.service = service; }
    @GetMapping public Result<PageResult<Medicine>> page(MedicineQuery query) { return Result.success(service.page(query)); }
    @GetMapping("/{id}") public Result<Medicine> detail(@PathVariable Long id) { return Result.success(service.detail(id)); }
    @PostMapping public Result<Map<String, Long>> create(@RequestBody Medicine entity) { return Result.success(Map.of("id", service.create(entity))); }
    @PutMapping("/{id}") public Result<Void> update(@PathVariable Long id, @RequestBody Medicine entity) { service.update(id, entity); return Result.success(); }
    @DeleteMapping("/{id}") public Result<Void> delete(@PathVariable Long id) { service.delete(id); return Result.success(); }
}

