package com.kbs8707.chatroom.chatroom.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import com.kbs8707.chatroom.chatroom.entity.SystemEntity;
import com.kbs8707.chatroom.chatroom.util.StringUtil;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket/{username}")
@Component
public class WebSocketController {
    private static int onlineCount = 0;
    //Set for connected sessions
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<>();

    private static HashMap<String, String> users = new HashMap<>();
    private Session session;
    private MessageEntity mesEnt = new MessageEntity();
    private SystemEntity sysEnt = new SystemEntity();

    @OnOpen
    public void OnOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        addOnlineCount();
        webSocketSet.add(this);
        users.put(session.getId(), username);

        String userList = StringUtil.mapToString(users);
        String message = users.get(session.getId()) + " has entered the room, current users: " + userList;

        Gson json = new Gson();
        sysEnt.setUserName("System");
        sysEnt.setMessage(message);
        sysEnt.setUserList(json.toJson(users));

        String output = json.toJson(sysEnt);

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
        String leavingUser = users.get(session.getId());
        subOnlineCount();
        webSocketSet.remove(this);
        users.remove(session.getId());

        String userList = StringUtil.mapToString(users);
        String message = leavingUser + " has left the room, current users: " + userList;

        Gson json = new Gson();
        sysEnt.setUserName("System");
        sysEnt.setMessage(message);
        sysEnt.setUserList(json.toJson(users));

        String output = json.toJson(sysEnt);

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
        mesEnt.setUserName(users.get(session.getId()));
        mesEnt.setMessage(message);
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
