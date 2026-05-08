USE HuiYiMedicalDB;
GO

/* =========================================================
   慧医数字医疗应用系统初始化数据
   默认账号密码：admin / admin123
   密码格式：{noop}admin123，可被 Spring Security DelegatingPasswordEncoder 识别
   ========================================================= */

/* 1. 用户数据 */
IF NOT EXISTS (SELECT 1 FROM dbo.sys_user WHERE username = N'admin')
BEGIN
    INSERT INTO dbo.sys_user(username, password_hash, name, role, status)
    VALUES (N'admin', N'{noop}admin123', N'管理员', N'admin', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.sys_user WHERE username = N'operator')
BEGIN
    INSERT INTO dbo.sys_user(username, password_hash, name, role, status)
    VALUES (N'operator', N'{noop}admin123', N'运营人员', N'user', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.sys_user WHERE username = N'auditor')
BEGIN
    INSERT INTO dbo.sys_user(username, password_hash, name, role, status)
    VALUES (N'auditor', N'{noop}admin123', N'审核人员', N'user', 1);
END
GO

/* 2. 权限数据 */
IF NOT EXISTS (SELECT 1 FROM dbo.sys_permission WHERE permission_code = N'user:list')
BEGIN
    INSERT INTO dbo.sys_permission(permission_code, permission_name, module) VALUES
    (N'user:list', N'用户列表', N'用户管理'),
    (N'user:create', N'创建用户', N'用户管理'),
    (N'user:update', N'编辑用户', N'用户管理'),
    (N'user:delete', N'删除用户', N'用户管理'),
    (N'user:auth', N'用户授权', N'用户管理'),
    (N'password:change', N'修改密码', N'密码管理'),
    (N'password:reset', N'重置密码', N'密码管理'),
    (N'company:list', N'公司列表', N'医药公司管理'),
    (N'company:create', N'创建公司', N'医药公司管理'),
    (N'company:update', N'编辑公司', N'医药公司管理'),
    (N'company:delete', N'删除公司', N'医药公司管理'),
    (N'policy:list', N'政策列表', N'政策管理'),
    (N'policy:create', N'创建政策', N'政策管理'),
    (N'policy:update', N'编辑政策', N'政策管理'),
    (N'policy:delete', N'删除政策', N'政策管理'),
    (N'material:list', N'材料列表', N'必备材料管理'),
    (N'material:create', N'创建材料', N'必备材料管理'),
    (N'material:update', N'编辑材料', N'必备材料管理'),
    (N'material:delete', N'删除材料', N'必备材料管理'),
    (N'city:list', N'城市列表', N'城市管理'),
    (N'city:create', N'创建城市', N'城市管理'),
    (N'city:update', N'编辑城市', N'城市管理'),
    (N'city:delete', N'删除城市', N'城市管理'),
    (N'location:list', N'地点列表', N'销售地点管理'),
    (N'location:create', N'创建地点', N'销售地点管理'),
    (N'location:update', N'编辑地点', N'销售地点管理'),
    (N'location:delete', N'删除地点', N'销售地点管理'),
    (N'medicine:list', N'药品列表', N'药品管理'),
    (N'medicine:create', N'创建药品', N'药品管理'),
    (N'medicine:update', N'编辑药品', N'药品管理'),
    (N'medicine:delete', N'删除药品', N'药品管理'),
    (N'doctor:list', N'医生列表', N'医生管理'),
    (N'doctor:create', N'创建医生', N'医生管理'),
    (N'doctor:update', N'编辑医生', N'医生管理'),
    (N'doctor:delete', N'删除医生', N'医生管理'),
    (N'dashboard:view', N'查看仪表盘', N'仪表盘');
END
GO

/* 3. 给管理员分配全部权限 */
DECLARE @admin_id BIGINT = (SELECT id FROM dbo.sys_user WHERE username = N'admin');

INSERT INTO dbo.sys_user_permission(user_id, permission_id)
SELECT @admin_id, p.id
FROM dbo.sys_permission p
WHERE @admin_id IS NOT NULL
AND NOT EXISTS (
    SELECT 1
    FROM dbo.sys_user_permission up
    WHERE up.user_id = @admin_id AND up.permission_id = p.id
);
GO

