const BASE_URL = 'http://192.168.1.12:8080'


const request = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			header:  {
				"Authorization": uni.getStorageSync("token")
			}
		}).then(response => {
			let data = response[1].data;
			if (data.errno == 0) {
				resolve(data)
			} else if (data.errno == 501) {
				
				uni.$u.toast('请重新登录')
				uni.redirectTo({
					url: '/pages/login/login'
				})
			} else {
				
				uni.$u.toast(data.errmsg)
				return
			}
		}).catch(err => {
			reject(err)
		})
	})
}

export default request
