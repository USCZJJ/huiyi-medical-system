<template>
  <div class="user-list">
    <h2>用户列表</h2>
    <el-card class="filter-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="searchForm.role" placeholder="请选择角色" clearable>
            <el-option label="管理员" value="admin" />
            <el-option label="普通用户" value="user" />
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
          <span>用户列表</span>
          <el-button type="primary" @click="openCreateDialog">添加用户</el-button>
        </div>
      </template>
      <el-table :data="userList" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="role" label="角色" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.role === 'admin' ? 'primary' : 'success'">
              {{ scope.row.role === 'admin' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditDialog(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="removeUser(scope.row.id)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="editingId ? '编辑用户' : '添加用户'" width="480px">
      <el-form ref="userFormRef" :model="userForm" :rules="rules" label-width="90px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userForm.name" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" style="width: 100%">
            <el-option label="管理员" value="admin" />
            <el-option label="普通用户" value="user" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="!editingId" label="密码" prop="password">
          <el-input v-model="userForm.password" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUser">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, createUser, updateUser, deleteUser } from '../../api/user'

export default {
  name: 'UserList',
  setup() {
    const searchForm = ref({ username: '', role: '' })
    const userList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const dialogVisible = ref(false)
    const editingId = ref(null)
    const userFormRef = ref(null)
    const userForm = ref({ username: '', name: '', role: 'user', password: 'admin123' })
    const rules = ref({
      username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
      name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      role: [{ required: true, message: '请选择角色', trigger: 'change' }],
      password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
    })

    const loadData = async () => {
      const res = await getUserList({ ...searchForm.value, page: currentPage.value, pageSize: pageSize.value })
      userList.value = res.data?.list || []
      total.value = res.data?.total || 0
    }

    const search = () => {
      currentPage.value = 1
      loadData()
    }
    const reset = () => {
      searchForm.value = { username: '', role: '' }
      currentPage.value = 1
      loadData()
    }

    const openCreateDialog = () => {
      editingId.value = null
      userForm.value = { username: '', name: '', role: 'user', password: 'admin123' }
      dialogVisible.value = true
    }

    const openEditDialog = user => {
      editingId.value = user.id
      userForm.value = { username: user.username, name: user.name, role: user.role, password: '' }
      dialogVisible.value = true
    }

    const submitUser = async () => {
      const valid = await userFormRef.value.validate()
      if (!valid) return

      if (editingId.value) {
        await updateUser(editingId.value, userForm.value)
        ElMessage.success('更新成功')
      } else {
        await createUser(userForm.value)
        ElMessage.success('添加成功')
      }
      dialogVisible.value = false
      loadData()
    }

    const removeUser = async id => {
      await ElMessageBox.confirm('确定要删除该用户吗？', '提示', { type: 'warning' })
      await deleteUser(id)
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

    onMounted(loadData)

    return {
      searchForm,
      userList,
      currentPage,
      pageSize,
      total,
      dialogVisible,
      editingId,
      userFormRef,
      userForm,
      rules,
      search,
      reset,
      openCreateDialog,
      openEditDialog,
      submitUser,
      removeUser,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.user-list {
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
</style>
