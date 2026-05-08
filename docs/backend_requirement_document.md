# 慧医数字医疗应用系统后端需求文档

## 1. 项目目标

建设一套与前端 Vue 管理端配套的后端服务，提供登录认证、用户管理、权限管理、医药公司管理、政策管理、必备材料管理、城市管理、销售地点管理、药品管理、医生管理、密码管理、仪表盘统计等接口能力。

后端采用 Spring Boot + MyBatis 标准分层架构，数据库使用 SQL Server 2022。接口统一以 RESTful 风格提供，响应格式统一，支持 JWT 登录鉴权、分页查询、条件筛选、基础数据增删改查和仪表盘聚合统计。

建议数据库名称：

```text
HuiYiMedicalDB
```

## 2. 技术要求

| 分类 | 技术 |
| --- | --- |
| 后端框架 | Spring Boot 3.x |
| Web 框架 | Spring MVC |
| ORM/持久层 | MyBatis |
| 数据库 | SQL Server 2022 |
| 数据库驱动 | Microsoft JDBC Driver for SQL Server |
| 安全认证 | Spring Security + JWT |
| 参数校验 | Spring Validation |
| API 文档 | SpringDoc OpenAPI / Swagger |
| 构建工具 | Maven |
| Java 版本 | JDK 17 或以上 |

## 3. 后端分层标准

建议包结构：

```text
com.huiyi.medical
├── MedicalApplication.java
├── common
│   ├── Result.java
│   ├── PageResult.java
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
├── config
│   ├── MyBatisConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── security
│   ├── JwtAuthenticationFilter.java
│   ├── JwtTokenProvider.java
│   └── LoginUserDetailsService.java
├── controller
├── service
├── service.impl
├── mapper
├── entity
├── dto
├── vo
└── util
```

分层职责：

| 层级 | 职责 |
| --- | --- |
| Controller | 接收 HTTP 请求、参数校验、返回统一响应 |
| Service | 编排业务逻辑、事务控制、权限相关判断 |
| Mapper | MyBatis 数据访问接口 |
| Entity | 数据库表映射对象 |
| DTO | 接收前端请求参数 |
| VO | 返回给前端的数据对象 |
| Common | 统一响应、异常、分页模型 |
| Security | 登录认证、JWT 校验、权限上下文 |

## 4. 通用接口规范

### 4.1 基础路径

```text
/api
```

### 4.2 统一响应

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

分页响应：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [],
    "total": 0,
    "page": 1,
    "pageSize": 10
  }
}
```

### 4.3 错误码

| code | 含义 |
| --- | --- |
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录或 Token 失效 |
| 403 | 无权限 |
| 404 | 数据不存在 |
| 409 | 数据冲突，例如用户名重复 |
| 500 | 服务端异常 |

### 4.4 鉴权规则

登录接口不需要 Token，其余接口默认需要：

```http
Authorization: Bearer <token>
```

JWT 中建议包含：

| 字段 | 说明 |
| --- | --- |
| userId | 用户 ID |
| username | 用户名 |
| role | 用户角色 |
| permissions | 权限编码列表，可选 |
| exp | 过期时间 |

## 5. 数据库要求

数据库使用 SQL Server 2022，表结构应覆盖以下表：

| 表名 | 说明 |
| --- | --- |
| sys_user | 用户表 |
| sys_permission | 权限表 |
| sys_user_permission | 用户权限关联表 |
| company | 医药公司表 |
| policy | 政策表 |
| material | 必备材料表 |
| city | 城市表 |
| location | 销售地点表 |
| medicine | 药品表 |
| doctor | 医生表 |

字段以已提供的 SQL Server 建表脚本为准。后端实体字段使用 Java 驼峰命名，数据库字段使用下划线命名，通过 MyBatis ResultMap 或开启驼峰映射处理。

建议开启：

```yaml
mybatis:
  configuration:
    map-underscore-to-camel-case: true
