# 慧医数字医疗应用系统代码结构说明

本文档用于说明项目代码目录、核心文件职责和前后端调用关系，便于后续维护、答辩和二次开发。

## 1. 项目整体结构

```text
.
├─ src/                       前端 Vue 3 源码
├─ backend/                   后端 Spring Boot + MyBatis 源码
├─ docs/                      项目文档
├─ public/                    前端静态资源目录
├─ dist/                      前端构建产物
├─ package.json               前端依赖和启动脚本
├─ vite.config.js             Vite 配置和接口代理
└─ README.md                  项目启动说明
```

项目采用前后端分离结构：

- 前端负责页面展示、表单交互、路由跳转和调用接口。
- 后端负责接口处理、业务逻辑、权限认证、数据库读写。
- 数据库使用 SQL Server 2022，业务数据保存到 `HuiYiMedicalDB`。

## 2. 前端代码结构

前端目录为 `src/`，技术栈为 Vue 3、Vue Router、Element Plus、Axios、ECharts。

```text
src/
├─ api/                       接口封装
├─ assets/                    静态资源
├─ components/                公共组件
├─ router/                    前端路由
├─ utils/                     工具函数
├─ views/                     页面组件
├─ App.vue                    根组件
├─ main.js                    前端入口文件
└─ style.css                  全局样式
```

### 2.1 `src/main.js`

前端应用入口文件，主要功能：

- 创建 Vue 应用实例。
- 注册 Element Plus。
- 注册路由。
- 挂载到页面 DOM。

### 2.2 `src/App.vue`

Vue 根组件，承载整个应用的路由出口。

### 2.3 `src/style.css`

全局样式文件，用于定义基础页面样式。

### 2.4 `src/utils/request.js`

Axios 请求封装文件，所有前端接口都会经过这里。

主要功能：

- 设置统一请求前缀：`/api`
- 从 `localStorage` 读取 `token`
- 自动添加 `Authorization: Bearer xxx`
- 统一处理后端响应结构
- 统一处理接口错误提示

前端业务数据不保存到本地缓存，只保存登录态：

```text
token
user
```

### 2.5 `src/router/index.js`

前端路由配置文件。

主要功能：

- 定义登录页、首页和各业务页面路由。
- 使用路由守卫检查是否已登录。
- 未登录访问业务页面时跳转到 `/login`。

主要路由模块：

- `/` 仪表盘
- `/user` 用户管理
- `/user/auth` 用户授权
- `/password/change` 修改密码
- `/password/reset` 重置密码
- `/company` 医药公司
- `/company/policy` 政策管理
- `/material` 必备材料
- `/city` 城市信息
- `/location` 销售地点
- `/location/map` 销售地点地图
- `/medicine` 药品信息
- `/doctor` 医师管理

## 3. 前端接口目录 `src/api`

`src/api/` 按业务模块封装接口，每个文件对应一个后端资源。

| 文件 | 功能 |
| --- | --- |
| `user.js` | 登录、用户列表、用户新增/编辑/删除、权限、密码修改/重置 |
| `dashboard.js` | 仪表盘统计数据 |
| `company.js` | 医药公司增删改查 |
| `policy.js` | 政策增删改查 |
| `material.js` | 必备材料增删改查 |
| `city.js` | 城市增删改查 |
| `location.js` | 销售地点增删改查 |
| `medicine.js` | 药品增删改查 |
| `doctor.js` | 医师增删改查 |

接口调用示例：

```js
export function getCompanyList(params) {
  return request({
    url: '/companies',
    method: 'get',
    params
  })
}
```

前端实际请求会经过 Vite 代理：

```text
/api/companies -> http://localhost:8080/api/companies
```

## 4. 前端页面目录 `src/views`

`src/views/` 存放所有页面组件，按业务模块划分。

### 4.1 登录和布局

| 文件 | 功能 |
| --- | --- |
| `Login.vue` | 登录页面，调用 `/api/auth/login` 获取 token |
| `Home.vue` | 后台首页布局，包含侧边栏、顶部栏、退出登录 |
| `dashboard/Dashboard.vue` | 仪表盘页面，展示统计卡片、图表、最新政策 |

### 4.2 用户和权限

| 文件 | 功能 |
| --- | --- |
| `user/UserList.vue` | 用户列表、新增、编辑、删除 |
| `user/Auth.vue` | 用户授权，读取权限列表并保存用户权限 |
| `password/Change.vue` | 当前用户修改密码 |
| `password/Reset.vue` | 管理员重置用户密码 |

### 4.3 医药公司和政策

| 文件 | 功能 |
| --- | --- |
| `company/CompanyList.vue` | 公司列表、搜索、删除 |
| `company/AddCompany.vue` | 新增公司 |
| `company/EditCompany.vue` | 编辑公司 |
| `company/PolicyList.vue` | 政策列表、搜索、删除 |
| `company/AddPolicy.vue` | 新增政策 |
| `company/EditPolicy.vue` | 编辑政策 |

### 4.4 基础资料模块

