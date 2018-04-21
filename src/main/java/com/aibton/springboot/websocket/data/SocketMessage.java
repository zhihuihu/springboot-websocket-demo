/**
 * otoc.cn ltd.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package com.aibton.springboot.websocket.data;

/**
 * @author huzhihui
 * @version $: v 0.1 2018 2018/1/4 9:46 huzhihui Exp $$
 */
public class SocketMessage {
    public String message;

    public String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