```

## 6. 功能需求

### 6.1 登录认证

功能说明：

- 用户输入用户名和密码登录。
- 后端校验用户是否存在、是否启用、密码是否正确。
- 密码必须加密存储，建议使用 BCrypt。
- 登录成功返回 JWT Token 和当前用户信息。
- 登录失败返回明确错误信息，但不要泄露密码校验细节。

接口：

```http
POST /api/auth/login
```

请求字段：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| username | string | 是 | 用户名 |
| password | string | 是 | 密码 |

返回字段：

| 字段 | 说明 |
| --- | --- |
| token | JWT Token |
| user.id | 用户 ID |
| user.username | 用户名 |
| user.name | 姓名 |
| user.role | 角色 |

### 6.2 当前用户信息

功能说明：

- 根据 Token 获取当前登录用户。
- 返回用户基础信息和权限列表。

接口：

```http
GET /api/users/me
```

### 6.3 用户管理

功能说明：

- 支持用户分页列表查询。
- 支持按用户名、角色筛选。
- 支持创建用户、编辑用户、删除用户。
- 创建用户时用户名不可重复。
- 删除建议使用逻辑删除。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/users` | 用户列表 |
| POST | `/api/users` | 创建用户 |
| PUT | `/api/users/{id}` | 更新用户 |
| DELETE | `/api/users/{id}` | 删除用户 |

用户字段：

| 字段 | 说明 |
| --- | --- |
| id | 用户 ID |
| username | 用户名 |
| password | 创建时传入，后端保存 password_hash |
| name | 姓名 |
| role | 角色 |
| permissions | 权限编码列表 |
| createdAt | 创建时间 |

### 6.4 用户授权

功能说明：

- 查询用户当前权限。
- 更新用户权限。
- 权限数据来源于 `sys_permission`。
- 保存用户权限时维护 `sys_user_permission` 关联表。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/permissions` | 权限列表 |
| GET | `/api/users/{id}/permissions` | 用户权限详情 |
| PUT | `/api/users/{id}/permissions` | 更新用户权限 |

### 6.5 密码管理

功能说明：

- 当前登录用户可修改自己的密码。
- 管理员可重置指定用户密码。
- 修改密码时需要校验原密码。
- 新密码必须重新 BCrypt 加密后保存。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| PUT | `/api/users/me/password` | 修改当前用户密码 |
| PUT | `/api/users/{id}/password/reset` | 管理员重置密码 |

### 6.6 医药公司管理

功能说明：

- 支持公司分页列表。
- 支持按公司名称、城市筛选。
- 支持新增、详情、编辑、删除。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/companies` | 公司列表 |
| GET | `/api/companies/{id}` | 公司详情 |
| POST | `/api/companies` | 创建公司 |
| PUT | `/api/companies/{id}` | 更新公司 |
| DELETE | `/api/companies/{id}` | 删除公司 |

字段：

| 字段 | 说明 |
| --- | --- |
| companyName | 公司名称 |
| contact | 联系人 |
| phone | 联系电话 |
| city | 城市 |
| address | 地址 |

### 6.7 政策管理

功能说明：

- 支持政策分页列表。
- 支持按政策名称、适用公司筛选。
- 支持新增、详情、编辑、删除。
- 列表返回时需要带出 `companyName`。
- 到期日期应大于或等于生效日期。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/policies` | 政策列表 |
| GET | `/api/policies/{id}` | 政策详情 |
| POST | `/api/policies` | 创建政策 |
| PUT | `/api/policies/{id}` | 更新政策 |
| DELETE | `/api/policies/{id}` | 删除政策 |

字段：

| 字段 | 说明 |
| --- | --- |
| policyName | 政策名称 |
| companyId | 适用公司 ID |
| companyName | 适用公司名称，列表展示用 |
| effectiveDate | 生效日期 |
| expiryDate | 到期日期 |
| content | 政策内容 |

### 6.8 必备材料管理

功能说明：

- 支持材料分页列表。
- 支持按材料名称、材料类型筛选。
- 支持新增、详情、编辑、删除。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/materials` | 材料列表 |
| GET | `/api/materials/{id}` | 材料详情 |
| POST | `/api/materials` | 创建材料 |
| PUT | `/api/materials/{id}` | 更新材料 |
| DELETE | `/api/materials/{id}` | 删除材料 |

