package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.DoctorQuery;
import com.huiyi.medical.entity.Doctor;
import com.huiyi.medical.service.DoctorService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService service;
    public DoctorController(DoctorService service) { this.service = service; }
    @GetMapping public Result<PageResult<Doctor>> page(DoctorQuery query) { return Result.success(service.page(query)); }
    @GetMapping("/{id}") public Result<Doctor> detail(@PathVariable Long id) { return Result.success(service.detail(id)); }
    @PostMapping public Result<Map<String, Long>> create(@RequestBody Doctor entity) { return Result.success(Map.of("id", service.create(entity))); }
    @PutMapping("/{id}") public Result<Void> update(@PathVariable Long id, @RequestBody Doctor entity) { service.update(id, entity); return Result.success(); }
    @DeleteMapping("/{id}") public Result<Void> delete(@PathVariable Long id) { service.delete(id); return Result.success(); }
}

