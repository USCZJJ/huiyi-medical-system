<template>
  <div class="edit-location">
    <h2>编辑销售地点</h2>
    <el-card class="form-card">
      <el-form :model="locationForm" :rules="rules" ref="locationFormRef" label-width="120px">
        <el-form-item label="地点名称" prop="locationName">
          <el-input v-model="locationForm.locationName" placeholder="请输入地点名称" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-select v-model="locationForm.city" placeholder="请选择城市">
            <el-option v-for="city in cities" :key="city.cityName" :label="city.cityName" :value="city.cityName" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="locationForm.address" placeholder="请输入地址" type="textarea" rows="3" />
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="locationForm.contact" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="locationForm.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input-number v-model="locationForm.longitude" :precision="6" style="width: 100%" />
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input-number v-model="locationForm.latitude" :precision="6" style="width: 100%" />
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
import { getLocation, updateLocation } from '../../api/location'
import { getCityList } from '../../api/city'

export default {
  name: 'EditLocation',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const locationFormRef = ref(null)
    const cities = ref([])
    const locationForm = ref({
      locationName: '',
      city: '',
      address: '',
      contact: '',
      phone: '',
      longitude: null,
      latitude: null
    })
    const rules = ref({
      locationName: [{ required: true, message: '请输入地点名称', trigger: 'blur' }],
      city: [{ required: true, message: '请选择城市', trigger: 'change' }],
      address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
      contact: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
      phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
    })

    const loadCities = async () => {
      const res = await getCityList({ page: 1, pageSize: 100 })
      cities.value = res.data?.list || []
    }

    const loadData = async () => {
      const res = await getLocation(route.params.id)
      locationForm.value = res.data || locationForm.value
    }

    const submitForm = async () => {
      const valid = await locationFormRef.value.validate()
      if (!valid) return
      await updateLocation(route.params.id, locationForm.value)
      ElMessage.success('地点编辑成功')
      router.push('/location')
    }

    const goBack = () => router.push('/location')

    onMounted(async () => {
      await loadCities()
      await loadData()
    })

    return { locationForm, rules, locationFormRef, cities, loadData, submitForm, goBack }
  }
}
</script>

<style scoped>
.edit-location {
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
