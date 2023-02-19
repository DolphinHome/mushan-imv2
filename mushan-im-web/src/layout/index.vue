<template>
    <div class="home">
        <div class="home-cont">
            <div class="home-cont-left">
                <div class="home-cont-left-top">
                    <img :src="url" class="home-cont-left-img">
                    <router-link tag="div" to="/list"  class="home-icon" replace>
                    <el-icon size="25" color="#9F9D99">
                        <ChatDotRound />
                    </el-icon>
                    </router-link>
                    <router-link  tag="div" to="/add" class="home-icon">
                    <el-icon size="25" color="#9F9D99">
                        <User />
                    </el-icon>
                    </router-link>
                </div>
                <div class="home-cont-left-boom" @click="loginout">
                    <el-icon size="25"><SwitchButton /></el-icon>
                </div>
            </div>
            <div class="home-cont-right">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
    import { useRouter } from 'vue-router'
    import { onMounted,reactive,ref } from 'vue'
    import {getUser} from "../api/user";

    const router = useRouter();
    const url = ref('');

    const loginout = ()=>{
        localStorage.removeItem("mushan-token");
        localStorage.removeItem("im-userid")
        router.replace("/login");
    }

    const details = ()=>{
        getUser().then(res=>{
          url.value = res.data.image
          localStorage.setItem("im-userid",res.data.id)
        })
    }

    onMounted(()=>{
        details()
    })


</script>

<style scoped>
    .home{
        width: 100vw;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .home-cont{
        border-radius: 10px;
        width: 850px;
        height: 650px;
        background: #FFFFFF;
        display: flex;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }
    .home-cont-left{
        flex: 1;
        border-right: 1px solid #eeeeee;
        background-color: rgb(247,247,247);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
        padding-bottom: 10px;
    }
    .home-cont-right{
        flex: 9;
    }
    .home-cont-left-img{
        border-radius: 5px;
        margin-top: 20px;
        width: 50px;
        height: 50px;
    }
    .home-cont-left-top{
        display: flex;
        flex-direction: column;
        align-items: center;
        height: 200px;
    }
    .home-icon{
        margin-top: 10px;
        cursor:pointer;
        flex: 1;
    }
    .router-link-active i {
        color: red;
    }

</style>