| 文件 | 功能 |
| --- | --- |
| `material/MaterialList.vue` | 材料列表、搜索、删除 |
| `material/AddMaterial.vue` | 新增材料 |
| `material/EditMaterial.vue` | 编辑材料 |
| `city/CityList.vue` | 城市列表、搜索、删除 |
| `city/AddCity.vue` | 新增城市 |
| `city/EditCity.vue` | 编辑城市 |

### 4.5 销售地点、药品、医师

| 文件 | 功能 |
| --- | --- |
| `location/LocationList.vue` | 销售地点列表、搜索、删除 |
| `location/AddLocation.vue` | 新增销售地点 |
| `location/EditLocation.vue` | 编辑销售地点 |
| `location/LocationMap.vue` | 销售地点地图展示页 |
| `medicine/MedicineList.vue` | 药品列表、搜索、删除 |
| `medicine/AddMedicine.vue` | 新增药品 |
| `medicine/EditMedicine.vue` | 编辑药品 |
| `doctor/DoctorList.vue` | 医师列表、搜索、删除 |
| `doctor/AddDoctor.vue` | 新增医师 |
| `doctor/EditDoctor.vue` | 编辑医师 |

## 5. 后端代码结构

后端目录为 `backend/`，技术栈为 Spring Boot、Spring Security、JWT、MyBatis、SQL Server。

```text
backend/
├─ pom.xml                    Maven 依赖配置
├─ README.md                  后端说明
├─ sql/init_data.sql          初始化数据脚本
└─ src/main/
   ├─ java/com/huiyi/medical/
   │  ├─ common/              通用响应、异常、分页
   │  ├─ config/              Spring 配置
   │  ├─ controller/          REST 接口控制器
   │  ├─ dto/                 请求参数对象
   │  ├─ entity/              数据库实体对象
   │  ├─ mapper/              MyBatis Mapper 接口
   │  ├─ security/            JWT 认证相关代码
   │  ├─ service/             业务逻辑层
   │  ├─ vo/                  接口返回视图对象
   │  └─ MedicalApplication.java
   └─ resources/
      ├─ application.yml      后端配置文件
      └─ mapper/              MyBatis XML SQL 文件
```

## 6. 后端核心文件说明

### 6.1 启动类

| 文件 | 功能 |
| --- | --- |
| `MedicalApplication.java` | Spring Boot 后端启动入口 |

### 6.2 通用模块 `common`

| 文件 | 功能 |
| --- | --- |
| `Result.java` | 统一接口响应结构，包含 `code`、`message`、`data` |
| `PageResult.java` | 分页响应结构，包含 `list`、`total` |
| `BusinessException.java` | 业务异常类 |
| `GlobalExceptionHandler.java` | 全局异常处理，将异常转换为统一响应 |

### 6.3 配置模块 `config`

| 文件 | 功能 |
| --- | --- |
| `SecurityConfig.java` | Spring Security 配置，设置登录放行、JWT 过滤器、密码编码器 |
| `WebConfig.java` | Web 相关配置，例如跨域配置 |

### 6.4 认证模块 `security`

| 文件 | 功能 |
| --- | --- |
| `JwtTokenProvider.java` | JWT 生成、解析、校验 |
| `JwtAuthenticationFilter.java` | 请求过滤器，从请求头读取 token 并设置登录状态 |

### 6.5 控制器模块 `controller`

控制器负责接收前端请求，并调用 Service 处理业务。

| 文件 | 接口功能 |
| --- | --- |
| `AuthController.java` | 登录接口 `/api/auth/login` |
| `UserController.java` | 用户、权限、密码接口 |
| `DashboardController.java` | 仪表盘统计接口 |
| `CompanyController.java` | 医药公司接口 |
| `PolicyController.java` | 政策接口 |
| `MaterialController.java` | 必备材料接口 |
| `CityController.java` | 城市接口 |
| `LocationController.java` | 销售地点接口 |
| `MedicineController.java` | 药品接口 |
| `DoctorController.java` | 医师接口 |

### 6.6 DTO 模块 `dto`

DTO 用于接收前端请求参数。

| 类型 | 文件 |
| --- | --- |
| 登录请求 | `LoginRequest.java` |
| 用户请求 | `UserRequest.java` |
| 密码请求 | `PasswordChangeRequest.java`、`PasswordResetRequest.java` |
| 权限请求 | `PermissionUpdateRequest.java` |
| 分页基础参数 | `PageQuery.java` |
| 列表查询参数 | `CompanyQuery.java`、`PolicyQuery.java`、`MaterialQuery.java`、`CityQuery.java`、`LocationQuery.java`、`MedicineQuery.java`、`DoctorQuery.java`、`UserQuery.java` |

### 6.7 实体模块 `entity`

Entity 对应数据库表字段。

| 文件 | 对应表 |
| --- | --- |
| `BaseEntity.java` | 通用字段：id、createdAt、updatedAt、deleted |
| `User.java` | `sys_user` |
| `Permission.java` | `sys_permission` |
| `Company.java` | `company` |
| `Policy.java` | `policy` |
| `Material.java` | `material` |
| `City.java` | `city` |
| `Location.java` | `location` |
| `Medicine.java` | `medicine` |
| `Doctor.java` | `doctor` |

