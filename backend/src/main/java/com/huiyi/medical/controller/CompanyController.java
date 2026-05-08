package com.huiyi.medical.controller;

import com.huiyi.medical.common.PageResult;
import com.huiyi.medical.common.Result;
import com.huiyi.medical.dto.CompanyQuery;
import com.huiyi.medical.entity.Company;
import com.huiyi.medical.service.CompanyService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService service;
    public CompanyController(CompanyService service) { this.service = service; }
    @GetMapping public Result<PageResult<Company>> page(CompanyQuery query) { return Result.success(service.page(query)); }
    @GetMapping("/{id}") public Result<Company> detail(@PathVariable Long id) { return Result.success(service.detail(id)); }
    @PostMapping public Result<Map<String, Long>> create(@RequestBody Company entity) { return Result.success(Map.of("id", service.create(entity))); }
    @PutMapping("/{id}") public Result<Void> update(@PathVariable Long id, @RequestBody Company entity) { service.update(id, entity); return Result.success(); }
    @DeleteMapping("/{id}") public Result<Void> delete(@PathVariable Long id) { service.delete(id); return Result.success(); }
}

