<template>
  <div class="material-list">
    <h2>必备材料列表</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="材料名称">
          <el-input v-model="searchForm.materialName" placeholder="请输入材料名称" />
        </el-form-item>
        <el-form-item label="材料类型">
          <el-select v-model="searchForm.type" placeholder="请选择材料类型" clearable>
            <el-option label="营业执照" value="business_license" />
            <el-option label="药品经营许可证" value="drug_license" />
            <el-option label="GSP证书" value="gsp_certificate" />
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
          <span>材料列表</span>
          <el-button type="primary" @click="addMaterial">添加材料</el-button>
        </div>
      </template>
      <el-table :data="materialList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="materialName" label="材料名称" />
        <el-table-column prop="type" label="材料类型" width="150">
          <template #default="scope">
            <el-tag :type="typeTag(scope.row.type)">{{ typeName(scope.row.type) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <div class="operation-buttons">
              <el-button type="primary" size="small" @click="editMaterial(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteMaterial(scope.row.id)">删除</el-button>
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
import { getMaterialList, deleteMaterial as deleteMaterialApi } from '../../api/material'

export default {
  name: 'MaterialList',
  setup() {
    const router = useRouter()
    const searchForm = ref({ materialName: '', type: '' })
    const materialList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    const loadData = async () => {
      const res = await getMaterialList({
        ...searchForm.value,
        page: currentPage.value,
        pageSize: pageSize.value
      })
      materialList.value = res.data?.list || []
      total.value = res.data?.total || 0
    }

    const search = () => {
      currentPage.value = 1
      loadData()
    }

    const reset = () => {
      searchForm.value = { materialName: '', type: '' }
      currentPage.value = 1
      loadData()
    }

    const addMaterial = () => router.push('/material/add')
    const editMaterial = material => router.push(`/material/edit/${material.id}`)

    const deleteMaterial = async id => {
      await ElMessageBox.confirm('确定要删除该材料吗？', '提示', { type: 'warning' })
      await deleteMaterialApi(id)
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
      business_license: '营业执照',
      drug_license: '药品经营许可证',
      gsp_certificate: 'GSP证书'
    }[type] || type)
    const typeTag = type => type === 'business_license' ? 'primary' : type === 'drug_license' ? 'success' : 'warning'

    onMounted(loadData)

    return {
      searchForm,
      materialList,
      currentPage,
      pageSize,
      total,
      search,
      reset,
      addMaterial,
      editMaterial,
      deleteMaterial,
      handleSizeChange,
      handleCurrentChange,
      typeName,
      typeTag
    }
  }
}
</script>

<style scoped>
.material-list {
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
