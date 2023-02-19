import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		token: '',
		socketTast: null,
		websocketData: "", // 存放从后端接收到的websocket数据
	},
	mutations: {



	},
	actions: {
		setToken(e, token) {
			this.state.token = token;
		},
		init({ state, dispatch }, url) {
			state.socketTast = uni.connectSocket({
				url, // url是websocket连接ip
				success: () => {
					console.log('websocket连接成功！')
				},
				fail: e => {
					console.log('连接失败' + e)
				}
			})

			state.socketTast.onMessage(res => {
				let data = JSON.parse(res.data)
				console.log(data)
				 if (data.type == 3){
					 uni.navigateTo({
					 	url: '/pages/call/call?from='+data.from+"&type=2"
					 });
				  }else{
					 state.websocketData = res.data 
				  }
			})

			// this.state.socketTast.onSocketMessage = (e) => {
			// 	    console.log(e)
			// 		this.state.websocketData = e.data
			// 	},
			state.socketTast.closeSocket = (e) => {
				console.log(e)
			}
		},
		websocketSend(e, data) {
			uni.sendSocketMessage({
				data: JSON.stringify(data)
			});
		}

	}

})

export default store
