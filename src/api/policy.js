import request from '../utils/request'

/**
 * 获取政策列表
 */
export function getPolicyList(params) {
  return request({
    url: '/policies',
    method: 'get',
    params
  })
}

/**
 * 获取政策详情
 */
export function getPolicy(id) {
  return request({
    url: `/policies/${id}`,
    method: 'get'
  })
}

/**
 * 创建政策
 */
export function createPolicy(data) {
  return request({
    url: '/policies',
    method: 'post',
    data
  })
}

/**
 * 更新政策
 */
export function updatePolicy(id, data) {
  return request({
    url: `/policies/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除政策
 */
export function deletePolicy(id) {
  return request({
    url: `/policies/${id}`,
    method: 'delete'
  })
}
