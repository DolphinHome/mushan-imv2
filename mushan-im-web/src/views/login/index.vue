<template>
    <div class="count">
        <div class="login-count">
            <el-form
                    ref="ruleFormRef"
                    :model="ruleForm"
                    :rules="rules"
                    class="login-form">
                <h3 class="title">登录</h3>
                <el-form-item  prop="username">
                    <el-input class="input-with-select"  v-model="ruleForm.username" clearable>
                        <template #prefix>
                            <el-icon><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>


                <el-form-item  prop="password">
                    <el-input
                            type="password"
                            v-model="ruleForm.password"
                            class="input-with-select" clearable>
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>

                <el-form-item>
                    <el-button :loading="load" type="primary" @click="submitForm(ruleFormRef)" style="width: 100%">登录</el-button>
                </el-form-item>

            </el-form>
        </div>
    </div>
</template>

<script lang="ts" setup>
    import { onMounted,reactive,ref } from 'vue'
    import { useRouter } from 'vue-router'
    import {login} from "../../api/login";
    import type { FormInstance, FormRules } from 'element-plus'
    import { ElMessage } from 'element-plus'



    const ruleFormRef = ref<FormInstance>()
    const router = useRouter();
    const load = ref(false);
    const ruleForm = reactive({
        username:'',
        password:'',
    })
    const rules = reactive<FormRules>({
        username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' },
        ],
        password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
        ],
    })





    const submitForm = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                load.value = true;
                login(ruleForm).then(res=>{
                    localStorage.setItem("mushan-token",res.data);
                    router.replace("/");
                })
            } else {
                ElMessage.error('请填写数据')
            }
        })
    }


</script>

<style scoped>
    .count{
     width: 100vw;
     height: 100vh;
    }
    .login-count{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }
    .login-form{
        border-radius: 15px;
        margin: 180px auto;
        width: 250px;
        padding: 15px 35px 15px 35px;
        background: #ffffff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    .title{
        text-align: center;
    }
</style>
