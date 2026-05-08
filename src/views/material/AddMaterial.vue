<template>
  <div class="add-material">
    <h2>添加必备材料</h2>
    <el-card class="form-card">
      <el-form :model="materialForm" :rules="rules" ref="materialFormRef" label-width="120px">
        <el-form-item label="材料名称" prop="materialName">
          <el-input v-model="materialForm.materialName" placeholder="请输入材料名称"></el-input>
        </el-form-item>
        <el-form-item label="材料类型" prop="type">
          <el-select v-model="materialForm.type" placeholder="请选择材料类型">
            <el-option label="营业执照" value="business_license"></el-option>
            <el-option label="药品经营许可证" value="drug_license"></el-option>
            <el-option label="GSP证书" value="gsp_certificate"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="materialForm.description" placeholder="请输入材料描述" type="textarea" rows="3"></el-input>
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
import { createMaterial } from '../../api/material'
export default {
  name: 'AddMaterial',
  setup() {
    const router = useRouter()
    const materialFormRef = ref(null)
    const materialForm = ref({ materialName: '', type: '', description: '' })
    const rules = ref({ materialName: [{ required: true, message: '???????', trigger: 'blur' }], type: [{ required: true, message: '???????', trigger: 'blur' }], description: [{ required: true, message: '???????', trigger: 'blur' }] })
    const submitForm = async () => { const valid = await materialFormRef.value.validate(); if (!valid) return; await createMaterial(materialForm.value); ElMessage.success('??????'); router.push('/material') }
    const resetForm = () => materialFormRef.value.resetFields()
    const goBack = () => router.push('/material')
    return { materialForm, rules, materialFormRef, submitForm, resetForm, goBack }
  }
}
</script>

<style scoped>
.add-material {
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
