<template>
<el-button @click="ff">11</el-button>
</template>

<script lang="ts" setup>
    import socket from "../utils/websocket";
    import {onMounted} from "vue";
    import {websocketStore} from "../store/websokcet.js";

    const web = websocketStore();

    web.$subscribe((mutations, state) => {
        console.log(state.msg)
    });


    const ff = ()=>{
        socket.send({uid:localStorage.getItem("im-userid"),type:1})
    }

    const loginWebsocket = ()=>{
        socket.init()
        setTimeout(()=>{
            socket.send({uid:localStorage.getItem("im-userid"),type:1})
        },2000);
    }

    onMounted(()=>{
        loginWebsocket()

    })

</script>

<style scoped>

</style>
