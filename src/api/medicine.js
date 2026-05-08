import request from '../utils/request'

/**
 * 获取药品列表
 */
export function getMedicineList(params) {
  return request({
    url: '/medicines',
    method: 'get',
    params
  })
}

/**
 * 获取药品详情
 */
export function getMedicine(id) {
  return request({
    url: `/medicines/${id}`,
    method: 'get'
  })
}

/**
 * 创建药品
 */
export function createMedicine(data) {
  return request({
    url: '/medicines',
    method: 'post',
    data
  })
}

/**
 * 更新药品
 */
export function updateMedicine(id, data) {
  return request({
    url: `/medicines/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除药品
 */
export function deleteMedicine(id) {
  return request({
    url: `/medicines/${id}`,
    method: 'delete'
  })
}
