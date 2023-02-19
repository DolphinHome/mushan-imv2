import request from '../../utils/request.js'

export function list(data) {
    return request({
        url: '/message/list',
        method: 'get',
        data:data,
    })
}


export function addMessage(data) {
    return request({
        url: '/message/add',
        method: 'post',
        data:data,
    })
}
