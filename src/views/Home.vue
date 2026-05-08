<template>
  <div class="home-container">
    <!-- 左侧导航 -->
    <el-aside width="260px" class="aside">
      <div class="logo">
        <div class="logo-icon">
          <el-icon class="logo-icon-el"><House /></el-icon>
        </div>
        <h1 class="logo-text">慧医数字医疗</h1>
        <p class="logo-subtitle">医疗应用管理系统</p>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="menu"
        router
        :collapse-transition="false"
      >
        <el-menu-item index="/">
          <el-icon class="menu-icon"><HomeFilled /></el-icon>
          <span class="menu-item-text">首页</span>
        </el-menu-item>
        
        <!-- 医药公司管理 -->
        <el-sub-menu index="company">
          <template #title>
            <el-icon class="menu-icon"><OfficeBuilding /></el-icon>
            <span class="menu-item-text">医药公司管理</span>
          </template>
          <el-menu-item index="/company">
            <el-icon class="sub-menu-icon"><List /></el-icon>
            <span class="menu-item-text">公司列表</span>
          </el-menu-item>
          <el-menu-item index="/company/policy">
            <el-icon class="sub-menu-icon"><Document /></el-icon>
            <span class="menu-item-text">政策管理</span>
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 必备材料管理 -->
        <el-sub-menu index="material">
          <template #title>
            <el-icon class="menu-icon"><Files /></el-icon>
            <span class="menu-item-text">必备材料管理</span>
          </template>
          <el-menu-item index="/material">
            <el-icon class="sub-menu-icon"><List /></el-icon>
            <span class="menu-item-text">材料列表</span>
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 城市信息管理 -->
        <el-sub-menu index="city">
          <template #title>
            <el-icon class="menu-icon"><Location /></el-icon>
            <span class="menu-item-text">城市信息管理</span>
          </template>
          <el-menu-item index="/city">
            <el-icon class="sub-menu-icon"><List /></el-icon>
            <span class="menu-item-text">城市列表</span>
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 销售地点管理 -->
        <el-sub-menu index="location">
          <template #title>
            <el-icon class="menu-icon"><Position /></el-icon>
            <span class="menu-item-text">销售地点管理</span>
          </template>
          <el-menu-item index="/location">
            <el-icon class="sub-menu-icon"><List /></el-icon>
            <span class="menu-item-text">地点列表</span>
          </el-menu-item>
          <el-menu-item index="/location/map">
            <el-icon class="sub-menu-icon"><MapLocation /></el-icon>
            <span class="menu-item-text">地图展示</span>
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 药品信息管理 -->
        <el-sub-menu index="medicine">
          <template #title>
            <el-icon class="menu-icon"><Warning /></el-icon>
            <span class="menu-item-text">药品信息管理</span>
          </template>
          <el-menu-item index="/medicine">
            <el-icon class="sub-menu-icon"><List /></el-icon>
            <span class="menu-item-text">药品列表</span>
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 医师管理 -->
        <el-sub-menu index="doctor">
          <template #title>
            <el-icon class="menu-icon"><UserFilled /></el-icon>
            <span class="menu-item-text">医师管理</span>
          </template>
          <el-menu-item index="/doctor">
            <el-icon class="sub-menu-icon"><List /></el-icon>
            <span class="menu-item-text">医师列表</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    
    <!-- 右侧内容 -->
    <el-container class="main-content">
      <el-header class="header">
        <div class="header-left">

          <h2 class="page-title">{{ currentPageTitle }}</h2>
        </div>
        <div class="header-right">

          <div class="user-info">
            <div class="user-status">
              <span class="status-dot"></span>
              <span class="status-text">在线</span>
            </div>
            <el-avatar size="default" class="user-avatar">{{ userInfo.name ? userInfo.name.charAt(0) : '管' }}</el-avatar>
            <span class="user-name">{{ userInfo.name || '管理员' }}</span>
            <el-dropdown>
              <div class="dropdown-trigger">
                <el-icon class="arrow-icon">
                  <ArrowDown />
                </el-icon>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="goToProfile">
                    <el-icon><User /></el-icon>
                    <span>个人资料</span>
                  </el-dropdown-item>
                  <el-dropdown-item @click="goToUserList">
                    <el-icon><UserFilled /></el-icon>
                    <span>用户列表</span>
                  </el-dropdown-item>
                  <el-dropdown-item @click="goToUserAuth">
                    <el-icon><Lock /></el-icon>
                    <span>用户授权</span>
                  </el-dropdown-item>
                  <el-dropdown-item divided @click="changePassword">
                    <el-icon><Key /></el-icon>
                    <span>修改密码</span>
                  </el-dropdown-item>
                  <el-dropdown-item @click="resetPassword">
                    <el-icon><Refresh /></el-icon>
                    <span>重置密码</span>
                  </el-dropdown-item>
                  <el-dropdown-item divided @click="logout">
                    <el-icon><SwitchButton /></el-icon>
                    <span>退出登录</span>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-main class="content-area">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {
  ArrowDown,
  House,
  HomeFilled,
  OfficeBuilding,
  List,
  Document,
  Files,
  Location,
  Position,
  MapLocation,
  Warning,
  UserFilled,
  User,
  Lock,
  Key,
  Refresh,
  SwitchButton
} from '@element-plus/icons-vue'