/* 4. 给普通用户分配部分权限 */
DECLARE @operator_id BIGINT = (SELECT id FROM dbo.sys_user WHERE username = N'operator');

INSERT INTO dbo.sys_user_permission(user_id, permission_id)
SELECT @operator_id, p.id
FROM dbo.sys_permission p
WHERE @operator_id IS NOT NULL
AND (
    p.permission_code = N'dashboard:view'
    OR p.permission_code = N'company:list'
    OR p.permission_code = N'company:create'
    OR p.permission_code = N'company:update'
    OR p.permission_code = N'policy:list'
    OR p.permission_code = N'policy:create'
    OR p.permission_code = N'policy:update'
    OR p.permission_code = N'material:list'
    OR p.permission_code = N'city:list'
    OR p.permission_code = N'location:list'
    OR p.permission_code = N'location:create'
    OR p.permission_code = N'location:update'
    OR p.permission_code = N'medicine:list'
    OR p.permission_code = N'doctor:list'
)
AND NOT EXISTS (
    SELECT 1
    FROM dbo.sys_user_permission up
    WHERE up.user_id = @operator_id AND up.permission_id = p.id
);
GO

DECLARE @auditor_id BIGINT = (SELECT id FROM dbo.sys_user WHERE username = N'auditor');

INSERT INTO dbo.sys_user_permission(user_id, permission_id)
SELECT @auditor_id, p.id
FROM dbo.sys_permission p
WHERE @auditor_id IS NOT NULL
AND (
    p.permission_code = N'dashboard:view'
    OR p.permission_code = N'company:list'
    OR p.permission_code = N'policy:list'
    OR p.permission_code = N'material:list'
    OR p.permission_code = N'city:list'
    OR p.permission_code = N'location:list'
    OR p.permission_code = N'medicine:list'
    OR p.permission_code = N'doctor:list'
)
AND NOT EXISTS (
    SELECT 1
    FROM dbo.sys_user_permission up
    WHERE up.user_id = @auditor_id AND up.permission_id = p.id
);
GO

/* 5. 城市数据 */
IF NOT EXISTS (SELECT 1 FROM dbo.city WHERE code = N'110000')
BEGIN
    INSERT INTO dbo.city(city_name, province, code) VALUES
    (N'北京', N'北京', N'110000'),
    (N'上海', N'上海', N'310000'),
    (N'广州', N'广东', N'440100'),
    (N'深圳', N'广东', N'440300'),
    (N'杭州', N'浙江', N'330100');
END
GO

/* 6. 医药公司数据 */
IF NOT EXISTS (SELECT 1 FROM dbo.company WHERE company_name = N'北京慧康医药有限公司')
BEGIN
    INSERT INTO dbo.company(company_name, contact, phone, city, address) VALUES
    (N'北京慧康医药有限公司', N'张三', N'13800138001', N'北京', N'北京市朝阳区建国路88号'),
    (N'上海仁济医药股份有限公司', N'李四', N'13900139002', N'上海', N'上海市浦东新区张江高科技园区'),
    (N'广州康民医药集团有限公司', N'王五', N'13700137003', N'广州', N'广州市天河区珠江新城'),
    (N'深圳安和医药控股有限公司', N'赵六', N'13600136004', N'深圳', N'深圳市南山区科技园'),
    (N'杭州健宁生物医药有限公司', N'钱七', N'13500135005', N'杭州', N'杭州市滨江区江南大道');
END
GO

/* 7. 政策数据，关联 company 表，用于政策列表多表查询 */
DECLARE @company_bj BIGINT = (SELECT id FROM dbo.company WHERE company_name = N'北京慧康医药有限公司');
DECLARE @company_sh BIGINT = (SELECT id FROM dbo.company WHERE company_name = N'上海仁济医药股份有限公司');
DECLARE @company_gz BIGINT = (SELECT id FROM dbo.company WHERE company_name = N'广州康民医药集团有限公司');
DECLARE @company_sz BIGINT = (SELECT id FROM dbo.company WHERE company_name = N'深圳安和医药控股有限公司');

