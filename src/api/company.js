import request from '../utils/request'

/**
 * 获取公司列表
 */
export function getCompanyList(params) {
  return request({
    url: '/companies',
    method: 'get',
    params
  })
}

/**
 * 获取公司详情
 */
export function getCompany(id) {
  return request({
    url: `/companies/${id}`,
    method: 'get'
  })
}

/**
 * 创建公司
 */
export function createCompany(data) {
  return request({
    url: '/companies',
    method: 'post',
    data
  })
}

/**
 * 更新公司
 */
export function updateCompany(id, data) {
  return request({
    url: `/companies/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除公司
 */
export function deleteCompany(id) {
  return request({
    url: `/companies/${id}`,
    method: 'delete'
  })
}
