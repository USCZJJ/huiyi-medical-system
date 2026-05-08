<template>
  <div class="add-location">
    <h2>添加销售地点</h2>
    <el-card class="form-card">
      <el-form :model="locationForm" :rules="rules" ref="locationFormRef" label-width="120px">
        <el-form-item label="地点名称" prop="locationName">
          <el-input v-model="locationForm.locationName" placeholder="请输入地点名称"></el-input>
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-select v-model="locationForm.city" placeholder="请选择城市">
            <el-option v-for="city in cities" :key="city.cityName" :label="city.cityName" :value="city.cityName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="locationForm.address" placeholder="请输入地址" type="textarea" rows="3"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="locationForm.contact" placeholder="请输入联系人"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="locationForm.phone" placeholder="请输入联系电话"></el-input>
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
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createLocation } from '../../api/location'
import { getCityList } from '../../api/city'
export default {
  name: 'AddLocation',
  setup() {
    const router = useRouter()
    const locationFormRef = ref(null)
    const locationForm = ref({ locationName: '', city: '', address: '', contact: '', phone: '', longitude: null, latitude: null })
    const cities = ref([])
    const rules = ref({ locationName: [{ required: true, message: '???????', trigger: 'blur' }], city: [{ required: true, message: '?????', trigger: 'blur' }], address: [{ required: true, message: '?????', trigger: 'blur' }], contact: [{ required: true, message: '??????', trigger: 'blur' }], phone: [{ required: true, message: '???????', trigger: 'blur' }] })
    const loadCities = async () => { const res = await getCityList({ page: 1, pageSize: 100 }); cities.value = res.data?.list || [] }
    const submitForm = async () => { const valid = await locationFormRef.value.validate(); if (!valid) return; await createLocation(locationForm.value); ElMessage.success('??????'); router.push('/location') }
    const resetForm = () => locationFormRef.value.resetFields()
    const goBack = () => router.push('/location')
    onMounted(loadCities)
    return { locationForm, rules, locationFormRef, cities, submitForm, resetForm, goBack }
  }
}
</script>

<style scoped>
.add-location {
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
