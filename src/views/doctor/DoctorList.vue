<template>
  <div class="doctor-list">
    <h2>医师信息列表</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="医师姓名">
          <el-input v-model="searchForm.doctorName" placeholder="请输入医师姓名"></el-input>
        </el-form-item>
        <el-form-item label="科室">
          <el-select v-model="searchForm.department" placeholder="请选择科室">
            <el-option label="内科" value="内科"></el-option>
            <el-option label="外科" value="外科"></el-option>
            <el-option label="儿科" value="儿科"></el-option>
            <el-option label="妇产科" value="妇产科"></el-option>
            <el-option label="皮肤科" value="皮肤科"></el-option>
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
          <span>医师列表</span>
          <el-button type="primary" @click="addDoctor">添加医师</el-button>
        </div>
      </template>
      <el-table :data="doctorList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="doctorName" label="医师姓名"></el-table-column>
        <el-table-column prop="department" label="科室" width="120"></el-table-column>
        <el-table-column prop="title" label="职称"></el-table-column>
        <el-table-column prop="phone" label="联系电话" width="150"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="editDoctor(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteDoctor(scope.row.id)">删除</el-button>
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
import { getDoctorList, deleteDoctor as deleteDoctorApi } from '../../api/doctor'
export default {
  name: 'DoctorList',
  setup() {
    const router = useRouter()
    const searchForm = ref({ doctorName: '', department: '' })
    const doctorList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const currentData = computed(() => doctorList.value)
    const loadData = async () => { const res = await getDoctorList({ ...searchForm.value, page: currentPage.value, pageSize: pageSize.value }); doctorList.value = res.data?.list || []; total.value = res.data?.total || 0 }
    const search = () => { currentPage.value = 1; loadData() }
    const reset = () => { searchForm.value = { doctorName: '', department: '' }; currentPage.value = 1; loadData() }
    const addDoctor = () => router.push('/doctor/add')
    const editDoctor = doctor => router.push(`/doctor/edit/${doctor.id}`)
    const deleteDoctor = async id => { await ElMessageBox.confirm('确定要删除该医师吗？', '提示', { type: 'warning' }); await deleteDoctorApi(id); ElMessage.success('删除成功'); loadData() }
    const handleSizeChange = size => { pageSize.value = size; currentPage.value = 1; loadData() }
    const handleCurrentChange = current => { currentPage.value = current; loadData() }
    onMounted(loadData)
    return { searchForm, doctorList: currentData, currentPage, pageSize, total, search, reset, addDoctor, editDoctor, deleteDoctor, handleSizeChange, handleCurrentChange }
  }
}
</script>

<style scoped>
.doctor-list {
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
