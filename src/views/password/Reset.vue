<template>
  <div class="reset-password">
    <h2>重置密码</h2>
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
          <span>用户列表</span>
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
        <el-table-column label="操作" width="140">
          <template #default="scope">
            <el-button type="primary" size="small" @click="resetPassword(scope.row)">重置密码</el-button>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, resetUserPassword } from '../../api/user'

export default {
  name: 'Reset',
  setup() {
    const searchForm = ref({ username: '' })
    const userList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    const loadData = async () => {
      const res = await getUserList({
        username: searchForm.value.username,
        page: currentPage.value,
        pageSize: pageSize.value
      })
      userList.value = res.data?.list || []
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

    const resetPassword = async user => {
      const { value } = await ElMessageBox.prompt(`请输入 ${user.username} 的新密码`, '重置密码', {
        inputValue: 'admin123',
        inputPattern: /^.{6,}$/,
        inputErrorMessage: '密码长度至少6位'
      })
      await resetUserPassword(user.id, value)
      ElMessage.success('密码重置成功')
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
      search,
      reset,
      resetPassword,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.reset-password {
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
