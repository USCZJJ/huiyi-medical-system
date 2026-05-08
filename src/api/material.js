import request from '../utils/request'

/**
 * 获取材料列表
 */
export function getMaterialList(params) {
  return request({
    url: '/materials',
    method: 'get',
    params
  })
}

/**
 * 获取材料详情
 */
export function getMaterial(id) {
  return request({
    url: `/materials/${id}`,
    method: 'get'
  })
}

/**
 * 创建材料
 */
export function createMaterial(data) {
  return request({
    url: '/materials',
    method: 'post',
    data
  })
}

/**
 * 更新材料
 */
export function updateMaterial(id, data) {
  return request({
    url: `/materials/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除材料
 */
export function deleteMaterial(id) {
  return request({
    url: `/materials/${id}`,
    method: 'delete'
  })
}
