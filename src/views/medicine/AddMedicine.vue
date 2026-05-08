<template>
  <div class="add-medicine">
    <h2>添加药品信息</h2>
    <el-card class="form-card">
      <el-form :model="medicineForm" :rules="rules" ref="medicineFormRef" label-width="120px">
        <el-form-item label="药品名称" prop="medicineName">
          <el-input v-model="medicineForm.medicineName" placeholder="请输入药品名称"></el-input>
        </el-form-item>
        <el-form-item label="药品类型" prop="type">
          <el-select v-model="medicineForm.type" placeholder="请选择药品类型">
            <el-option label="处方药" value="prescription"></el-option>
            <el-option label="非处方药" value="otc"></el-option>
            <el-option label="保健品" value="health"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <el-input v-model="medicineForm.specification" placeholder="请输入规格"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="medicineForm.price" type="number" placeholder="请输入价格"></el-input>
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="medicineForm.manufacturer" placeholder="请输入生产厂家"></el-input>
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
import { createMedicine } from '../../api/medicine'
export default {
  name: 'AddMedicine',
  setup() {
    const router = useRouter()
    const medicineFormRef = ref(null)
    const medicineForm = ref({ medicineName: '', type: '', specification: '', price: '', manufacturer: '' })
    const rules = ref({ medicineName: [{ required: true, message: '???????', trigger: 'blur' }], type: [{ required: true, message: '???????', trigger: 'blur' }], specification: [{ required: true, message: '?????', trigger: 'blur' }], price: [{ required: true, message: '?????', trigger: 'blur' }], manufacturer: [{ required: true, message: '???????', trigger: 'blur' }] })
    const submitForm = async () => { const valid = await medicineFormRef.value.validate(); if (!valid) return; await createMedicine({ ...medicineForm.value, price: Number(medicineForm.value.price) }); ElMessage.success('??????'); router.push('/medicine') }
    const resetForm = () => medicineFormRef.value.resetFields()
    const goBack = () => router.push('/medicine')
    return { medicineForm, rules, medicineFormRef, submitForm, resetForm, goBack }
  }
}
</script>

<style scoped>
.add-medicine {
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
