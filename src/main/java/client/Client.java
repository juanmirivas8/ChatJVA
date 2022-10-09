package client;

import model.*;
import server.ClientHandler;
import utils.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Client {
    protected static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    private static boolean instance = false;
    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    protected static String username;
    protected static String chat;
    protected static Client controller;
    protected static ChatJAXB chatJAXB;
    public Client(){
        try{
            if(!instance){
                socket = new Socket("localhost", 8080);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                chatJAXB = (ChatJAXB) objectInputStream.readObject();
                instance = true;
                this.listen();
            }
        }catch (IOException|ClassNotFoundException e){
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    public abstract void refresh();
    /**************************************************************************
     * ChatJAXB && Controller Handling
     *************************************************************************/

    public void localExit(){
        //send logout instruction
        try {
            objectOutputStream.writeObject(new Instruction(username,Command.EXIT,username));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        closeEverything();
    }
    public boolean localAddUser(User user){
        if(chatJAXB.addUser(user)){
            broadcastNewUser(user);
            refresh();
            return true;
        }else{
            return false;
        }
    }
    public boolean localAddRoom(Room room){
        if(chatJAXB.addRoom(room)){
            broadcastNewRoom(room);
            refresh();
            return true;
        }else{
            return false;
        }
    }
    public void localAddMessage(Message message){
        chatJAXB.addMessage(message);
        broadcastNewMessage(message);
        refresh();
    }
    public boolean localLogin(User user){
        if(chatJAXB.login(user)){
            username = user.getNickname();
            broadcastLogin(username);
            return true;
        }else{
            return false;
        }
    }
    public void localLogout(){
        chatJAXB.logout(username);
        broadcastLogout(username);
        username = "";
    }



    public void localJoinRoom(Room room){
        chat = room.getName();
        room.joinRoom(username);
        List<Object> params = new ArrayList<>();
        params.add(room.getName());
        params.add(username);
        broadcastJoinRoom(params);
        refresh();
    }


    public void localLeaveRoom(){
        Room room = chatJAXB.getRoom(chat);
        room.leaveRoom(username);
        chat = null;
        broadcastLeaveRoom(room.getName());
        refresh();
    }



    /**************************************************************************
     * Socket Sending Handling
     *************************************************************************/

    private void broadcastLogout(String username) {
        Instruction i = new Instruction(username,Command.LOGOUT,username);
        try {
            objectOutputStream.writeObject(i);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    private void broadcastLogin(String username) {
        Instruction i = new Instruction(username,Command.LOGIN,username);
        try {
            objectOutputStream.writeObject(i);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    private void broadcastLeaveRoom(String roomName){
        Instruction i = new Instruction(roomName, Command.LEAVE_ROOM,username);
        try {
            objectOutputStream.writeObject(i);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    private void broadcastJoinRoom(List<Object> params) {
        Instruction i = new Instruction(params,Command.JOIN_ROOM,username);
        try {
            objectOutputStream.writeObject(i);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    private void broadcastNewUser(User user) {
        try {
            objectOutputStream.writeObject(new Instruction(user,Command.CREATE_USER,username));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    private void broadcastNewMessage(Message message) {
        try {
            objectOutputStream.writeObject(new Instruction(message,Command.SEND_MESSAGE,username));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    private void broadcastNewRoom(Room room) {
        try {
            objectOutputStream.writeObject(new Instruction(room,Command.CREATE_ROOM,username));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
    /**************************************************************************
     * Socket Listening Handling
     *************************************************************************/

    private void listen(){
        new Thread(() -> {
            while (!socket.isClosed()){
                try {
                    Instruction i = (Instruction) objectInputStream.readObject();
                    switch (i.getCommand()){
                        case CREATE_USER -> remoteCreateUser(i);
                        case SEND_MESSAGE -> remoteSendMessage(i);
                        case CREATE_ROOM -> remoteCreateRoom(i);
                        case JOIN_ROOM -> remoteJoinRoom(i);
                        case LEAVE_ROOM -> remoteLeaveRoom(i);
                        case LOGOUT -> remoteLogout(i);
                    }
                    if(controller != null){
                        controller.refresh();
                    }

                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE,e.getMessage());
                }
            }
        }).start();
    }

    private void remoteLogout(Instruction i) {
        chatJAXB.logout((String) i.getObject());
        refresh();
    }


    private void remoteLeaveRoom(Instruction i) {
        Room room = chatJAXB.getRoom((String)i.getObject());
        room.leaveRoom(i.getUsername());
        refresh();
    }

    private void remoteJoinRoom(Instruction i) {
        List<Object> params = (List<Object>) i.getObject();
        String roomName = (String) params.get(0);
        String username = (String) params.get(1);

        Room room = chatJAXB.getRoom(roomName);
        room.joinRoom(username);
        refresh();
    }

    private void remoteCreateRoom(Instruction i) {
        chatJAXB.addRoom((Room) i.getObject());
        refresh();
    }

    private void remoteSendMessage(Instruction i) {
        Message message = (Message) i.getObject();
        chatJAXB.addMessage(message);
        refresh();
    }

    private void remoteCreateUser(Instruction i) {
        User user = (User) i.getObject();
        chatJAXB.addUser(user);
    }

    private void closeEverything(){
        try {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
}
