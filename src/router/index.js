import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../views/Home.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        component: () => import('../views/dashboard/Dashboard.vue'),
        meta: { requiresAuth: true }
      },
      // 用户管理
      {
        path: 'user',
        component: () => import('../views/user/UserList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'user/auth',
        component: () => import('../views/user/Auth.vue'),
        meta: { requiresAuth: true }
      },
      // 密码管理
      {
        path: 'password/change',
        component: () => import('../views/password/Change.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'password/reset',
        component: () => import('../views/password/Reset.vue'),
        meta: { requiresAuth: true }
      },
      // 医药公司管理
      {
        path: 'company',
        component: () => import('../views/company/CompanyList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'company/add',
        component: () => import('../views/company/AddCompany.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'company/edit/:id',
        component: () => import('../views/company/EditCompany.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'company/policy',
        component: () => import('../views/company/PolicyList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'company/policy/add',
        component: () => import('../views/company/AddPolicy.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'company/policy/edit/:id',
        component: () => import('../views/company/EditPolicy.vue'),
        meta: { requiresAuth: true }
      },
      // 必备材料管理
      {
        path: 'material',
        component: () => import('../views/material/MaterialList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'material/add',
        component: () => import('../views/material/AddMaterial.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'material/edit/:id',
        component: () => import('../views/material/EditMaterial.vue'),
        meta: { requiresAuth: true }
      },
      // 城市信息管理
      {
        path: 'city',
        component: () => import('../views/city/CityList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'city/add',
        component: () => import('../views/city/AddCity.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'city/edit/:id',
        component: () => import('../views/city/EditCity.vue'),
        meta: { requiresAuth: true }
      },
      // 销售地点管理
      {
        path: 'location',
        component: () => import('../views/location/LocationList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'location/add',
        component: () => import('../views/location/AddLocation.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'location/edit/:id',
        component: () => import('../views/location/EditLocation.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'location/map',
        component: () => import('../views/location/LocationMap.vue'),
        meta: { requiresAuth: true }
      },
      // 药品信息管理
      {
        path: 'medicine',
        component: () => import('../views/medicine/MedicineList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'medicine/add',
        component: () => import('../views/medicine/AddMedicine.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'medicine/edit/:id',
        component: () => import('../views/medicine/EditMedicine.vue'),
        meta: { requiresAuth: true }
      },
      // 医师管理
      {
        path: 'doctor',
        component: () => import('../views/doctor/DoctorList.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'doctor/add',
        component: () => import('../views/doctor/AddDoctor.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'doctor/edit/:id',
        component: () => import('../views/doctor/EditDoctor.vue'),
        meta: { requiresAuth: true }
      }
    ]
  },
  {
    path: '/login',
    component: () => import('../views/Login.vue')
  },
  // 重定向所有未匹配的路由到登录页
  {
    path: '/:pathMatch(.*)*',
    redirect: '/login'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 检查路由是否需要认证
  if (to.meta.requiresAuth) {
    // 检查是否已登录
    const token = localStorage.getItem('token')
    if (token) {
      // 已登录，继续访问
      next()
    } else {
      // 未登录，重定向到登录页
      next('/login')
    }
  } else {
    // 不需要认证的路由，直接访问
    next()
  }
})

export default router