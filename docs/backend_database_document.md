# 慧医数字医疗应用系统后端数据库信息

## 1. 数据库概述

前端当前覆盖用户、权限、医药公司、政策、必备材料、城市、销售地点、药品、医生、仪表盘统计等功能。后端数据库建议使用 MySQL 8.0，字符集使用 `utf8mb4`，排序规则使用 `utf8mb4_0900_ai_ci` 或 `utf8mb4_general_ci`。

建议数据库名：

```sql
medical
```

## 2. 通用字段建议

业务表建议统一包含：

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| id | BIGINT PRIMARY KEY AUTO_INCREMENT | 主键 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |
| deleted | TINYINT | 逻辑删除标记，0 正常，1 删除 |

## 3. 表结构设计

### 3.1 用户表 `sys_user`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 用户 ID |
| username | VARCHAR(50) | UNIQUE, NOT NULL | 用户名 |
| password_hash | VARCHAR(255) | NOT NULL | 加密密码 |
| name | VARCHAR(50) | NOT NULL | 姓名/昵称 |
| role | VARCHAR(30) | NOT NULL | 角色：admin/user 等 |
| status | TINYINT | DEFAULT 1 | 状态：1 启用，0 禁用 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
UNIQUE KEY uk_sys_user_username (username)
```

### 3.2 权限表 `sys_permission`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 权限 ID |
| permission_code | VARCHAR(100) | UNIQUE, NOT NULL | 权限编码 |
| permission_name | VARCHAR(100) | NOT NULL | 权限名称 |
| module | VARCHAR(50) | NOT NULL | 所属模块 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |

示例权限编码：

- `user:list`
- `user:auth`
- `company:list`
- `company:create`
- `company:update`
- `company:delete`
- `policy:list`
- `material:list`
- `city:list`
- `location:list`
- `medicine:list`
- `doctor:list`

### 3.3 用户权限关联表 `sys_user_permission`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| user_id | BIGINT | NOT NULL | 用户 ID |
| permission_id | BIGINT | NOT NULL | 权限 ID |
| created_at | DATETIME | NOT NULL | 创建时间 |

索引：

```sql
UNIQUE KEY uk_user_permission (user_id, permission_id)
```

### 3.4 医药公司表 `company`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 公司 ID |
| company_name | VARCHAR(100) | NOT NULL | 公司名称 |
| contact | VARCHAR(50) | NOT NULL | 联系人 |
| phone | VARCHAR(30) | NOT NULL | 联系电话 |
| city | VARCHAR(50) | NOT NULL | 城市 |
| address | VARCHAR(255) | NOT NULL | 地址 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
KEY idx_company_name (company_name)
KEY idx_company_city (city)
```

### 3.5 政策表 `policy`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 政策 ID |
| policy_name | VARCHAR(100) | NOT NULL | 政策名称 |
| company_id | BIGINT | NOT NULL | 适用公司 ID |
| effective_date | DATE | NOT NULL | 生效日期 |
| expiry_date | DATE | NOT NULL | 过期日期 |
| content | TEXT | NULL | 政策内容 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
KEY idx_policy_name (policy_name)
KEY idx_policy_company_id (company_id)
KEY idx_policy_effective_date (effective_date)
KEY idx_policy_expiry_date (expiry_date)
```

### 3.6 必备材料表 `material`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 材料 ID |
| material_name | VARCHAR(100) | NOT NULL | 材料名称 |
| type | VARCHAR(50) | NOT NULL | 材料类型 |
| description | VARCHAR(500) | NULL | 描述 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
KEY idx_material_name (material_name)
KEY idx_material_type (type)
```

### 3.7 城市表 `city`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 城市 ID |
| city_name | VARCHAR(50) | NOT NULL | 城市名称 |
| province | VARCHAR(50) | NOT NULL | 所属省份 |
| code | VARCHAR(20) | NOT NULL | 城市代码 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
UNIQUE KEY uk_city_code (code)
KEY idx_city_name (city_name)
KEY idx_city_province (province)
```

### 3.8 销售地点表 `location`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 地点 ID |
| location_name | VARCHAR(100) | NOT NULL | 地点名称 |
| city | VARCHAR(50) | NOT NULL | 城市 |
| address | VARCHAR(255) | NOT NULL | 地址 |
| contact | VARCHAR(50) | NOT NULL | 联系人 |
| phone | VARCHAR(30) | NOT NULL | 联系电话 |
| longitude | DECIMAL(10,6) | NULL | 经度，地图展示需要 |
| latitude | DECIMAL(10,6) | NULL | 纬度，地图展示需要 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
KEY idx_location_name (location_name)
KEY idx_location_city (city)
KEY idx_location_geo (longitude, latitude)
```