字段：

| 字段 | 说明 |
| --- | --- |
| materialName | 材料名称 |
| type | 材料类型 |
| description | 描述 |

### 6.9 城市信息管理

功能说明：

- 支持城市分页列表。
- 支持按城市名称、省份筛选。
- 支持新增、详情、编辑、删除。
- 城市代码不可重复。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/cities` | 城市列表 |
| GET | `/api/cities/{id}` | 城市详情 |
| POST | `/api/cities` | 创建城市 |
| PUT | `/api/cities/{id}` | 更新城市 |
| DELETE | `/api/cities/{id}` | 删除城市 |

字段：

| 字段 | 说明 |
| --- | --- |
| cityName | 城市名称 |
| province | 所属省份 |
| code | 城市代码 |

### 6.10 销售地点管理

功能说明：

- 支持销售地点分页列表。
- 支持按地点名称、城市筛选。
- 支持新增、详情、编辑、删除。
- 地图展示需要经纬度字段。
- 若前端只传地址，后端可预留地理编码扩展能力。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/locations` | 地点列表 |
| GET | `/api/locations/{id}` | 地点详情 |
| POST | `/api/locations` | 创建地点 |
| PUT | `/api/locations/{id}` | 更新地点 |
| DELETE | `/api/locations/{id}` | 删除地点 |

字段：

| 字段 | 说明 |
| --- | --- |
| locationName | 地点名称 |
| city | 城市 |
| address | 地址 |
| contact | 联系人 |
| phone | 联系电话 |
| longitude | 经度 |
| latitude | 纬度 |

### 6.11 药品信息管理

功能说明：

- 支持药品分页列表。
- 支持按药品名称、药品类型筛选。
- 支持新增、详情、编辑、删除。
- 价格必须为非负数。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/medicines` | 药品列表 |
| GET | `/api/medicines/{id}` | 药品详情 |
| POST | `/api/medicines` | 创建药品 |
| PUT | `/api/medicines/{id}` | 更新药品 |
| DELETE | `/api/medicines/{id}` | 删除药品 |

字段：

| 字段 | 说明 |
| --- | --- |
| medicineName | 药品名称 |
| type | 药品类型 |
| specification | 规格 |
| price | 价格 |
| manufacturer | 生产厂家 |

### 6.12 医生管理

功能说明：

- 支持医生分页列表。
- 支持按医生姓名、科室、职称筛选。
- 支持新增、详情、编辑、删除。
- 邮箱格式需要校验。

接口：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/doctors` | 医生列表 |
| GET | `/api/doctors/{id}` | 医生详情 |
| POST | `/api/doctors` | 创建医生 |
| PUT | `/api/doctors/{id}` | 更新医生 |
| DELETE | `/api/doctors/{id}` | 删除医生 |

字段：

| 字段 | 说明 |
| --- | --- |
| doctorName | 医生姓名 |
| department | 科室 |
| title | 职称 |
| phone | 联系电话 |
| email | 邮箱 |

### 6.13 仪表盘统计

功能说明：

- 返回各业务模块数量统计。
- 返回医生职称分布。
- 返回医生科室分布。
- 返回最新政策列表，按生效日期倒序取前 5 条。

接口：

```http
GET /api/dashboard
```

返回字段：

| 字段 | 说明 |
| --- | --- |
| companyCount | 公司数量 |
| materialCount | 材料数量 |
| cityCount | 城市数量 |
| locationCount | 销售地点数量 |
| medicineCount | 药品数量 |
| doctorCount | 医生数量 |
| doctorTitleDistribution | 医生职称分布 |
| departmentDistribution | 科室分布 |
| latestPolicies | 最新政策 |

