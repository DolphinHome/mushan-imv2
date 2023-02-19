import request from '../../utils/request.js'


export function getlist() {
    return request({
        url: '/friend/list',
        method: 'get',
    })
}
