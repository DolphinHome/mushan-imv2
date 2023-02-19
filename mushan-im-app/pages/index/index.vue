<template>
	<view class="u-page">
		<u-list>
			<u-list-item v-for="(item, index) in list" :key="index">
				<u-cell :title="item.username" @click="tochart(item)">
					<u-avatar slot="icon" shape="square" size="35" :src="item.image"
						customStyle="margin: -3px 5px -3px 0"></u-avatar>
				</u-cell>
			</u-list-item>
		</u-list>
	</view>
</template>

<script>
	import {getUser} from "../../api/user/index.js"
	import {getlist} from "../../api/friend/index.js"
	export default {
		data() {
			return {
				list: []
			}
		},
		onLoad() {
			//获取用户id 进行websocket链接
			getUser().then(res => {
				let msg = {
					uid: res.data.id,
					type: 1
				}
				this.$store.dispatch('websocketSend', msg);
				uni.setStorage({
					key: 'im-userid',
					data: res.data.id,
				});
				
			}).then(() => {
				getlist().then(res => {
					this.list = res.data
				})
			})


		},
		methods: {

			tochart(e) {
				uni.navigateTo({
					url: '/pages/chat/chat?uid=' + e.id
				});
			}

		},
	}
</script>
