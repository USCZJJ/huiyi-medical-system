<template>
  <div class="location-list">
    <h2>销售地点列表</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="地点名称">
          <el-input v-model="searchForm.locationName" placeholder="请输入地点名称"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-select v-model="searchForm.city" placeholder="请选择城市">
            <el-option v-for="city in cities" :key="city.cityName" :label="city.cityName" :value="city.cityName"></el-option>
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
          <span>地点列表</span>
          <el-button type="primary" @click="addLocation">添加地点</el-button>
        </div>
      </template>
      <el-table :data="locationList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="locationName" label="地点名称"></el-table-column>
        <el-table-column prop="city" label="城市" width="100"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="contact" label="联系人" width="120"></el-table-column>
        <el-table-column prop="phone" label="联系电话" width="150"></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="editLocation(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteLocation(scope.row.id)">删除</el-button>
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
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getLocationList, deleteLocation as deleteLocationApi } from '../../api/location'
import { getCityList } from '../../api/city'
export default {
  name: 'LocationList',
  setup() {
    const router = useRouter()
    const searchForm = ref({ locationName: '', city: '' })
    const locationList = ref([])
    const cities = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const currentData = computed(() => locationList.value)
    const loadCities = async () => { const res = await getCityList({ page: 1, pageSize: 100 }); cities.value = res.data?.list || [] }
    const loadData = async () => { const res = await getLocationList({ ...searchForm.value, page: currentPage.value, pageSize: pageSize.value }); locationList.value = res.data?.list || []; total.value = res.data?.total || 0 }
    const search = () => { currentPage.value = 1; loadData() }
    const reset = () => { searchForm.value = { locationName: '', city: '' }; currentPage.value = 1; loadData() }
    const addLocation = () => router.push('/location/add')
    const editLocation = location => router.push(`/location/edit/${location.id}`)
    const deleteLocation = async id => { await ElMessageBox.confirm('确定要删除该地点吗？', '提示', { type: 'warning' }); await deleteLocationApi(id); ElMessage.success('删除成功'); loadData() }
    const handleSizeChange = size => { pageSize.value = size; currentPage.value = 1; loadData() }
    const handleCurrentChange = current => { currentPage.value = current; loadData() }
    onMounted(async () => { await loadCities(); await loadData() })
    return { searchForm, locationList: currentData, cities, currentPage, pageSize, total, search, reset, addLocation, editLocation, deleteLocation, handleSizeChange, handleCurrentChange }
  }
}
</script>

<style scoped>
.location-list {
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
