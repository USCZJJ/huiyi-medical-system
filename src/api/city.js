import request from '../utils/request'

/**
 * 获取城市列表
 */
export function getCityList(params) {
  return request({
    url: '/cities',
    method: 'get',
    params
  })
}

/**
 * 获取城市详情
 */
export function getCity(id) {
  return request({
    url: `/cities/${id}`,
    method: 'get'
  })
}

/**
 * 创建城市
 */
export function createCity(data) {
  return request({
    url: '/cities',
    method: 'post',
    data
  })
}

/**
 * 更新城市
 */
export function updateCity(id, data) {
  return request({
    url: `/cities/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除城市
 */
export function deleteCity(id) {
  return request({
    url: `/cities/${id}`,
    method: 'delete'
  })
}
