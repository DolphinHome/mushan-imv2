<template>
    {{uid}}{{tid}}
    <el-button v-if="isaccept" @click="accept">接受</el-button>
    <video ref="v1" autoplay playsinline style="width: 300px;height: 300px"></video>
    <video ref="v2" autoplay playsinline style="width: 300px;height: 300px"></video>
</template>

<script  lang="ts" setup>
    import { onMounted,reactive,ref } from 'vue'
    import { useRoute,useRouter } from 'vue-router'
    import {websocketStore} from "../../store/websokcet.js";
    import socket from "../../utils/websocket.ts";

    const web = websocketStore();
    let route = useRoute();
    const v1 = ref();
    const v2 = ref();
    const pc = ref(null);
    const mystrea = ref();
    // const uid = localStorage.getItem("im-userid");
    // const tid = route.query.from;
    const uid = ref();
    const tid = ref();
    const isaccept = ref(false);



    const accept = ()=>{

        socket.send({uid:uid.value,to:tid.value,type:4})
    }

    const loginWebsocket = ()=>{
         Promise.resolve().then(()=>{
             socket.init()
         }).then(()=>{
           //  uid.value = localStorage.getItem("im-userid")
             if (route.query.type == 1){
                  uid.value =  localStorage.getItem("im-userid")
                  tid.value = route.query.from
             }else {
                 uid.value =  localStorage.getItem("im-userid")
                 tid.value = route.query.from
                 isaccept.value = true
             }
             socket.send({uid:uid.value,type:1})
         }).then(()=>{
             if (route.query.type == 1){
                 socket.send({uid:uid.value,to:tid.value,type:3})
             }
         })
    }







    web.$subscribe((mutations, state) => {
        let msg = JSON.parse(state.msg);
        switch (msg.type) {
            case 4:
                sxt().then(res=>{
                    socket.send({uid:uid.value,to:tid.value,type:5})
                })
                break;
            case 5:
                sxt().then(res=>{
                    if (pc.value == null){
                        createPeerConnection();
                    }

                    pc.value.createOffer(createOfferAndSendMessage,handleCreateOfferError)
                })
                break;
            case 6:
                if (pc.value == null) {
                    createPeerConnection()
                }
                console.log(msg.message)
                pc.value.setRemoteDescription(new RTCSessionDescription(msg.message));
                doAnswer()
                break;
            case 7:
                pc.value.setRemoteDescription(new RTCSessionDescription(msg.message));
                break;
            case 8:

                console.log("aaa")
                var candidate = new RTCIceCandidate({
                    sdpMLineIndex: msg.message.sdpMLineIndex,
                    candidate: msg.message.candidate
                });
                pc.value.addIceCandidate(candidate)

                break;
        }
    });


    const handleCreateOfferError = ()=>{
        console.log("aa")
    }


    const createOfferAndSendMessage = (sessionDescription)=>{
        pc.value.setLocalDescription(sessionDescription)
        socket.send({uid:uid.value,to:tid.value,type:6,message:sessionDescription})
    }


    const doAnswer = ()=>{
        if (pc.value == null) {
            createPeerConnection()
        }
        pc.value.createAnswer().then(createAnswerAndSendMessage, handleCreateAnswerError);
    }

    const handleCreateAnswerError = ()=>{
        console.log("bbb")
    }


    const createAnswerAndSendMessage = (sessionDescription)=>{
        pc.value.setLocalDescription(sessionDescription)
        socket.send({uid:uid.value,to:tid.value,type:7,message:sessionDescription})
       // ws.value.send(JSON.stringify({type:"4",uid:uid.value,to:rid.value,message:sessionDescription}))
    }



    const createPeerConnection = ()=>{
        pc.value = new RTCPeerConnection(null);
        pc.value.onicecandidate = handleIceCandidate;
        pc.value.onaddstream = handleRemoteStreamAdded;
        for (const trac of mystrea.value.getTracks()){
            pc.value.addTrack(trac,mystrea.value);
        }
    }

    const handleRemoteStreamAdded = (event)=>{
        console.log(event)
        v2.value.srcObject = event.stream;
    }


    const handleIceCandidate = (event)=>{
        if (event.candidate){
            socket.send({uid:uid.value,to:tid.value,type:8,message:event.candidate})
        }
    }



    //开启摄像头
    const sxt = ()=>{
        return new Promise((resolve,reject)=>{
            navigator.mediaDevices.getUserMedia({
                audio: true,
                video: true,
            }).then(function (value) {
                mystrea.value = value
                v1.value.srcObject = value  // 自己的流
            }).then(()=>resolve())
                .catch(()=>reject());
        })
    }



    onMounted(()=>{
        loginWebsocket();
    })
</script>

<style scoped>

</style>
