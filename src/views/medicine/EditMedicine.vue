<template>
  <div class="edit-medicine">
    <h2>编辑药品信息</h2>
    <el-card class="form-card">
      <el-form :model="medicineForm" :rules="rules" ref="medicineFormRef" label-width="120px">
        <el-form-item label="药品名称" prop="medicineName">
          <el-input v-model="medicineForm.medicineName" placeholder="请输入药品名称" />
        </el-form-item>
        <el-form-item label="药品类型" prop="type">
          <el-select v-model="medicineForm.type" placeholder="请选择药品类型">
            <el-option label="处方药" value="prescription" />
            <el-option label="非处方药" value="otc" />
            <el-option label="保健品" value="health" />
          </el-select>
        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <el-input v-model="medicineForm.specification" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="medicineForm.price" :precision="2" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="medicineForm.manufacturer" placeholder="请输入生产厂家" />
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
import { getMedicine, updateMedicine } from '../../api/medicine'

export default {
  name: 'EditMedicine',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const medicineFormRef = ref(null)
    const medicineForm = ref({ medicineName: '', type: '', specification: '', price: 0, manufacturer: '' })
    const rules = ref({
      medicineName: [{ required: true, message: '请输入药品名称', trigger: 'blur' }],
      type: [{ required: true, message: '请选择药品类型', trigger: 'change' }],
      specification: [{ required: true, message: '请输入规格', trigger: 'blur' }],
      price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
      manufacturer: [{ required: true, message: '请输入生产厂家', trigger: 'blur' }]
    })

    const loadData = async () => {
      const res = await getMedicine(route.params.id)
      medicineForm.value = res.data || medicineForm.value
    }

    const submitForm = async () => {
      const valid = await medicineFormRef.value.validate()
      if (!valid) return
      await updateMedicine(route.params.id, medicineForm.value)
      ElMessage.success('药品编辑成功')
      router.push('/medicine')
    }

    const goBack = () => router.push('/medicine')

    onMounted(loadData)

    return { medicineForm, rules, medicineFormRef, loadData, submitForm, goBack }
  }
}
</script>

<style scoped>
.edit-medicine {
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
