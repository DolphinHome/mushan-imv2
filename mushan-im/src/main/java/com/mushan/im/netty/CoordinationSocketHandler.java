package com.mushan.im.netty;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Configuration
@ChannelHandler.Sharable
public class CoordinationSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public Map<String, Channel> cmap = new HashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //接收的消息
        Map map = JSON.parseObject(msg.text(), Map.class);
        String type = map.get("type").toString();
        switch (type){
            case "1":   // 登录
                websocketLogin(map,ctx);
                break;
            case "2": // 发送对话消息
                send(map);
                break;
            case "3":   // 请求进行视频通话
                call(map);
                break;
            case "4":   // 对方回应通话
                response(map);
                break;
            case "5":   //传递请求off
                askoff(map);
                break;
            case "6":   //获取off
                getoff(map);
                break;
            case "7":
                answer(map);
                break;
            case "8":
                candidate(map);
                break;
        }
        System.out.println(String.format("收到客户端%s的数据：%s", ctx.channel().id(), msg.text()));
//        channelGroup.writeAndFlush(new TextWebSocketFrame("aaabbb"));
    }

    private void answer(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",7); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            obj.put("message",map.get("message"));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }

    private void candidate(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",8); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            obj.put("message",map.get("message"));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }

    private void getoff(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",6); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            obj.put("message",map.get("message"));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }


    private void askoff(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",5); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }

    private void response(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",4); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }

    private void call(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",3); // 对话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }

    private void send(Map map) {
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",2); // 对话
            obj.put("uuid", UUID.randomUUID().toString());
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            System.out.println("未登录");
        }
    }

    private void websocketLogin(Map map,ChannelHandlerContext ctx) {
        String uid = map.get("uid").toString();
        cmap.put("user"+uid,ctx.channel());
        System.out.println(uid+"登录");
    }

    private void sendMessage(ChannelHandlerContext ctx) throws InterruptedException {
        String message = "我是服务器，你好呀";
        ctx.writeAndFlush(new TextWebSocketFrame("hello"));
    }



}