export default {
  name: 'Home',
  components: {
    ArrowDown,
    House,
    HomeFilled,
    OfficeBuilding,
    List,
    Document,
    Files,
    Location,
    Position,
    MapLocation,
    Warning,
    UserFilled,
    User,
    Lock,
    Key,
    Refresh,
    SwitchButton
  },
  setup() {
    const router = useRouter()
    const userInfo = ref({
      name: '管理员',
      role: 'admin'
    })
    
    // 页面加载时获取用户信息
    onMounted(() => {
      // 从 localStorage 读取用户信息
      const userStr = localStorage.getItem('user')
      if (userStr) {
        userInfo.value = JSON.parse(userStr)
      }
    })
    
    const activeMenu = computed(() => {
      return router.currentRoute.value.path
    })
    
    const currentPageTitle = computed(() => {
      const path = router.currentRoute.value.path
      const titleMap = {
        '/': '首页仪表盘',
        '/user': '用户列表',
        '/user/auth': '用户授权',
        '/password/change': '修改密码',
        '/password/reset': '重置密码',
        '/company': '公司列表',
        '/company/add': '添加公司',
        '/company/edit': '编辑公司',
        '/company/policy': '政策管理',
        '/company/policy/add': '添加政策',
        '/company/policy/edit': '编辑政策',
        '/material': '材料列表',
        '/material/add': '添加材料',
        '/material/edit': '编辑材料',
        '/city': '城市列表',
        '/city/add': '添加城市',
        '/city/edit': '编辑城市',
        '/location': '地点列表',
        '/location/add': '添加地点',
        '/location/edit': '编辑地点',
        '/location/map': '地图展示',
        '/medicine': '药品列表',
        '/medicine/add': '添加药品',
        '/medicine/edit': '编辑药品',
        '/doctor': '医师列表',
        '/doctor/add': '添加医师',
        '/doctor/edit': '编辑医师'
      }
      return titleMap[path] || '慧医数字医疗'
    })
    
    const logout = () => {
      // 清除本地存储
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      // 跳转到登录页
      router.push('/login')
    }
    
    const goToProfile = () => {
      // 跳转到个人资料页面
      console.log('个人资料')
    }
    
    const goToUserList = () => {
      router.push('/user')
    }
    
    const goToUserAuth = () => {
      router.push('/user/auth')
    }
    
    const changePassword = () => {
      router.push('/password/change')
    }
    
    const resetPassword = () => {
      router.push('/password/reset')
    }
    
    return {
      activeMenu,
      currentPageTitle,
      userInfo,
      logout,
      goToProfile,
      goToUserList,
      goToUserAuth,
      changePassword,
      resetPassword
    }
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  height: 100%;
  width: 100%;
  overflow: hidden;
  font-family: 'Microsoft YaHei', Arial, sans-serif;
  background-color: #f0f2f5;
}

/* 左侧导航栏 */
.aside {
  background: linear-gradient(180deg, #1f2937 0%, #111827 100%);
  color: white;
  height: 100%;
  overflow-y: auto;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
}

.logo {
  padding: 28px 20px;
  text-align: center;
  border-bottom: 1px solid #374151;
  background-color: rgba(255, 255, 255, 0.05);
}

.logo-icon {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #3b82f6, #6366f1);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16px;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.logo-icon-el {
  font-size: 32px;
  color: white;
}

.logo-text {
  font-size: 20px;
  font-weight: bold;
  margin: 0 0 6px 0;
  color: #ffffff;
  letter-spacing: 1px;
}

.logo-subtitle {
  font-size: 12px;
  color: #9ca3af;
  margin: 0;
  font-weight: 400;
}

.menu {
  border-right: none;
  background-color: transparent;
  padding-top: 16px;
}

.menu-item-text {
  font-size: 14px;
  font-weight: 500;
}

.menu-icon {
  font-size: 20px;
  margin-right: 12px;
}

.sub-menu-icon {
  font-size: 16px;
  margin-right: 8px;
}

.menu :deep(.el-menu-item) {
  color: #e5e7eb;
  height: 50px;
  line-height: 50px;
  margin: 0 12px;
  border-radius: 8px;
  font-size: 14px;
  display: flex;
  align-items: center;
}

.menu :deep(.el-menu-item:hover),
.menu :deep(.el-menu-item.is-active) {
  background-color: #374151;
  color: #ffffff;
  font-weight: 600;
}

.menu :deep(.el-sub-menu__title) {
  color: #e5e7eb;
  height: 50px;
  line-height: 50px;
  margin: 0 12px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
}

.menu :deep(.el-sub-menu__title:hover) {
  background-color: #374151;
  color: #ffffff;
}

.menu :deep(.el-sub-menu .el-menu) {
  background-color: transparent;
}

.menu :deep(.el-sub-menu .el-menu-item) {
  height: 45px;
  line-height: 45px;
  margin: 0 24px;
  font-size: 13px;
  padding-left: 8px;
  background-color: rgba(255, 255, 255, 0.05);
  border-radius: 6px;
  margin: 4px 24px;
  transition: all 0.3s ease;
}

.menu :deep(.el-sub-menu .el-menu-item:hover) {
  background-color: #374151;
  color: #ffffff;
  transform: translateX(4px);
}

.menu :deep(.el-sub-menu .el-menu-item.is-active) {
  background-color: #3b82f6;
  color: #ffffff;
  font-weight: 600;
}

/* 右侧主内容区 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #f0f2f5;
  min-width: 0;
}

/* 顶部导航栏 */
.header {
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 32px;
  height: 72px;
  border-bottom: 1px solid #e5e7eb;
}

.header-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 4px;
}

.breadcrumb {
  font-size: 12px;
  color: #6b7280;
}

.breadcrumb :deep(.el-breadcrumb__item:last-child .el-breadcrumb__inner) {
  color: #3b82f6;
  font-weight: 500;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background-color: #f9fafb;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.user-info:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  background-color: #f3f4f6;
}

.user-status {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #6b7280;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #10b981;
  box-shadow: 0 0 0 2px rgba(16, 185, 129, 0.2);
}

.status-text {
  font-weight: 500;
}

.user-avatar {
  background-color: #3b82f6;
  color: white;
  font-weight: 600;
  box-shadow: 0 2px 4px rgba(59, 130, 246, 0.3);
  transition: all 0.3s ease;
}

.user-avatar:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(59, 130, 246, 0.4);
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #374151;
}

.dropdown-trigger {
  font-size: 16px;
  color: #6b7280;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 4px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.dropdown-trigger:hover {
  background-color: #f3f4f6;
  color: #3b82f6;
  transform: translateY(-1px);
}

.arrow-icon {
  font-size: 16px;
  width: 16px;
  height: 16px;
}

/* 下拉菜单样式 */
:deep(.el-dropdown-menu) {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border: none;
  padding: 8px 0;
}

:deep(.el-dropdown-item) {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  font-size: 14px;
  transition: all 0.2s ease;
}

:deep(.el-dropdown-item:hover) {
  background-color: #f3f4f6;
  color: #3b82f6;
}

:deep(.el-dropdown-item.is-disabled) {
  color: #9ca3af;
}

:deep(.el-dropdown-item.divided) {
  border-top: 1px solid #e5e7eb;
  margin-top: 8px;
  padding-top: 16px;
}

/* 内容区域 */
.content-area {
  flex: 1;
  padding: 24px 32px;
  overflow-y: auto;
  background-color: #f0f2f5;
}

/* 响应式调整 */
@media (max-width: 1200px) {
  .aside {
    width: 220px !important;
  }
  
  .content-area {
    padding: 20px;
  }
}
</style>