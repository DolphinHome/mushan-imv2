import request from '../../utils/request.js'


export function addApply(data) {
    return request({
        url: '/friend/add',
        method: 'post',
        data:data
    })
}


export function getfriends() {
    return request({
        url: '/friend/getFriends',
        method: 'get',
    })
}

export function agree(data) {
    return request({
        url: '/friend/agree',
        method: 'post',
        data:data
    })
}
