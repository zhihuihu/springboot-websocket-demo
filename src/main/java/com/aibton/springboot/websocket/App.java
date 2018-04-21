/**
 * otoc.cn ltd.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package com.aibton.springboot.websocket;

import com.aibton.springboot.websocket.data.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author huzhihui
 * @version $: v 0.1 2018 2018/1/4 9:47 huzhihui Exp $$
 */
@Controller
@EnableScheduling
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/")
    public String index(HttpSession session) {
        session.setAttribute("name", "胡志辉");
        return "index";
    }

    @MessageMapping("/send")
    @SendTo("/topic/send")
    public SocketMessage send(SocketMessage message) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.date = df.format(new Date());
        return message;
    }

    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback";
    }


    @MessageMapping("/chat")
    public void handleChat(SimpMessageHeaderAccessor headerAccessor, String msg) {
        String reId = msg.substring(0, msg.indexOf(";"));
        System.out.println(headerAccessor.getSessionAttributes());
        Map<String, Object> map = headerAccessor.getSessionAttributes();
        System.out.println("HTTP_SESSION_ID:" + map.get("HTTP_SESSION_ID"));
        System.out.println("session:" + headerAccessor.getSessionId() + "		value:" + msg);
        messagingTemplate.convertAndSendToUser(reId, "/queue/notifications", msg);
    }
}
