<template>
  <div class="add-city">
    <h2>添加城市信息</h2>
    <el-card class="form-card">
      <el-form :model="cityForm" :rules="rules" ref="cityFormRef" label-width="120px">
        <el-form-item label="城市名称" prop="cityName">
          <el-input v-model="cityForm.cityName" placeholder="请输入城市名称"></el-input>
        </el-form-item>
        <el-form-item label="所属省份" prop="province">
          <el-input v-model="cityForm.province" placeholder="请输入所属省份"></el-input>
        </el-form-item>
        <el-form-item label="城市代码" prop="code">
          <el-input v-model="cityForm.code" placeholder="请输入城市代码"></el-input>
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
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createCity } from '../../api/city'
export default {
  name: 'AddCity',
  setup() {
    const router = useRouter()
    const cityFormRef = ref(null)
    const cityForm = ref({ cityName: '', province: '', code: '' })
    const rules = ref({ cityName: [{ required: true, message: '???????', trigger: 'blur' }], province: [{ required: true, message: '???????', trigger: 'blur' }], code: [{ required: true, message: '???????', trigger: 'blur' }] })
    const submitForm = async () => { const valid = await cityFormRef.value.validate(); if (!valid) return; await createCity(cityForm.value); ElMessage.success('??????'); router.push('/city') }
    const resetForm = () => cityFormRef.value.resetFields()
    const goBack = () => router.push('/city')
    return { cityForm, rules, cityFormRef, submitForm, resetForm, goBack }
  }
}
</script>

<style scoped>
.add-city {
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
