<template>
	<view class="cent">
		<h3>登录</h3>
		<u--form :model="form" ref="uForm" :rules="rules">
			<u-form-item label="用户名" prop="username" label-width="130">
				<u-input v-model="form.username" placeholder="请输入用户名" />
			</u-form-item>

			<u-form-item label="密码" prop="password" label-width="130">
				<u-input v-model="form.password" type="password" placeholder="请输入密码" />
			</u-form-item>
			<u-form-item>
				<u-button type="primary" text="确定" @click="submit"></u-button>
			</u-form-item>
		</u--form>
	</view>
</template>

<script>
	import {
		login
	} from "../../api/login/index.js"
	export default {
		data() {
			return {
				form: {
					username: '',
					password: '',
				},
				loading: true,
				rules: {
					username: [{
						required: true,
						message: '请输入用户名',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}, ],

					password: [{
						required: true,
						message: '请输入密码',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}, ],
				}

			}
		},
		onLoad() {
			this.$store.dispatch('init', "ws://192.168.1.12:8004/ws");
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			setToken(token) {
				return new Promise((resolve, reject) => {
					uni.setStorageSync('token', token);
					resolve()
				})
			},

			submit() {
				this.$refs.uForm.validate().then(res => {
					login(this.form).then(val => {
						this.setToken(val.data).then(() => {
							uni.redirectTo({
								url: '/pages/index/index'
							})
						})


					})
				}).catch(err => {
					console.log("bb")
				})
			}



		}
	}
</script>

<style scoped>
	.cent {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		width: 100vw;
		height: 100vh;
	}
</style>
