<template>
  <div class="edit-doctor">
    <h2>编辑医师信息</h2>
    <el-card class="form-card">
      <el-form :model="doctorForm" :rules="rules" ref="doctorFormRef" label-width="120px">
        <el-form-item label="医师姓名" prop="doctorName">
          <el-input v-model="doctorForm.doctorName" placeholder="请输入医师姓名"></el-input>
        </el-form-item>
        <el-form-item label="科室" prop="department">
          <el-select v-model="doctorForm.department" placeholder="请选择科室">
            <el-option label="内科" value="内科"></el-option>
            <el-option label="外科" value="外科"></el-option>
            <el-option label="儿科" value="儿科"></el-option>
            <el-option label="妇产科" value="妇产科"></el-option>
            <el-option label="皮肤科" value="皮肤科"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="doctorForm.title" placeholder="请输入职称"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="doctorForm.phone" placeholder="请输入联系电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="doctorForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
          <el-button @click="goBack">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getDoctor, updateDoctor } from '../../api/doctor'
export default {
  name: 'EditDoctor',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const doctorFormRef = ref(null)
    const doctorForm = ref({ doctorName: '', department: '', title: '', phone: '', email: '' })
    const rules = ref({ doctorName: [{ required: true, message: '???????', trigger: 'blur' }], department: [{ required: true, message: '?????', trigger: 'blur' }], title: [{ required: true, message: '?????', trigger: 'blur' }], phone: [{ required: true, message: '???????', trigger: 'blur' }], email: [{ required: true, message: '?????', trigger: 'blur' }, { type: 'email', message: '???????', trigger: 'blur' }] })
    const loadData = async () => { const res = await getDoctor(route.params.id); doctorForm.value = res.data }
    const submitForm = async () => { const valid = await doctorFormRef.value.validate(); if (!valid) return; await updateDoctor(route.params.id, doctorForm.value); ElMessage.success('??????'); router.push('/doctor') }
    const resetForm = () => doctorFormRef.value.resetFields()
    const goBack = () => router.push('/doctor')
    onMounted(loadData)
    return { doctorForm, rules, doctorFormRef, submitForm, resetForm, goBack }
  }
}
</script>

<style scoped>
.edit-doctor {
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
