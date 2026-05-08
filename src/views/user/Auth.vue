<template>
  <div class="auth-page">
    <h2>用户授权</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" />
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
          <span>用户权限列表</span>
        </div>
      </template>
      <el-table :data="userAuthList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="permissions" label="权限">
          <template #default="scope">
            <el-tag
              v-for="perm in scope.row.permissions"
              :key="perm"
              size="small"
              style="margin-right: 5px; margin-bottom: 5px"
            >
              {{ permissionName(perm) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="140">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editAuth(scope.row)">编辑权限</el-button>
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

    <el-dialog v-model="dialogVisible" title="编辑权限" width="620px">
      <el-checkbox-group v-model="checkedPermissions">
        <div class="permission-grid">
          <el-checkbox
            v-for="permission in permissionList"
            :key="permission.permissionCode"
            :label="permission.permissionCode"
          >
            {{ permission.permissionName }}
          </el-checkbox>
        </div>
      </el-checkbox-group>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAuth">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { computed, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserList, getPermissionList, getUserPermissions, updateUserPermissions } from '../../api/user'

export default {
  name: 'Auth',
  setup() {
    const searchForm = ref({ username: '' })
    const userAuthList = ref([])
    const permissionList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const dialogVisible = ref(false)
    const currentUser = ref(null)
    const checkedPermissions = ref([])

    const permissionMap = computed(() => {
      return permissionList.value.reduce((map, item) => {
        map[item.permissionCode] = item.permissionName
        return map
      }, {})
    })

    const permissionName = code => permissionMap.value[code] || code

    const loadPermissions = async () => {
      const res = await getPermissionList()
      permissionList.value = res.data || []
    }

    const loadData = async () => {
      const res = await getUserList({
        username: searchForm.value.username,
        page: currentPage.value,
        pageSize: pageSize.value
      })
      userAuthList.value = res.data?.list || []
      total.value = res.data?.total || 0
    }

    const search = () => {
      currentPage.value = 1
      loadData()
    }
    const reset = () => {
      searchForm.value = { username: '' }
      currentPage.value = 1
      loadData()
    }

    const editAuth = async user => {
      currentUser.value = user
      const res = await getUserPermissions(user.id)
      checkedPermissions.value = res.data || []
      dialogVisible.value = true
    }

    const saveAuth = async () => {
      await updateUserPermissions(currentUser.value.id, checkedPermissions.value)
      ElMessage.success('权限保存成功')
      dialogVisible.value = false
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

    onMounted(async () => {
      await loadPermissions()
      await loadData()
    })

    return {
      searchForm,
      userAuthList,
      permissionList,
      currentPage,
      pageSize,
      total,
      dialogVisible,
      checkedPermissions,
      search,
      reset,
      editAuth,
      saveAuth,
      handleSizeChange,
      handleCurrentChange,
      permissionName
    }
  }
}
</script>

<style scoped>
.auth-page {
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

.permission-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px 20px;
}
</style>