### 6.8 Mapper 模块 `mapper`

Mapper 接口定义数据库访问方法，具体 SQL 在 `resources/mapper/*.xml` 中。

| Java 接口 | XML 文件 | 功能 |
| --- | --- | --- |
| `UserMapper.java` | `UserMapper.xml` | 用户、权限、密码相关 SQL |
| `CompanyMapper.java` | `CompanyMapper.xml` | 公司 SQL |
| `PolicyMapper.java` | `PolicyMapper.xml` | 政策 SQL，包含公司关联查询 |
| `MaterialMapper.java` | `MaterialMapper.xml` | 材料 SQL |
| `CityMapper.java` | `CityMapper.xml` | 城市 SQL |
| `LocationMapper.java` | `LocationMapper.xml` | 销售地点 SQL |
| `MedicineMapper.java` | `MedicineMapper.xml` | 药品 SQL |
| `DoctorMapper.java` | `DoctorMapper.xml` | 医师 SQL，包含分布统计 |

### 6.9 Service 模块 `service`

Service 负责业务逻辑处理。

| 文件 | 功能 |
| --- | --- |
| `UserService.java` | 登录、用户 CRUD、权限更新、密码修改/重置 |
| `DashboardService.java` | 汇总统计公司、材料、城市、地点、药品、医师、政策数据 |
| `CompanyService.java` | 公司业务逻辑 |
| `PolicyService.java` | 政策业务逻辑 |
| `MaterialService.java` | 材料业务逻辑 |
| `CityService.java` | 城市业务逻辑 |
| `LocationService.java` | 销售地点业务逻辑 |
| `MedicineService.java` | 药品业务逻辑 |
| `DoctorService.java` | 医师业务逻辑 |

### 6.10 VO 模块 `vo`

VO 用于封装接口返回给前端的数据。

| 文件 | 功能 |
| --- | --- |
| `LoginVO.java` | 登录返回 token 和用户信息 |
| `UserVO.java` | 用户列表和当前用户返回结构 |
| `DashboardVO.java` | 仪表盘统计返回结构 |
| `DistributionVO.java` | 图表分布数据结构，例如职称分布、科室分布 |

## 7. 后端配置文件

### `backend/src/main/resources/application.yml`

主要配置：

- 后端端口：`8080`
- SQL Server 数据库连接
- MyBatis XML 路径
- 实体别名包路径
- JWT 密钥和过期时间
- Swagger UI 路径

关键配置：

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=HuiYiMedicalDB;encrypt=true;trustServerCertificate=true
```

## 8. 数据库脚本

### `backend/sql/init_data.sql`

初始化演示数据脚本。

主要写入：

- 默认用户：`admin`、`operator`、`auditor`
- 默认密码：`admin123`
- 系统权限
- 用户权限关系
- 医药公司
- 政策
- 必备材料
- 城市
- 销售地点
- 药品
- 医师

执行顺序：

1. 先创建数据库 `HuiYiMedicalDB`。
2. 先执行建表 SQL。
3. 再执行 `backend/sql/init_data.sql`。

## 9. 前后端调用链

以“公司列表”为例：

```text
CompanyList.vue
  -> src/api/company.js
    -> src/utils/request.js
      -> /api/companies
        -> CompanyController
          -> CompanyService
            -> CompanyMapper
              -> CompanyMapper.xml
                -> SQL Server company 表
```

新增、编辑、删除流程类似，只是请求方法不同：

| 操作 | HTTP 方法 | 示例接口 |
| --- | --- | --- |
| 查询列表 | GET | `/api/companies` |
| 查询详情 | GET | `/api/companies/{id}` |
| 新增 | POST | `/api/companies` |
| 编辑 | PUT | `/api/companies/{id}` |
| 删除 | DELETE | `/api/companies/{id}` |

## 10. 新增功能时的开发位置

如果后续要新增一个业务模块，通常需要修改这些位置：

### 前端

1. 在 `src/api/` 新增接口文件。
2. 在 `src/views/` 新增列表、新增、编辑页面。
3. 在 `src/router/index.js` 配置路由。
4. 在 `Home.vue` 中增加菜单入口。

### 后端

1. 在 `entity/` 新增实体类。
2. 在 `dto/` 新增查询或请求参数类。
3. 在 `mapper/` 新增 Mapper 接口。
4. 在 `resources/mapper/` 新增 MyBatis XML。
5. 在 `service/` 新增业务逻辑类。
6. 在 `controller/` 新增接口控制器。
7. 在数据库中新增表结构。
8. 如需演示数据，更新 `backend/sql/init_data.sql`。

## 11. 当前数据保存方式

当前系统业务数据统一保存到 SQL Server 数据库。

前端不会保存业务数据到本地缓存，浏览器只保存登录态：

```text
localStorage.token
localStorage.user
```

后端没有使用 Redis 或本地缓存保存业务数据，所有业务查询和写入都通过 MyBatis 操作数据库。
