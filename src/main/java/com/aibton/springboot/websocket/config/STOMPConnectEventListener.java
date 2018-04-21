/**
 * otoc.cn ltd.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package com.aibton.springboot.websocket.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * @author huzhihui
 * @version $: v 0.1 2018 2018/1/18 14:34 huzhihui Exp $$
 */
@Configuration
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Override
    public void onApplicationEvent(SessionConnectEvent sessionConnectEvent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
        //login get from browser
        String agentId = sha.getNativeHeader("login").get(0);
        String sessionId = sha.getSessionId();
        System.out.println("agentId=" + agentId + "sessionId=" + sessionId);
    }
}
