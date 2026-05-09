# 慧医医疗管理系统

本项目包含 Vue 3 前端和 Spring Boot + MyBatis 后端，数据库使用 SQL Server 2022。前端通过 `/api` 代理访问后端，业务数据统一保存到 SQL Server 数据库。

## 目录结构

```text
.
├─ src/                       前端源码
├─ backend/                   后端源码
│  ├─ src/main/java/          Spring Boot 代码
│  ├─ src/main/resources/     配置和 MyBatis XML
│  └─ sql/init_data.sql       初始化演示数据
├─ docs/                      功能、接口、数据库、后端需求文档
├─ package.json               前端依赖和脚本
└─ vite.config.js             前端开发服务和代理配置
```

## 环境要求

- Node.js 18+，建议 20+
- JDK 17+
- Maven 3.8+
- SQL Server 2022
- 数据库客户端：SQL Server Management Studio 或 Azure Data Studio

## 1. 初始化数据库

先在 SQL Server 中创建数据库：

-- 执行 backend/sql/SQLQuery建库.sql

创建完成后，进入 `HuiYiMedicalDB` 执行建表 SQL。建表完成后，再执行初始化数据：

```sql
USE HuiYiMedicalDB;
GO

-- 执行 backend/sql/init_data.sql
```

初始化脚本会写入用户、权限、公司、政策、材料、城市、销售地点、药品、医师等演示数据。

默认账号：

```text
用户名：admin
密码：admin123
```

还包含演示账号：

```text
operator / admin123
auditor  / admin123
```

## 2. 配置后端数据库连接

打开：

```text
backend/src/main/resources/application.yml
```

确认数据库连接信息和你的 SQL Server 一致：

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=HuiYiMedicalDB;encrypt=true;trustServerCertificate=true
    username: sa
    password: ******  //修改为你的 SQL Server 密码
```

如果你的 SQL Server 用户名、密码或端口不同，需要先改这里。

## 3. 启动后端

在项目根目录执行：

```powershell
cd backend
mvn spring-boot:run
```

后端默认端口：

```text
http://localhost:8080
```

接口文档地址：

```text
http://localhost:8080/swagger-ui.html
```

## 4. 启动前端

回到项目根目录，安装依赖：

```powershell
npm install
```

启动开发服务：

```powershell
npm run dev
```

前端默认地址：

```text
http://localhost:5173
```

前端代理配置在 `vite.config.js` 中，已将 `/api` 转发到后端：

```text
/api -> http://localhost:8080
```

所以需要先启动后端，再启动前端。

## 5. 启动顺序

推荐按下面顺序启动：

1. 启动 SQL Server 2022。
2. 创建数据库 `HuiYiMedicalDB`。
3. 执行建表 SQL。
4. 执行 `backend/sql/init_data.sql`。
5. 修改并确认 `backend/src/main/resources/application.yml` 数据库账号密码。
6. 在 `backend` 目录启动后端：`mvn spring-boot:run`。
7. 在项目根目录启动前端：`npm run dev`。
8. 浏览器访问 `http://localhost:5173`，使用 `admin / admin123` 登录。

## 6. 功能说明

当前前端页面已经改为调用后端接口，不再使用前端本地业务假数据。业务数据由后端写入 SQL Server。

已接入接口的功能包括：

- 登录
- 仪表盘统计
- 用户管理
- 用户授权
- 修改密码、重置密码
- 医药公司管理
- 政策管理
- 必备材料管理
- 城市信息管理
- 销售地点管理和地图展示
- 药品信息管理
- 医师管理

前端只在 `localStorage` 中保存登录态：

```text
token
user
```

公司、政策、材料、城市、地点、药品、医生、用户、权限等业务数据不会保存到前端缓存。

## 7. 构建前端

生产构建：

```powershell
npm run build
```

如果本机 npm 路径异常，也可以直接执行：

```powershell
.\node_modules\.bin\vite.cmd build
```

构建产物输出到：

```text
dist/
```

## 8. 常见问题

### 登录失败

检查：

- 后端是否已启动在 `8080` 端口。
- SQL Server 是否已启动。
- `application.yml` 中数据库账号密码是否正确。
- 是否已执行初始化数据脚本。
- 默认账号是否为 `admin / admin123`。

### 前端页面没有数据

检查：

- 前端是否通过 `http://localhost:5173` 访问。
- 后端接口是否能访问，例如 `http://localhost:8080/swagger-ui.html`。
- `backend/sql/init_data.sql` 是否执行成功。
- 浏览器控制台 Network 中 `/api/...` 请求是否返回 `200`。

### 后端连接数据库失败

检查：

- SQL Server TCP/IP 是否启用。
- SQL Server 端口是否为 `1433`。
- 数据库名是否为 `HuiYiMedicalDB`。
- 用户名密码是否和 `application.yml` 一致。

### npm run build 失败，提示找不到 npm-cli.js

这是本机 Node/npm 安装路径问题，不是项目代码问题。可以重新安装 Node.js，或临时使用：

```powershell
.\node_modules\.bin\vite.cmd build
```
