<template>
    <div class="chat">
        <h3 align="center">{{title}}</h3>
        <el-scrollbar  ref="scrollbarRef" @scroll="scroll">
        <div class="chat-list" v-for="(item,index) in arr.list" :key="index">
                        <div :class="[item.from == query.to?'chat-list-left':'chat-list-right']">
                            <img :src="item.image" class="list-img">
                            <div :class="item.from == query.to?'left-msg':'right-msg'">{{item.content}}</div>
                        </div>

<!--            <div class="chat-list-left">-->
<!--                <img src="https://cdn.uviewui.com/uview/album/2.jpg" class="list-img">-->
<!--                <div class="left-msg">啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊</div>-->
<!--            </div>-->
<!--            <div class="chat-list-right">-->
<!--                <img src="https://cdn.uviewui.com/uview/album/2.jpg" class="list-img">-->
<!--                <div class="right-msg">3123123123eijfewijewifjeiowjfiowejfoweijfeowijeoifjewoijfeiowjfeowijfeiojfewoijeiofjeefjweiojfeoi</div>-->
<!--            </div>-->
        </div>
        </el-scrollbar>
        <div class="chat-boom">
            <el-input
                    class="textarea"
                    type="textarea"
                    :rows="7"
                    placeholder="Please input"
                    v-model="text"
            />
            <el-button type="primary" @click="call">视频通话</el-button>
            <el-button type="primary" @click="send">发送</el-button>
        </div>
    </div>
</template>

<script lang="ts" setup>
    import { onMounted,reactive,ref,onBeforeUnmount } from 'vue'
    import { useRoute,useRouter } from 'vue-router'
    import { onBeforeRouteUpdate } from "vue-router";
    import {list,addMessage} from "../../api/message";
    import {websocketStore} from "../../store/websokcet.js";
    import socket from "../../utils/websocket.ts";



    let route = useRoute();
    const scrollbarRef = ref<InstanceType<typeof ElScrollbar>>()
    const innerRef = ref<HTMLDivElement>()
    const query = reactive({to:'',pageNum:1,pageSize:10})
    let total = ref('');
    let arr = reactive({list:[]});
    let text = ref('');
    const web = websocketStore();
    const title = ref('');
    const router = useRouter();


    web.$subscribe((mutations, state) => {
       let msg = JSON.parse(state.msg);
       if (msg.type == 2){
           getlist();
       }
    });

    const loginWebsocket = ()=>{
        Promise.resolve().then(()=>{
            socket.init()
        }).then(()=>{
            socket.send({uid:localStorage.getItem("im-userid"),type:1})
        })
    }
    const send =()=>{
        addMessage({to:query.to,content:text.value}).then(res=>{
            getlist();
        }).then(()=>{
            socket.send({from:localStorage.getItem("im-userid"),to:query.to,message:text.value,type:2})
        })
    }
    const call = ()=>{
        router.push({path:"call",query:{from:query.to,type:1}}) // type 1 为开始请求通话  2 为回应通话
    }

    onBeforeRouteUpdate((to)=>{
        query.to = to.query.uid
        getlist();
    })

    const getuid = ()=>{
        query.to = route.query.uid
        getlist();
    }





    const getlist = ()=>{
        list(query).then(res=>{
          let data =  res.data;
          arr.list = data.list;
          scrollbarRef.value!.setScrollTop(2200)
        })
    }


    onMounted(()=>{
        getuid();
        loginWebsocket();
    })

</script>

<style scoped>
    .chat{
        background: rgb(247,247,247);
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }
    .chat-boom{
        display: flex;
        justify-content: space-between;
        align-items: end;
    }
    .list-img{
        width: 40px;
        height: 40px;
        border-radius: 5px;
    }
    .chat-list{
        display: flex;
        flex-direction: column;
        height: 100%;
    }
    .chat-list-left{
        padding: 20px;
        display: flex;
        flex-direction: row;
    }
    .chat-list-right{
        padding: 20px;
        display: flex;
        flex-direction: row-reverse;
    }
    .left-msg{
        margin-left: 5px;
        background-color: #ffffff;
        font-size: 15px;
        border-radius: 6px;
        max-width: 200px;
        word-wrap:break-word
    }
    .right-msg{
        margin-right: 7px;
        background-color: #ffffff;
        font-size: 15px;
        border-radius: 6px;
        max-width: 200px;
        word-wrap:break-word
    }
</style>
