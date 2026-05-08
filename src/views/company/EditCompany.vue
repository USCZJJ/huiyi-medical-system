<template>
  <div class="edit-company">
    <h2>编辑医药公司</h2>
    <el-card class="form-card">
      <el-form :model="companyForm" :rules="rules" ref="companyFormRef" label-width="120px">
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="companyForm.companyName" placeholder="请输入公司名称"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="companyForm.contact" placeholder="请输入联系人"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="companyForm.phone" placeholder="请输入联系电话"></el-input>
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-select v-model="companyForm.city" placeholder="请选择城市">
            <el-option v-for="city in cities" :key="city.cityName" :label="city.cityName" :value="city.cityName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="companyForm.address" placeholder="请输入地址" type="textarea" rows="3"></el-input>
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
import { getCompany, updateCompany } from '../../api/company'
import { getCityList } from '../../api/city'
export default {
  name: 'EditCompany',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const companyFormRef = ref(null)
    const cities = ref([])
    const companyForm = ref({ companyName: '', contact: '', phone: '', city: '', address: '' })
    const rules = ref({
      companyName: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
      contact: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
      phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
      city: [{ required: true, message: '请选择城市', trigger: 'change' }],
      address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
    })
    const loadCities = async () => { const res = await getCityList({ page: 1, pageSize: 100 }); cities.value = res.data?.list || [] }
    const loadData = async () => { const res = await getCompany(route.params.id); companyForm.value = res.data }
    const submitForm = async () => { const valid = await companyFormRef.value.validate(); if (!valid) return; await updateCompany(route.params.id, companyForm.value); ElMessage.success('编辑成功'); router.push('/company') }
    const resetForm = () => companyFormRef.value.resetFields()
    const goBack = () => router.push('/company')
    onMounted(async () => { await loadCities(); await loadData() })
    return { companyForm, rules, companyFormRef, cities, submitForm, resetForm, goBack }
  }
}
</script>

<style scoped>
.edit-company {
  padding: 20px 0;
}

h2 {
  margin-bottom: 20px;
  color: #1f2937;
}

.form-card {
  max-width: 800px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
