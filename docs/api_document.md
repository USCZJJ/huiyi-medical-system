# 慧医数字医疗应用系统接口文档

## 1. 通用约定

### 1.1 基础路径

前端 Axios 实例配置的基础路径为：

```text
/api
```

接口文档中的路径均以 `/api` 开头。

### 1.2 鉴权

除登录接口外，业务接口建议均要求登录态：

```http
Authorization: Bearer <token>
```

### 1.3 通用响应结构

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

分页列表建议统一返回：

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

### 1.4 常用错误码

| code | 含义 |
| --- | --- |
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未登录或 Token 失效 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务端错误 |

## 2. 认证与当前用户

### 2.1 用户登录

```http
POST /api/auth/login
```

请求体：

```json
{
  "username": "admin",
  "password": "admin123"
}
```

响应：

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "jwt-token",
    "user": {
      "id": 1,
      "username": "admin",
      "name": "管理员",
      "role": "admin"
    }
  }
}
```

### 2.2 获取当前用户

```http
GET /api/users/me
```

响应：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "admin",
    "name": "管理员",
    "role": "admin",
    "permissions": ["user:list", "company:list"]
  }
}
```

## 3. 用户接口

### 3.1 用户列表

```http
GET /api/users
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| username | string | 否 | 用户名 |
| role | string | 否 | 角色 |

列表项字段：`id`、`username`、`name`、`role`、`permissions`、`createdAt`

### 3.2 创建用户

```http
POST /api/users
```

请求体：

```json
{
  "username": "user1",
  "password": "123456",
  "name": "用户1",
  "role": "user",
  "permissions": []
}
```

### 3.3 更新用户

```http
PUT /api/users/{id}
```

请求体：

```json
{
  "username": "user1",
  "name": "用户1",
  "role": "user",
  "permissions": []
}
```

### 3.4 删除用户

```http
DELETE /api/users/{id}
```

## 4. 医药公司接口

### 4.1 公司列表

```http
GET /api/companies
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| companyName | string | 否 | 公司名称 |
| city | string | 否 | 城市 |

列表项字段：`id`、`companyName`、`contact`、`phone`、`city`、`address`

### 4.2 公司详情

```http
GET /api/companies/{id}
```

### 4.3 创建公司

```http
POST /api/companies
```

请求体：

```json
{
  "companyName": "北京医药有限公司",
  "contact": "张三",
  "phone": "13800138001",
  "city": "北京",
  "address": "北京市朝阳区建国路88号"
}
```

### 4.4 更新公司

```http
PUT /api/companies/{id}
```

请求体同创建公司。

### 4.5 删除公司

```http
DELETE /api/companies/{id}
```

## 5. 政策接口

### 5.1 政策列表

```http
GET /api/policies
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| policyName | string | 否 | 政策名称 |
| companyId | number | 否 | 适用公司 ID |

列表项字段：`id`、`policyName`、`companyId`、`companyName`、`effectiveDate`、`expiryDate`、`content`

### 5.2 政策详情

```http
GET /api/policies/{id}
```

### 5.3 创建政策

```http
POST /api/policies
```

请求体：

```json
{
  "policyName": "药品优惠政策",
  "companyId": 1,
  "effectiveDate": "2026-01-01",
  "expiryDate": "2026-12-31",
  "content": "政策内容"
}
```

### 5.4 更新政策

```http
PUT /api/policies/{id}
```

请求体同创建政策。

### 5.5 删除政策

```http
DELETE /api/policies/{id}
```

## 6. 必备材料接口

### 6.1 材料列表

```http
GET /api/materials
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| materialName | string | 否 | 材料名称 |
| type | string | 否 | 材料类型 |

列表项字段：`id`、`materialName`、`type`、`description`

### 6.2 材料详情

```http
GET /api/materials/{id}
```

### 6.3 创建材料

```http
POST /api/materials
```

请求体：

```json
{
  "materialName": "营业执照",
  "type": "business_license",
  "description": "企业合法经营证明"
}
```

### 6.4 更新材料

```http
PUT /api/materials/{id}
```

请求体同创建材料。

### 6.5 删除材料

```http
DELETE /api/materials/{id}
```

## 7. 城市接口

### 7.1 城市列表