### 3.9 药品表 `medicine`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 药品 ID |
| medicine_name | VARCHAR(100) | NOT NULL | 药品名称 |
| type | VARCHAR(50) | NOT NULL | 药品类型 |
| specification | VARCHAR(100) | NOT NULL | 规格 |
| price | DECIMAL(10,2) | NOT NULL | 价格 |
| manufacturer | VARCHAR(100) | NOT NULL | 生产厂家 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
KEY idx_medicine_name (medicine_name)
KEY idx_medicine_type (type)
KEY idx_medicine_manufacturer (manufacturer)
```

### 3.10 医生表 `doctor`

| 字段 | 类型 | 约束 | 说明 |
| --- | --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT | 医生 ID |
| doctor_name | VARCHAR(50) | NOT NULL | 医生姓名 |
| department | VARCHAR(50) | NOT NULL | 科室 |
| title | VARCHAR(50) | NOT NULL | 职称 |
| phone | VARCHAR(30) | NOT NULL | 联系电话 |
| email | VARCHAR(100) | NOT NULL | 邮箱 |
| created_at | DATETIME | NOT NULL | 创建时间 |
| updated_at | DATETIME | NOT NULL | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

索引：

```sql
KEY idx_doctor_name (doctor_name)
KEY idx_doctor_department (department)
KEY idx_doctor_title (title)
```

## 4. 前后端字段映射

| 前端字段 | 数据库字段 | 所属模块 |
| --- | --- | --- |
| username | username | 用户 |
| password | password_hash | 用户 |
| name | name | 用户 |
| role | role | 用户 |
| permissions | sys_user_permission | 用户授权 |
| companyName | company_name | 公司 |
| contact | contact | 公司、地点 |
| phone | phone | 公司、地点、医生 |
| city | city | 公司、地点 |
| address | address | 公司、地点 |
| policyName | policy_name | 政策 |
| companyId | company_id | 政策 |
| companyName | company.company_name | 政策列表关联展示 |
| effectiveDate | effective_date | 政策 |
| expiryDate | expiry_date | 政策 |
| content | content | 政策 |
| materialName | material_name | 材料 |
| type | type | 材料、药品 |
| description | description | 材料 |
| cityName | city_name | 城市 |
| province | province | 城市 |
| code | code | 城市 |
| locationName | location_name | 地点 |
| longitude | longitude | 地点 |
| latitude | latitude | 地点 |
| medicineName | medicine_name | 药品 |
| specification | specification | 药品 |
| price | price | 药品 |
| manufacturer | manufacturer | 药品 |
| doctorName | doctor_name | 医生 |
| department | department | 医生 |
| title | title | 医生 |
| email | email | 医生 |

## 5. 建表 SQL 草案

```sql
CREATE DATABASE IF NOT EXISTS medical
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE medical;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  name VARCHAR(50) NOT NULL,
  role VARCHAR(30) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  UNIQUE KEY uk_sys_user_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sys_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  permission_code VARCHAR(100) NOT NULL,
  permission_name VARCHAR(100) NOT NULL,
  module VARCHAR(50) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sys_user_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_user_permission (user_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE company (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  company_name VARCHAR(100) NOT NULL,
  contact VARCHAR(50) NOT NULL,
  phone VARCHAR(30) NOT NULL,
  city VARCHAR(50) NOT NULL,
  address VARCHAR(255) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  KEY idx_company_name (company_name),
  KEY idx_company_city (city)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_name VARCHAR(100) NOT NULL,
  company_id BIGINT NOT NULL,
  effective_date DATE NOT NULL,
  expiry_date DATE NOT NULL,
  content TEXT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  KEY idx_policy_name (policy_name),
  KEY idx_policy_company_id (company_id),
  KEY idx_policy_effective_date (effective_date),
  KEY idx_policy_expiry_date (expiry_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE material (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  material_name VARCHAR(100) NOT NULL,
  type VARCHAR(50) NOT NULL,
  description VARCHAR(500) NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  KEY idx_material_name (material_name),
  KEY idx_material_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE city (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  city_name VARCHAR(50) NOT NULL,
  province VARCHAR(50) NOT NULL,
  code VARCHAR(20) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  UNIQUE KEY uk_city_code (code),
  KEY idx_city_name (city_name),
  KEY idx_city_province (province)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE location (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  location_name VARCHAR(100) NOT NULL,
  city VARCHAR(50) NOT NULL,
  address VARCHAR(255) NOT NULL,
  contact VARCHAR(50) NOT NULL,
  phone VARCHAR(30) NOT NULL,
  longitude DECIMAL(10,6) NULL,
  latitude DECIMAL(10,6) NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  KEY idx_location_name (location_name),
  KEY idx_location_city (city),
  KEY idx_location_geo (longitude, latitude)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE medicine (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  medicine_name VARCHAR(100) NOT NULL,
  type VARCHAR(50) NOT NULL,
  specification VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  manufacturer VARCHAR(100) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  KEY idx_medicine_name (medicine_name),
  KEY idx_medicine_type (type),
  KEY idx_medicine_manufacturer (manufacturer)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE doctor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  doctor_name VARCHAR(50) NOT NULL,
  department VARCHAR(50) NOT NULL,
  title VARCHAR(50) NOT NULL,
  phone VARCHAR(30) NOT NULL,
  email VARCHAR(100) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  KEY idx_doctor_name (doctor_name),
  KEY idx_doctor_department (department),
  KEY idx_doctor_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 6. 后端需要提供的数据能力

- 登录认证：校验用户名密码，签发 JWT，返回当前用户信息。
- 用户信息：用户列表、创建、编辑、删除、当前用户详情。
- 权限管理：用户权限列表、权限更新、按角色控制前端菜单和接口访问。
- 密码管理：当前用户修改密码、管理员重置用户密码。
- 基础资料 CRUD：公司、政策、材料、城市、销售地点、药品、医生。
- 分页查询：所有列表接口支持分页、条件查询。
- 仪表盘聚合：提供各模块数量统计、医生职称分布、科室分布、最新政策。
- 地图数据：销售地点应支持经纬度，后端可提供地址地理编码结果或直接保存坐标。

## 7. 数据初始化建议

- 初始化管理员账号：`admin`
- 初始化基础角色：`admin`、`user`
- 初始化常用权限编码
- 初始化城市数据：北京、上海、广州、深圳等
- 初始化药品类型、材料类型、医生科室等字典数据，后续可扩展为独立字典表

