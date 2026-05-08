import request from '../utils/request'

/**
 * 获取地点列表
 */
export function getLocationList(params) {
  return request({
    url: '/locations',
    method: 'get',
    params
  })
}

/**
 * 获取地点详情
 */
export function getLocation(id) {
  return request({
    url: `/locations/${id}`,
    method: 'get'
  })
}

/**
 * 创建地点
 */
export function createLocation(data) {
  return request({
    url: '/locations',
    method: 'post',
    data
  })
}

/**
 * 更新地点
 */
export function updateLocation(id, data) {
  return request({
    url: `/locations/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除地点
 */
export function deleteLocation(id) {
  return request({
    url: `/locations/${id}`,
    method: 'delete'
  })
}
