import request from '../utils/request'

/**
 * 用户登录
 * @param {Object} data - 登录信息
 * @returns {Promise}
 */
export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

/**
 * 获取当前用户信息
 * @returns {Promise}
 */
export function getCurrentUser() {
  return request({
    url: '/users/me',
    method: 'get'
  })
}

/**
 * 获取用户列表
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
export function getUserList(params) {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

/**
 * 创建用户
 * @param {Object} data - 用户信息
 * @returns {Promise}
 */
export function createUser(data) {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

/**
 * 更新用户
 * @param {number} id - 用户 ID
 * @param {Object} data - 用户信息
 * @returns {Promise}
 */
export function updateUser(id, data) {
  return request({
    url: `/users/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除用户
 * @param {number} id - 用户 ID
 * @returns {Promise}
 */
export function deleteUser(id) {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

export function getPermissionList() {
  return request({
    url: '/permissions',
    method: 'get'
  })
}

export function getUserPermissions(id) {
  return request({
    url: `/users/${id}/permissions`,
    method: 'get'
  })
}

export function updateUserPermissions(id, permissions) {
  return request({
    url: `/users/${id}/permissions`,
    method: 'put',
    data: { permissions }
  })
}

export function changePassword(data) {
  return request({
    url: '/users/me/password',
    method: 'put',
    data
  })
}

export function resetUserPassword(id, newPassword = 'admin123') {
  return request({
    url: `/users/${id}/password/reset`,
    method: 'put',
    data: { newPassword }
  })
}
