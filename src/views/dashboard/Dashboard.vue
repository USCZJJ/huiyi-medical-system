<template>
  <div class="dashboard">
    <div class="welcome-section">
      <div class="welcome-content">
        <h1 class="welcome-title">欢迎回来，{{ userInfo.name || userInfo.username || '管理员' }}！</h1>
        <p class="welcome-subtitle">今天是 {{ currentDate }}，祝您工作愉快！</p>
      </div>
      <div class="welcome-stats">
        <div v-for="item in statItems" :key="item.key" class="stat-item">
          <div class="stat-value">{{ item.value }}</div>
          <div class="stat-label">{{ item.label }}</div>
        </div>
      </div>
    </div>

    <div class="stats-container">
      <el-card v-for="item in statCards" :key="item.path" class="stat-card" @click="navigateTo(item.path)">
        <div class="stat-content">
          <div class="stat-icon" :class="item.className">
            <el-icon class="stat-icon-el"><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-number">{{ item.value }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </div>
      </el-card>
    </div>

    <div class="charts-container">
      <el-card class="chart-card">
        <template #header>
          <div class="chart-header">
            <h3 class="card-title">医生职级分布</h3>
          </div>
        </template>
        <div class="chart-wrapper">
          <v-chart :option="barOption" style="width: 100%; height: 100%" />
        </div>
      </el-card>

      <el-card class="chart-card">
        <template #header>
          <div class="chart-header">
            <h3 class="card-title">医院科室分布</h3>
          </div>
        </template>
        <div class="chart-wrapper">
          <v-chart :option="pieOption" style="width: 100%; height: 450px" />
        </div>
      </el-card>
    </div>

    <el-card class="policy-card">
      <template #header>
        <div class="card-header">
          <h3 class="card-title">最新政策</h3>
        </div>
      </template>
      <el-table :data="latestPolicies" style="width: 100%" stripe border>
        <el-table-column prop="policyName" label="政策名称" min-width="200" />
        <el-table-column prop="companyName" label="所属公司" width="200" />
        <el-table-column prop="effectiveDate" label="生效日期" width="150" />
        <el-table-column prop="expiryDate" label="到期日期" width="150">
          <template #default="scope">
            <span :class="{ 'expiry-soon': isExpirySoon(scope.row.expiryDate) }">{{ scope.row.expiryDate }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { OfficeBuilding, Files, Location, Position, Warning, UserFilled } from '@element-plus/icons-vue'
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
import { BarChart, PieChart } from 'echarts/charts'
import { TooltipComponent, GridComponent, LegendComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import { getDashboard } from '../../api/dashboard'

use([TooltipComponent, GridComponent, LegendComponent, BarChart, PieChart, CanvasRenderer])

export default {
  name: 'Dashboard',
  components: { OfficeBuilding, Files, Location, Position, Warning, UserFilled, VChart },
  setup() {
    const router = useRouter()
    const stats = reactive({
      companyCount: 0,
      materialCount: 0,
      cityCount: 0,
      locationCount: 0,
      medicineCount: 0,
      doctorCount: 0
    })
    const latestPolicies = ref([])
    const titleDistribution = ref([])
    const departmentDistribution = ref([])
    const userInfo = ref({})

    const currentDate = computed(() => {
      const date = new Date()
      return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
    })

    const statItems = computed(() => [
      { key: 'doctor', label: '注册医师', value: stats.doctorCount },
      { key: 'company', label: '合作公司', value: stats.companyCount },
      { key: 'medicine', label: '药品种类', value: stats.medicineCount },
      { key: 'material', label: '必备材料', value: stats.materialCount },
      { key: 'city', label: '城市信息', value: stats.cityCount },
      { key: 'location', label: '销售地点', value: stats.locationCount }
    ])

    const statCards = computed(() => [
      { label: '医药公司', value: stats.companyCount, path: '/company', icon: 'OfficeBuilding', className: 'stat-icon-blue' },
      { label: '必备材料', value: stats.materialCount, path: '/material', icon: 'Files', className: 'stat-icon-green' },
      { label: '城市信息', value: stats.cityCount, path: '/city', icon: 'Location', className: 'stat-icon-orange' },
      { label: '销售地点', value: stats.locationCount, path: '/location', icon: 'Position', className: 'stat-icon-purple' },
      { label: '药品信息', value: stats.medicineCount, path: '/medicine', icon: 'Warning', className: 'stat-icon-red' },
      { label: '医生管理', value: stats.doctorCount, path: '/doctor', icon: 'UserFilled', className: 'stat-icon-teal' }
    ])

    const barOption = computed(() => ({
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
      grid: { left: '10%', right: '10%', bottom: '14%', top: '10%', containLabel: true },
      xAxis: { type: 'category', data: titleDistribution.value.map(item => item.name), axisTick: { show: false } },
      yAxis: { type: 'value' },
      series: [{
        name: '人数',
        type: 'bar',
        barWidth: '55%',
        data: titleDistribution.value.map((item, index) => ({
          value: item.value,
          itemStyle: { color: ['#10b981', '#3b82f6', '#f59e0b', '#8b5cf6', '#ef4444'][index % 5], borderRadius: [4, 4, 0, 0] }
        }))
      }]
    }))

    const pieOption = computed(() => ({
      tooltip: { trigger: 'item' },
      legend: { bottom: '2%', left: 'center' },
      series: [{
        name: '科室分布',
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '45%'],
        data: departmentDistribution.value,
        label: { show: false },
        labelLine: { show: false }
      }]
    }))

    const loadUserInfo = () => {
      const userStr = localStorage.getItem('user')
      userInfo.value = userStr ? JSON.parse(userStr) : {}
    }

    const loadDashboard = async () => {
      const res = await getDashboard()
      const data = res.data || {}
      Object.assign(stats, {
        companyCount: data.companyCount || 0,
        materialCount: data.materialCount || 0,
        cityCount: data.cityCount || 0,
        locationCount: data.locationCount || 0,
        medicineCount: data.medicineCount || 0,
        doctorCount: data.doctorCount || 0
      })
      titleDistribution.value = data.doctorTitleDistribution || []
      departmentDistribution.value = data.departmentDistribution || []
      latestPolicies.value = data.latestPolicies || []
    }

    const isExpirySoon = expiryDate => {
      const diffDays = Math.ceil((new Date(expiryDate) - new Date()) / (1000 * 60 * 60 * 24))
      return diffDays <= 30
    }
    const navigateTo = path => router.push(path)

    onMounted(() => {
      loadUserInfo()
      loadDashboard()
    })

    return {
      stats,
      statItems,
      statCards,
      barOption,
      pieOption,
      latestPolicies,
      userInfo,
      currentDate,
      navigateTo,
      isExpirySoon
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.welcome-section {
  background: linear-gradient(135deg, #3b82f6, #6366f1);
  border-radius: 16px;
  padding: 32px;
  margin-bottom: 30px;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 16px rgba(59, 130, 246, 0.3);
}

.welcome-title {
  font-size: 28px;
  font-weight: bold;
  margin: 0 0 8px 0;
}

.welcome-subtitle {
  font-size: 16px;
  margin: 0;
  color: #e0e7ff;
}

.welcome-stats {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  justify-content: flex-end;
}

.stat-item {
  text-align: center;
  padding: 16px 24px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #ffffff;
}

.stat-label {
  font-size: 14px;
  color: #e0e7ff;
  font-weight: 500;
}

.stats-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  cursor: pointer;
}

.stat-content {
  display: flex;
  align-items: center;
  padding: 24px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
}

.stat-icon-el {
  font-size: 28px;
}

.stat-icon-blue { background-color: rgba(59, 130, 246, 0.1); color: #3b82f6; }
.stat-icon-green { background-color: rgba(16, 185, 129, 0.1); color: #10b981; }
.stat-icon-orange { background-color: rgba(245, 158, 11, 0.1); color: #f59e0b; }
.stat-icon-purple { background-color: rgba(139, 92, 246, 0.1); color: #8b5cf6; }
.stat-icon-red { background-color: rgba(239, 68, 68, 0.1); color: #ef4444; }
.stat-icon-teal { background-color: rgba(14, 165, 233, 0.1); color: #0ea5e9; }

.stat-number {
  font-size: 36px;
  font-weight: bold;
  color: #1f2937;
}

.charts-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-bottom: 30px;
}

.chart-card,
.policy-card {
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.chart-header,
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
  margin: 0;
}

.chart-wrapper {
  min-height: 450px;
  width: 100%;
  height: 100%;
  padding: 20px;
}

.expiry-soon {
  color: #ef4444;
  font-weight: 600;
}

@media (max-width: 1200px) {
  .welcome-section {
    flex-direction: column;
    text-align: center;
    gap: 24px;
  }

  .charts-container {
    grid-template-columns: 1fr;
  }
}
</style>
