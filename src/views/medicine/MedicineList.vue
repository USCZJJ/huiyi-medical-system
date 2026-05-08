<template>
  <div class="medicine-list">
    <h2>药品信息列表</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="药品名称">
          <el-input v-model="searchForm.medicineName" placeholder="请输入药品名称" />
        </el-form-item>
        <el-form-item label="药品类型">
          <el-select v-model="searchForm.type" placeholder="请选择药品类型" clearable>
            <el-option label="处方药" value="prescription" />
            <el-option label="非处方药" value="otc" />
            <el-option label="保健品" value="health" />
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
          <span>药品列表</span>
          <el-button type="primary" @click="addMedicine">添加药品</el-button>
        </div>
      </template>
      <el-table :data="medicineList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="medicineName" label="药品名称" />
        <el-table-column prop="type" label="药品类型" width="120">
          <template #default="scope">
            <el-tag :type="typeTag(scope.row.type)">{{ typeName(scope.row.type) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column prop="manufacturer" label="生产厂家" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="editMedicine(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteMedicine(scope.row.id)">删除</el-button>
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
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMedicineList, deleteMedicine as deleteMedicineApi } from '../../api/medicine'

export default {
  name: 'MedicineList',
  setup() {
    const router = useRouter()
    const searchForm = ref({ medicineName: '', type: '' })
    const medicineList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    const loadData = async () => {
      const res = await getMedicineList({
        ...searchForm.value,
        page: currentPage.value,
        pageSize: pageSize.value
      })
      medicineList.value = res.data?.list || []
      total.value = res.data?.total || 0
    }

    const search = () => {
      currentPage.value = 1
      loadData()
    }

    const reset = () => {
      searchForm.value = { medicineName: '', type: '' }
      currentPage.value = 1
      loadData()
    }

    const addMedicine = () => router.push('/medicine/add')
    const editMedicine = medicine => router.push(`/medicine/edit/${medicine.id}`)

    const deleteMedicine = async id => {
      await ElMessageBox.confirm('确定要删除该药品吗？', '提示', { type: 'warning' })
      await deleteMedicineApi(id)
      ElMessage.success('删除成功')
      loadData()
    }

    const handleSizeChange = size => {
      pageSize.value = size
      currentPage.value = 1
      loadData()
    }
    const handleCurrentChange = current => {
      currentPage.value = current
      loadData()
    }

    const typeName = type => ({
      prescription: '处方药',
      otc: '非处方药',
      health: '保健品'
    }[type] || type)
    const typeTag = type => type === 'prescription' ? 'primary' : type === 'otc' ? 'success' : 'warning'

    onMounted(loadData)

    return {
      searchForm,
      medicineList,
      currentPage,
      pageSize,
      total,
      search,
      reset,
      addMedicine,
      editMedicine,
      deleteMedicine,
      handleSizeChange,
      handleCurrentChange,
      typeName,
      typeTag
    }
  }
}
</script>

<style scoped>
.medicine-list {
  padding: 20px 0;
}

h2 {
  margin-bottom: 20px;
  color: #1f2937;
}

.filter-card,
.table-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.filter-card {
  margin-bottom: 20px;
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
}
</style>