IF NOT EXISTS (SELECT 1 FROM dbo.policy WHERE policy_name = N'北京慧康慢病药品优惠政策')
BEGIN
    INSERT INTO dbo.policy(policy_name, company_id, effective_date, expiry_date, content) VALUES
    (N'北京慧康慢病药品优惠政策', @company_bj, '2026-01-01', '2026-12-31', N'面向慢病处方药提供阶梯优惠。'),
    (N'上海仁济冷链药品配送政策', @company_sh, '2026-02-01', '2026-11-30', N'规范冷链药品配送、签收和追溯要求。'),
    (N'广州康民基层医院返利政策', @company_gz, '2026-03-01', '2026-09-30', N'基层医院采购达标后按季度返利。'),
    (N'深圳安和互联网医院供药政策', @company_sz, '2026-04-01', '2027-03-31', N'互联网医院处方流转药品供给规则。');
END
GO

/* 8. 必备材料数据 */
IF NOT EXISTS (SELECT 1 FROM dbo.material WHERE material_name = N'营业执照')
BEGIN
    INSERT INTO dbo.material(material_name, type, description) VALUES
    (N'营业执照', N'business_license', N'企业合法经营资质证明。'),
    (N'药品经营许可证', N'drug_license', N'医药经营主体必备许可证。'),
    (N'法人身份证明', N'identity', N'企业法人身份信息材料。'),
    (N'质量管理制度文件', N'quality_system', N'药品质量管理制度说明。'),
    (N'冷链运输资质', N'cold_chain', N'涉及冷链药品配送时提交。');
END
GO

/* 9. 销售地点数据，包含经纬度用于地图展示 */
IF NOT EXISTS (SELECT 1 FROM dbo.[location] WHERE location_name = N'北京朝阳慧康药店')
BEGIN
    INSERT INTO dbo.[location](location_name, city, address, contact, phone, longitude, latitude) VALUES
    (N'北京朝阳慧康药店', N'北京', N'北京市朝阳区建国路88号一层', N'刘店长', N'13810001001', 116.468000, 39.914000),
    (N'上海浦东仁济药房', N'上海', N'上海市浦东新区张江路100号', N'陈经理', N'13910001002', 121.587000, 31.204000),
    (N'广州天河康民药店', N'广州', N'广州市天河区华夏路16号', N'黄主管', N'13710001003', 113.323000, 23.119000),
    (N'深圳南山安和药房', N'深圳', N'深圳市南山区科技南路12号', N'周店长', N'13610001004', 113.946000, 22.540000),
    (N'杭州滨江健宁药店', N'杭州', N'杭州市滨江区江南大道388号', N'吴主管', N'13510001005', 120.210000, 30.208000);
END
GO

/* 10. 药品数据 */
IF NOT EXISTS (SELECT 1 FROM dbo.medicine WHERE medicine_name = N'阿莫西林胶囊')
BEGIN
    INSERT INTO dbo.medicine(medicine_name, type, specification, price, manufacturer) VALUES
    (N'阿莫西林胶囊', N'prescription', N'0.25g*24粒', 12.50, N'华北制药'),
    (N'布洛芬缓释胶囊', N'otc', N'0.3g*20粒', 18.80, N'中美史克'),
    (N'盐酸二甲双胍片', N'prescription', N'0.5g*60片', 22.30, N'北京慧康制药'),
    (N'维生素C片', N'health', N'100mg*100片', 9.90, N'上海仁济药业'),
    (N'连花清瘟胶囊', N'otc', N'0.35g*36粒', 29.80, N'以岭药业');
END
GO

/* 11. 医生数据，用于医生列表、职称分布、科室分布图表 */
IF NOT EXISTS (SELECT 1 FROM dbo.doctor WHERE doctor_name = N'张医生')
BEGIN
    INSERT INTO dbo.doctor(doctor_name, department, title, phone, email) VALUES
    (N'张医生', N'内科', N'主任医师', N'13820002001', N'zhang@example.com'),
    (N'李医生', N'外科', N'副主任医师', N'13920002002', N'li@example.com'),
    (N'王医生', N'儿科', N'主治医师', N'13720002003', N'wang@example.com'),
    (N'赵医生', N'妇产科', N'主任医师', N'13620002004', N'zhao@example.com'),
    (N'钱医生', N'皮肤科', N'住院医师', N'13520002005', N'qian@example.com'),
    (N'孙医生', N'内科', N'主治医师', N'13420002006', N'sun@example.com');
END
GO
