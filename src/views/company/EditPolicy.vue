<template>
  <div class="edit-policy">
    <h2>编辑政策</h2>
    <el-card class="form-card">
      <el-form :model="policyForm" :rules="rules" ref="policyFormRef" label-width="120px">
        <el-form-item label="政策名称" prop="policyName">
          <el-input v-model="policyForm.policyName" placeholder="请输入政策名称" />
        </el-form-item>
        <el-form-item label="适用公司" prop="companyId">
          <el-select v-model="policyForm.companyId" placeholder="请选择公司">
            <el-option
              v-for="company in companies"
              :key="company.id"
              :label="company.companyName"
              :value="company.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="生效日期" prop="effectiveDate">
          <el-date-picker v-model="policyForm.effectiveDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择生效日期" />
        </el-form-item>
        <el-form-item label="过期日期" prop="expiryDate">
          <el-date-picker v-model="policyForm.expiryDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择过期日期" />
        </el-form-item>
        <el-form-item label="政策内容" prop="content">
          <el-input v-model="policyForm.content" placeholder="请输入政策内容" type="textarea" rows="5" />
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
import { getPolicy, updatePolicy } from '../../api/policy'
import { getCompanyList } from '../../api/company'

export default {
  name: 'EditPolicy',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const policyFormRef = ref(null)
    const companies = ref([])
    const policyForm = ref({ policyName: '', companyId: '', effectiveDate: '', expiryDate: '', content: '' })
    const rules = ref({
      policyName: [{ required: true, message: '请输入政策名称', trigger: 'blur' }],
      companyId: [{ required: true, message: '请选择适用公司', trigger: 'change' }],
      effectiveDate: [{ required: true, message: '请选择生效日期', trigger: 'change' }],
      expiryDate: [{ required: true, message: '请选择过期日期', trigger: 'change' }],
      content: [{ required: true, message: '请输入政策内容', trigger: 'blur' }]
    })

    const loadCompanies = async () => {
      const res = await getCompanyList({ page: 1, pageSize: 100 })
      companies.value = res.data?.list || []
    }

    const loadData = async () => {
      const res = await getPolicy(route.params.id)
      policyForm.value = res.data || policyForm.value
    }

    const submitForm = async () => {
      const valid = await policyFormRef.value.validate()
      if (!valid) return
      await updatePolicy(route.params.id, policyForm.value)
      ElMessage.success('政策编辑成功')
      router.push('/company/policy')
    }

    const goBack = () => router.push('/company/policy')

    onMounted(async () => {
      await loadCompanies()
      await loadData()
    })

    return { policyForm, rules, policyFormRef, companies, loadData, submitForm, goBack }
  }
}
</script>

<style scoped>
.edit-policy {
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
