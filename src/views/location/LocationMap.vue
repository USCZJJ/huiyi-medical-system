<template>
  <div class="location-map">
    <h2>销售地点地图</h2>
    <el-card class="map-card">
      <div class="map-container">
        <div class="map-placeholder">
          <h3>销售地点分布</h3>
          <p>下方地点数据来自后端销售地点接口</p>
          <div class="map-markers">
            <div v-for="location in locationList" :key="location.id" class="map-marker">
              <el-tag>{{ location.locationName }}</el-tag>
              <p>{{ location.city }} · {{ location.address }}</p>
              <small>{{ location.contact }} {{ location.phone }}</small>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { getLocationList } from '../../api/location'

export default {
  name: 'LocationMap',
  setup() {
    const locationList = ref([])

    const loadData = async () => {
      const res = await getLocationList({ page: 1, pageSize: 100 })
      locationList.value = res.data?.list || []
    }

    onMounted(loadData)

    return { locationList }
  }
}
</script>

<style scoped>
.location-map {
  padding: 20px 0;
}

h2 {
  margin-bottom: 20px;
  color: #1f2937;
}

.map-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.map-container {
  min-height: 600px;
}

.map-placeholder {
  min-height: 600px;
  background-color: #f0f2f5;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 30px;
}

.map-placeholder h3 {
  color: #1f2937;
  margin-bottom: 10px;
}

.map-placeholder p {
  color: #6b7280;
  margin-bottom: 20px;
}

.map-markers {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
  justify-content: center;
}

.map-marker {
  background-color: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  max-width: 240px;
}

.map-marker p {
  margin: 10px 0 6px;
  font-size: 14px;
}
</style>
