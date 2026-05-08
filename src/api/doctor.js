import request from '../utils/request'

/**
 * 获取医生列表
 */
export function getDoctorList(params) {
  return request({
    url: '/doctors',
    method: 'get',
    params
  })
}

/**
 * 获取医生详情
 */
export function getDoctor(id) {
  return request({
    url: `/doctors/${id}`,
    method: 'get'
  })
}

/**
 * 创建医生
 */
export function createDoctor(data) {
  return request({
    url: '/doctors',
    method: 'post',
    data
  })
}

/**
 * 更新医生
 */
export function updateDoctor(id, data) {
  return request({
    url: `/doctors/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除医生
 */
export function deleteDoctor(id) {
  return request({
    url: `/doctors/${id}`,
    method: 'delete'
  })
}
