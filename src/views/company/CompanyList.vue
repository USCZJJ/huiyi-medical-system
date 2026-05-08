<template>
  <div class="company-list">
    <el-card class="filter-card">
      <template #header>
        <div class="card-header">
          <h3 class="card-title">搜索筛选</h3>
        </div>
      </template>
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="公司名称">
          <el-input 
            v-model="searchForm.companyName" 
            placeholder="请输入公司名称"
            size="large"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-select 
            v-model="searchForm.city" 
            placeholder="请选择城市"
            size="large"
            clearable
          >
            <el-option v-for="city in cities" :key="city.cityName" :label="city.cityName" :value="city.cityName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search" size="large">
            <el-icon><Search /></el-icon>
            <span>搜索</span>
          </el-button>
          <el-button @click="reset" size="large">
            <el-icon><Refresh /></el-icon>
            <span>重置</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <h3 class="card-title">公司列表</h3>
          <el-button type="primary" @click="addCompany" size="large">
            <el-icon><Plus /></el-icon>
            <span>添加公司</span>
          </el-button>
        </div>
      </template>
      <el-table 
        :data="companyList" 
        style="width: 100%"
        stripe
        border
        :header-cell-style="{ background: '#f9fafb', fontWeight: '600' }"
        :row-class-name="tableRowClass"
      >
        <el-table-column prop="id" label="ID" width="80">
          <template #default="scope">
            <div class="id-cell">{{ scope.row.id }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="companyName" label="公司名称">
          <template #default="scope">
            <div class="company-name">{{ scope.row.companyName }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="contact" label="联系人" width="120"></el-table-column>
        <el-table-column prop="phone" label="联系电话" width="150"></el-table-column>
        <el-table-column prop="city" label="城市" width="100">
          <template #default="scope">
            <el-tag size="small" effect="plain">{{ scope.row.city }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="editCompany(scope.row)">
                <el-icon><Edit /></el-icon>
                <span>编辑</span>
              </el-button>
              <el-button type="danger" size="small" @click="deleteCompany(scope.row.id)">
                <el-icon><Delete /></el-icon>
                <span>删除</span>
              </el-button>
            </div>
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
          background
          size="large"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getCompanyList, deleteCompany as deleteCompanyApi } from '../../api/company'
import { getCityList } from '../../api/city'
export default {
  name: 'CompanyList',
  components: { Search, Refresh, Plus, Edit, Delete },
  setup() {
    const router = useRouter()
    const searchForm = ref({ companyName: '', city: '' })
    const companyList = ref([])
    const cities = ref([])
    const total = ref(0)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const loadCities = async () => {
      const res = await getCityList({ page: 1, pageSize: 100 })
      cities.value = res.data?.list || []
    }
    const loadData = async () => {
      const res = await getCompanyList({ ...searchForm.value, page: currentPage.value, pageSize: pageSize.value })
      companyList.value = res.data?.list || []
      total.value = res.data?.total || 0
    }
    const search = () => { currentPage.value = 1; loadData() }
    const reset = () => { searchForm.value = { companyName: '', city: '' }; currentPage.value = 1; loadData() }
    const addCompany = () => router.push('/company/add')
    const editCompany = company => router.push(`/company/edit/${company.id}`)
    const deleteCompany = async id => {
      await ElMessageBox.confirm('确定要删除该公司吗？', '提示', { type: 'warning' })
      await deleteCompanyApi(id)
      ElMessage.success('删除成功')
      loadData()
    }
    const handleSizeChange = size => { pageSize.value = size; currentPage.value = 1; loadData() }
    const handleCurrentChange = current => { currentPage.value = current; loadData() }
    const tableRowClass = ({ rowIndex }) => rowIndex % 2 === 0 ? 'table-row-even' : 'table-row-odd'
    onMounted(async () => { await loadCities(); await loadData() })
    return { searchForm, companyList, cities, currentPage, pageSize, total, search, reset, addCompany, editCompany, deleteCompany, handleSizeChange, handleCurrentChange, tableRowClass }
  }
}
</script>

<style scoped>
.company-list {
  padding: 0;
}

.filter-card {
  margin-bottom: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: none;
}

.table-card {
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: none;
  overflow: hidden;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid #f3f4f6;
  background-color: #f9fafb;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
  margin: 0;
}

.search-form {
  padding: 20px;
}

/* 表格样式 */
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
}

:deep(.el-table th) {
  background-color: #f9fafb !important;
  font-weight: 600 !important;
  color: #1f2937 !important;
  border-bottom: 1px solid #e5e7eb !important;
}

:deep(.el-table tr:hover > td) {
  background-color: #f3f4f6 !important;
}

.table-row-even {
  background-color: #f9fafb;
}

.table-row-odd {
  background-color: #ffffff;
}

.id-cell {
  font-weight: 600;
  color: #3b82f6;
}

.company-name {
  font-weight: 500;
  color: #1f2937;
}

.pagination {
  margin-top: 24px;
  padding: 0 20px 20px;
  display: flex;
  justify-content: flex-end;
}

.operation-buttons {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

/* 表单样式 */
:deep(.el-form-item) {
  margin-right: 16px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 2px 6px rgba(59, 130, 246, 0.2);
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.3);
  border-color: #3b82f6;
}

:deep(.el-button--primary) {
  background-color: #3b82f6;
  border-color: #3b82f6;
  border-radius: 8px;
  font-weight: 600;
  transition: all 0.3s ease;
}

:deep(.el-button--primary:hover) {
  background-color: #2563eb;
  border-color: #2563eb;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
}

:deep(.el-button) {
  border-radius: 8px;
  transition: all 0.3s ease;
}

:deep(.el-tag) {
  border-radius: 12px;
  padding: 2px 10px;
  font-size: 12px;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .search-form {
    flex-direction: column;
    align-items: stretch;
  }
  
  :deep(.el-form-item) {
    margin-right: 0;
    margin-bottom: 12px;
  }
  
  .card-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .pagination {
    justify-content: center;
  }
}
</style>
