<template>
  <div class="policy-list">
    <h2>政策管理</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="政策名称">
          <el-input v-model="searchForm.policyName" placeholder="请输入政策名称"></el-input>
        </el-form-item>
        <el-form-item label="适用公司">
          <el-select v-model="searchForm.companyId" placeholder="请选择公司" clearable>
            <el-option v-for="company in companies" :key="company.id" :label="company.companyName" :value="company.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>政策列表</span>
          <el-button type="primary" @click="addPolicy">添加政策</el-button>
        </div>
      </template>
      <el-table :data="policyList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="policyName" label="政策名称"></el-table-column>
        <el-table-column prop="companyName" label="适用公司"></el-table-column>
        <el-table-column prop="effectiveDate" label="生效日期" width="150"></el-table-column>
        <el-table-column prop="expiryDate" label="过期日期" width="150"></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editPolicy(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deletePolicy(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPolicyList, deletePolicy as deletePolicyApi } from '../../api/policy'
import { getCompanyList } from '../../api/company'
export default {
  name: 'PolicyList',
  setup() {
    const router = useRouter()
    const searchForm = ref({ policyName: '', companyId: '' })
    const policyList = ref([])
    const companies = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const loadCompanies = async () => { const res = await getCompanyList({ page: 1, pageSize: 100 }); companies.value = res.data?.list || [] }
    const loadData = async () => {
      const params = { ...searchForm.value, page: currentPage.value, pageSize: pageSize.value }
      const res = await getPolicyList(params)
      policyList.value = res.data?.list || []
      total.value = res.data?.total || 0
    }
    const search = () => { currentPage.value = 1; loadData() }
    const reset = () => { searchForm.value = { policyName: '', companyId: '' }; currentPage.value = 1; loadData() }
    const addPolicy = () => router.push('/company/policy/add')
    const editPolicy = policy => router.push(`/company/policy/edit/${policy.id}`)
    const deletePolicy = async id => { await ElMessageBox.confirm('确定要删除该政策吗？', '提示', { type: 'warning' }); await deletePolicyApi(id); ElMessage.success('删除成功'); loadData() }
    const handleSizeChange = size => { pageSize.value = size; currentPage.value = 1; loadData() }
    const handleCurrentChange = current => { currentPage.value = current; loadData() }
    onMounted(async () => { await loadCompanies(); await loadData() })
    return { searchForm, policyList, companies, currentPage, pageSize, total, search, reset, addPolicy, editPolicy, deletePolicy, handleSizeChange, handleCurrentChange }
  }
}
</script>

<style scoped>
.policy-list {
  padding: 20px 0;
}

h2 {
  margin-bottom: 20px;
  color: #1f2937;
}

.filter-card {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.table-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
