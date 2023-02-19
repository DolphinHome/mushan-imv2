<template>
	<view>
		<web-view class="top-bg" :src="src"></web-view>
	</view>
</template>


<script>
	export default {
		data() {
			return {
				src:'',
			}
		},
		onLoad(e) {
			let getWindowInfo = uni.getWindowInfo();
			this.src = "/hybrid/html/index.html?wid="+ getWindowInfo.screenWidth + "&hei=" + getWindowInfo.screenHeight+"&from="+e.from+"&type="+e.type+"&uid="+uni.getStorageSync("im-userid")
			plus.navigator.setFullscreen(false) //是否需要状态栏
			plus.camera.getCamera(); //二次获取摄像头，h5需要
			setTimeout(() => {
				uni.setStorageSync('auth', true)
				uni.setStorageSync('okAuto', true)
			}, 3000)
		},
		methods: {

		}
	}
</script>

<style>

</style>
