<template>
  <div class="city-list">
    <h2>城市信息列表</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="城市名称">
          <el-input v-model="searchForm.cityName" placeholder="请输入城市名称"></el-input>
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
          <span>城市列表</span>
          <el-button type="primary" @click="addCity">添加城市</el-button>
        </div>
      </template>
      <el-table :data="filteredCityList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="cityName" label="城市名称"></el-table-column>
        <el-table-column prop="province" label="所属省份"></el-table-column>
        <el-table-column prop="code" label="城市代码" width="120"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="editCity(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteCity(scope.row.id)">删除</el-button>
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
          :total="filteredCityList.length"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCityList, deleteCity as deleteCityApi } from '../../api/city'
export default {
  name: 'CityList',
  setup() {
    const router = useRouter()
    const searchForm = ref({ cityName: '' })
    const cityList = ref([])
    const total = ref(0)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const filteredCityList = computed(() => cityList.value)
    const loadData = async () => { const res = await getCityList({ ...searchForm.value, page: currentPage.value, pageSize: pageSize.value }); cityList.value = res.data?.list || []; total.value = res.data?.total || 0 }
    const search = () => { currentPage.value = 1; loadData() }
    const reset = () => { searchForm.value = { cityName: '' }; currentPage.value = 1; loadData() }
    const addCity = () => router.push('/city/add')
    const editCity = city => router.push(`/city/edit/${city.id}`)
    const deleteCity = async id => { await ElMessageBox.confirm('确定要删除该城市吗？', '提示', { type: 'warning' }); await deleteCityApi(id); ElMessage.success('删除成功'); loadData() }
    const handleSizeChange = size => { pageSize.value = size; currentPage.value = 1; loadData() }
    const handleCurrentChange = current => { currentPage.value = current; loadData() }
    onMounted(loadData)
    return { searchForm, cityList, currentPage, pageSize, total, filteredCityList, search, reset, addCity, editCity, deleteCity, handleSizeChange, handleCurrentChange }
  }
}
</script>

<style scoped>
.city-list {
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

.operation-buttons {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}
</style>