```http
GET /api/cities
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| cityName | string | 否 | 城市名称 |
| province | string | 否 | 省份 |

列表项字段：`id`、`cityName`、`province`、`code`

### 7.2 城市详情

```http
GET /api/cities/{id}
```

### 7.3 创建城市

```http
POST /api/cities
```

请求体：

```json
{
  "cityName": "北京",
  "province": "北京",
  "code": "110000"
}
```

### 7.4 更新城市

```http
PUT /api/cities/{id}
```

请求体同创建城市。

### 7.5 删除城市

```http
DELETE /api/cities/{id}
```

## 8. 销售地点接口

### 8.1 地点列表

```http
GET /api/locations
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| locationName | string | 否 | 地点名称 |
| city | string | 否 | 城市 |

列表项字段：`id`、`locationName`、`city`、`address`、`contact`、`phone`、`longitude`、`latitude`

### 8.2 地点详情

```http
GET /api/locations/{id}
```

### 8.3 创建地点

```http
POST /api/locations
```

请求体：

```json
{
  "locationName": "北京朝阳区药店",
  "city": "北京",
  "address": "北京市朝阳区某街道",
  "contact": "张三",
  "phone": "13800138000",
  "longitude": 116.4074,
  "latitude": 39.9042
}
```

### 8.4 更新地点

```http
PUT /api/locations/{id}
```

请求体同创建地点。

### 8.5 删除地点

```http
DELETE /api/locations/{id}
```

## 9. 药品接口

### 9.1 药品列表

```http
GET /api/medicines
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| medicineName | string | 否 | 药品名称 |
| type | string | 否 | 药品类型 |

列表项字段：`id`、`medicineName`、`type`、`specification`、`price`、`manufacturer`

### 9.2 药品详情

```http
GET /api/medicines/{id}
```

### 9.3 创建药品

```http
POST /api/medicines
```

请求体：

```json
{
  "medicineName": "阿莫西林胶囊",
  "type": "prescription",
  "specification": "0.25g*24粒",
  "price": 12.5,
  "manufacturer": "华北制药"
}
```

### 9.4 更新药品

```http
PUT /api/medicines/{id}
```

请求体同创建药品。

### 9.5 删除药品

```http
DELETE /api/medicines/{id}
```

## 10. 医生接口

### 10.1 医生列表

```http
GET /api/doctors
```

查询参数：

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| page | number | 否 | 页码 |
| pageSize | number | 否 | 每页条数 |
| doctorName | string | 否 | 医生姓名 |
| department | string | 否 | 科室 |
| title | string | 否 | 职称 |

列表项字段：`id`、`doctorName`、`department`、`title`、`phone`、`email`

### 10.2 医生详情

```http
GET /api/doctors/{id}
```

### 10.3 创建医生

```http
POST /api/doctors
```

请求体：

```json
{
  "doctorName": "张医生",
  "department": "内科",
  "title": "主任医师",
  "phone": "13800138001",
  "email": "zhang@example.com"
}
```

### 10.4 更新医生

```http
PUT /api/doctors/{id}
```

请求体同创建医生。

### 10.5 删除医生

```http
DELETE /api/doctors/{id}
```

## 11. 密码与权限接口

当前前端有页面入口，但 `src/api` 尚未封装以下接口。建议后端补齐。

### 11.1 修改密码

```http
PUT /api/users/me/password
```

请求体：

```json
{
  "oldPassword": "old123",
  "newPassword": "new123456"
}
```

### 11.2 管理员重置密码

```http
PUT /api/users/{id}/password/reset
```

请求体：

```json
{
  "newPassword": "123456"
}
```

### 11.3 更新用户权限

```http
PUT /api/users/{id}/permissions
```

请求体：

```json
{
  "permissions": ["company:list", "company:create"]
}
```

## 12. 仪表盘接口

当前仪表盘从 `localStorage` 聚合数据。建议后端提供聚合接口，减少前端多次请求和本地统计误差。

```http
GET /api/dashboard
```

响应：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "companyCount": 4,
    "materialCount": 8,
    "cityCount": 4,
    "locationCount": 10,
    "medicineCount": 20,
    "doctorCount": 30,
    "doctorTitleDistribution": [
      { "title": "主任医师", "count": 5 }
    ],
    "departmentDistribution": [
      { "department": "内科", "count": 8 }
    ],
    "latestPolicies": [
      {
        "id": 1,
        "policyName": "药品优惠政策",
        "companyName": "北京医药有限公司",
        "effectiveDate": "2026-01-01",
        "expiryDate": "2026-12-31"
      }
    ]
  }
}
```

