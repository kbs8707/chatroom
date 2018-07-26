package com.kbs8707.chatroom.chatroom.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.kbs8707.chatroom.chatroom.entity.MessageEntity;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket/{username}")
@Component
public class WebSocketController {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();
    private Session session;

    @OnOpen
    public void OnOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        addOnlineCount();
        webSocketSet.add(this);
        users.put(session.getId(), username);

        String message = users.get(session.getId()) + " has entered the room, current users: " + getOnlineCount();
        MessageEntity mesEnt = new MessageEntity("System", message);
        Gson json = new Gson();
        String output = json.toJson(mesEnt);

        for (WebSocketController s : webSocketSet) {
            try {
                sendMessage(s.session, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        this.session = session;
        subOnlineCount();
        webSocketSet.remove(this);

        String message = users.get(session.getId()) + " has left the room, current users: " + getOnlineCount();
        MessageEntity mesEnt = new MessageEntity("System", message);
        Gson json = new Gson();
        String output = json.toJson(mesEnt);

        for (WebSocketController s : webSocketSet) {
            if (s.session != session) {
                try {
                    sendMessage(s.session, output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        MessageEntity mesEnt = new MessageEntity(users.get(session.getId()), message);
        Gson json = new Gson();

        String output = json.toJson(mesEnt);

        for (WebSocketController s : webSocketSet) {
            try {
                s.sendMessage(s.session, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    private void sendMessage(Session s, String message) throws IOException {
        s.getBasicRemote().sendText(message);
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }
    private static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }
    private static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }

}
