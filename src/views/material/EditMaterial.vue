<template>
  <div class="edit-material">
    <h2>编辑必备材料</h2>
    <el-card class="form-card">
      <el-form :model="materialForm" :rules="rules" ref="materialFormRef" label-width="120px">
        <el-form-item label="材料名称" prop="materialName">
          <el-input v-model="materialForm.materialName" placeholder="请输入材料名称" />
        </el-form-item>
        <el-form-item label="材料类型" prop="type">
          <el-select v-model="materialForm.type" placeholder="请选择材料类型">
            <el-option label="营业执照" value="business_license" />
            <el-option label="药品经营许可证" value="drug_license" />
            <el-option label="GSP证书" value="gsp_certificate" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="materialForm.description" placeholder="请输入材料描述" type="textarea" rows="3" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="loadData">重置</el-button>
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
import { getMaterial, updateMaterial } from '../../api/material'

export default {
  name: 'EditMaterial',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const materialFormRef = ref(null)
    const materialForm = ref({ materialName: '', type: '', description: '' })
    const rules = ref({
      materialName: [{ required: true, message: '请输入材料名称', trigger: 'blur' }],
      type: [{ required: true, message: '请选择材料类型', trigger: 'change' }],
      description: [{ required: true, message: '请输入材料描述', trigger: 'blur' }]
    })

    const loadData = async () => {
      const res = await getMaterial(route.params.id)
      materialForm.value = res.data || materialForm.value
    }

    const submitForm = async () => {
      const valid = await materialFormRef.value.validate()
      if (!valid) return
      await updateMaterial(route.params.id, materialForm.value)
      ElMessage.success('材料编辑成功')
      router.push('/material')
    }

    const goBack = () => router.push('/material')

    onMounted(loadData)

    return { materialForm, rules, materialFormRef, loadData, submitForm, goBack }
  }
}
</script>

<style scoped>
.edit-material {
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
