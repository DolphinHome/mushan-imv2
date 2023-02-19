<template>
    <div class="cent">
       <div class="cent-top"><el-input v-model="query.username" /><el-button @click="getUserTop">搜索</el-button></div>

        <div class="userlist-left">
            <el-scrollbar height="200px">
                <div class="left-list" v-if="userTop.id">
                    <img :src="userTop.image" class="left-list-img">
                    <span class="left-list-title">{{userTop.username}}</span>
                    <el-button lass="left-list-bu" @click="add(userTop.id)">添加</el-button>
                </div>
            </el-scrollbar>
        </div>

        <el-divider />

        <div class="userlist-left">
            <el-scrollbar height="300px">
                <div class="left-list" v-for="(item,index) in list.arr" >
                    <img :src="item.image" class="left-list-img">
                    <span class="left-list-title">{{item.username}}</span>
                    <div lass="left-list-bu">
                        <el-button @click="accept(item.id)">接受</el-button>
                        <el-button>拒绝</el-button>
                    </div>
                </div>
            </el-scrollbar>
        </div>

    </div>
</template>

<script lang="ts" setup>
    import { onMounted,reactive,ref } from 'vue'
    import {getUserByUserName} from "../../api/user";
    import {addApply,getfriends,agree} from "../../api/apply";
    import { ElMessage } from 'element-plus'
    import socket from "../../utils/websocket";

    let query = reactive({
        username:""
    });
    let userTop = reactive({});
    let list = reactive({arr:[]});

    const add = (e)=>{
        addApply({tid:e}).then(res=>{
            ElMessage({
                message: '成功',
                type: 'success',
            })
        })
    }

    const accept = (e)=>{
        agree({fid:e}).then(res=>{
            ElMessage({
                message: '成功',
                type: 'success',
            })
        })

    }

    const getUserTop = ()=>{
        getUserByUserName(query).then(res=>{
            if (res.data){
                userTop.id = res.data.id;
                userTop.username = res.data.username;
                userTop.image = res.data.image;
            }
        })
    }

    onMounted(()=>{
        getfriends().then(res=>{
            list.arr = res.data
        })
    })
</script>

<style scoped>
    .cent{

        padding: 20px;
        display: flex;
        justify-content: center;
        flex-direction: column;
    }
    .cent-top{
        display: flex;
    }
    .userlist-left{
        display: flex;
        flex-direction: column;
    }



    .left-list{
        display: grid;
        grid-template-columns: repeat(3,1fr);
        grid-template-rows:repeat(1,1fr);
        grid-template-areas: 'a b c'
        'a b c';
        justify-items: center;
        align-items:center;
        margin-top: 10px;
    }

    .left-list-img{
        width: 50px;
        height: 50px;
        border-radius: 5px;
        grid-area:a
    }
    .left-list-title{
        grid-area:b
    }

</style>