## 7. MyBatis 开发要求

### 7.1 Mapper 接口

每个业务模块建立独立 Mapper：

```text
UserMapper
PermissionMapper
CompanyMapper
PolicyMapper
MaterialMapper
CityMapper
LocationMapper
MedicineMapper
DoctorMapper
DashboardMapper
```

### 7.2 XML SQL 文件

建议每个 Mapper 对应一个 XML：

```text
resources/mapper/UserMapper.xml
resources/mapper/CompanyMapper.xml
resources/mapper/PolicyMapper.xml
...
```

XML 中应包含：

- `BaseResultMap`
- 分页查询 SQL
- 条件筛选 SQL
- 详情查询 SQL
- 新增 SQL
- 更新 SQL
- 逻辑删除 SQL

### 7.3 分页要求

SQL Server 2022 分页建议使用：

```sql
ORDER BY id DESC
OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY
```

后端根据 `page` 和 `pageSize` 计算：

```text
offset = (page - 1) * pageSize
```

每个列表接口必须同时查询：

- 当前页数据 `list`
- 总条数 `total`

### 7.4 逻辑删除要求

所有业务查询默认带：

```sql
deleted = 0
```

删除接口不物理删除数据，统一执行：

```sql
UPDATE table_name
SET deleted = 1, updated_at = SYSDATETIME()
WHERE id = #{id}
```

用户权限关联表可根据业务选择物理删除，因为它只是关联关系。

## 8. 参数校验要求

后端必须对请求参数做校验，不能只依赖前端校验。

| 模块 | 校验 |
| --- | --- |
| 登录 | username、password 必填 |
| 用户 | username、name、role 必填，username 唯一 |
| 密码 | 新密码必填，修改密码需校验旧密码 |
| 公司 | companyName、contact、phone、city、address 必填 |
| 政策 | policyName、companyId、effectiveDate、expiryDate 必填，日期范围合法 |
| 材料 | materialName、type 必填 |
| 城市 | cityName、province、code 必填，code 唯一 |
| 地点 | locationName、city、address、contact、phone 必填 |
| 药品 | medicineName、type、specification、price、manufacturer 必填，price >= 0 |
| 医生 | doctorName、department、title、phone、email 必填，email 格式合法 |

## 9. 安全要求

- 密码必须使用 BCrypt 等安全算法加密存储。
- 登录失败不返回具体是用户名不存在还是密码错误。
- JWT 必须设置过期时间。
- 所有业务接口必须校验 Token。
- 管理员接口应校验角色或权限。
- 响应中不能返回 `password_hash`。
- 日志中不能打印明文密码、Token、数据库密码。
- 后端需要配置跨域，允许前端开发地址访问。

## 10. 配置文件要求

`application.yml` 示例：

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=HuiYiMedicalDB;encrypt=true;trustServerCertificate=true
    username: sa
    password: your_password
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.huiyi.medical.entity
  configuration:
    map-underscore-to-camel-case: true

jwt:
  secret: change-this-secret
  expiration: 86400000
```

## 11. Maven 依赖建议

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>
    <dependency>
        <groupId>com.microsoft.sqlserver</groupId>
        <artifactId>mssql-jdbc</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## 12. 验收标准

- 前端所有已有页面能切换为真实接口数据，不再依赖 `localStorage`。
- 登录后能正常访问受保护页面，未登录访问业务接口返回 401。
- 所有列表接口支持分页和条件查询。
- 所有增删改接口能正确落库。
- 删除接口为逻辑删除，删除后列表不再展示。
- 政策列表能展示公司名称。
- 仪表盘能展示真实统计数据和图表数据。
- Swagger 能查看全部接口。
- 数据库使用 SQL Server 2022，表结构与建表脚本一致。

