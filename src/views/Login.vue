<template>
  <div class="login-container">
    <div class="login-wrapper">
      <div class="login-left">
        <div class="login-banner">
          <div class="logo">
            <div class="logo-icon">
              <el-icon class="logo-icon-el"><House /></el-icon>
            </div>
            <h1 class="logo-text">慧医数字医疗</h1>
            <p class="logo-subtitle">医疗应用管理系统</p>
          </div>
          <div class="login-slogan">
            <h2>智能医疗管理平台</h2>
            <p>高效、安全、便捷的医疗资源管理解决方案</p>
          </div>
        </div>
      </div>
      <div class="login-right">
        <div class="login-form">
          <h3 class="form-title">用户登录</h3>
          <p class="form-subtitle">请输入您的账号和密码</p>
          <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-position="top">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                prefix-icon="Lock"
                size="large"
                show-password
                @keyup.enter="login"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="login" style="width: 100%" :loading="loading" size="large">
                {{ loading ? '登录中...' : '登录' }}
              </el-button>
            </el-form-item>
            <div class="login-footer">
              <span class="copyright">© 2024 慧医数字医疗应用系统</span>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { House, User, Lock } from '@element-plus/icons-vue'
import { login as loginApi } from '../api/user'

export default {
  name: 'Login',
  components: { House, User, Lock },
  setup() {
    const router = useRouter()
    const loginFormRef = ref(null)
    const loading = ref(false)
    const loginForm = ref({ username: 'admin', password: 'admin123' })
    const rules = ref({
      username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
      password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
    })

    const login = async () => {
      const valid = await loginFormRef.value.validate()
      if (!valid) return

      loading.value = true
      try {
        const res = await loginApi(loginForm.value)
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('user', JSON.stringify(res.data.user))
        ElMessage.success('登录成功')
        router.push('/')
      } finally {
        loading.value = false
      }
    }

    return { loading, loginForm, rules, loginFormRef, login }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
  font-family: 'Microsoft YaHei', Arial, sans-serif;
}

.login-wrapper {
  display: flex;
  width: 900px;
  height: 550px;
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, #3b82f6, #6366f1);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  color: white;
}

.login-banner {
  text-align: center;
  max-width: 300px;
}

.logo {
  margin-bottom: 40px;
}

.logo-icon {
  width: 80px;
  height: 80px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  backdrop-filter: blur(10px);
}

.logo-icon-el {
  font-size: 40px;
  color: white;
}

.logo-text {
  font-size: 24px;
  font-weight: bold;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.logo-subtitle {
  font-size: 14px;
  opacity: 0.9;
  margin: 0;
}

.login-slogan h2 {
  font-size: 28px;
  font-weight: bold;
  margin: 0 0 16px 0;
}

.login-slogan p {
  font-size: 16px;
  opacity: 0.9;
  line-height: 1.6;
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.login-form {
  width: 100%;
  max-width: 320px;
}

.form-title {
  font-size: 24px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 8px 0;
  text-align: center;
}

.form-subtitle {
  font-size: 14px;
  color: #6b7280;
  margin: 0 0 32px 0;
  text-align: center;
}

.login-footer {
  margin-top: 32px;
  text-align: center;
}

.copyright {
  font-size: 12px;
  color: #9ca3af;
}

:deep(.el-form-item) {
  margin-bottom: 20px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
}

:deep(.el-button--primary) {
  background-color: #3b82f6;
  border-color: #3b82f6;
  border-radius: 8px;
  font-weight: 600;
}

@media (max-width: 768px) {
  .login-wrapper {
    flex-direction: column;
    width: 90%;
    height: auto;
  }

  .login-left,
  .login-right {
    padding: 30px;
  }
}
</style>
