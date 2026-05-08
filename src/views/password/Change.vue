<template>
  <div class="change-password">
    <h2>修改密码</h2>
    <el-card class="form-card">
      <el-form :model="passwordForm" :rules="rules" ref="passwordFormRef" label-width="120px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入原密码" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" show-password />
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" placeholder="请确认新密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { changePassword } from '../../api/user'

export default {
  name: 'Change',
  setup() {
    const passwordFormRef = ref(null)
    const passwordForm = ref({ oldPassword: '', newPassword: '', confirmPassword: '' })
    const rules = ref({
      oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
      newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 6, message: '新密码长度至少为6位', trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, message: '请确认新密码', trigger: 'blur' },
        {
          validator: (rule, value, callback) => {
            if (value !== passwordForm.value.newPassword) callback(new Error('两次输入的密码不一致'))
            else callback()
          },
          trigger: 'blur'
        }
      ]
    })

    const submitForm = async () => {
      const valid = await passwordFormRef.value.validate()
      if (!valid) return
      await changePassword({
        oldPassword: passwordForm.value.oldPassword,
        newPassword: passwordForm.value.newPassword
      })
      ElMessage.success('密码修改成功')
      resetForm()
    }

    const resetForm = () => passwordFormRef.value.resetFields()

    return { passwordForm, rules, passwordFormRef, submitForm, resetForm }
  }
}
</script>

<style scoped>
.change-password {
  padding: 20px 0;
}

h2 {
  margin-bottom: 20px;
  color: #1f2937;
}

.form-card {
  max-width: 600px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
