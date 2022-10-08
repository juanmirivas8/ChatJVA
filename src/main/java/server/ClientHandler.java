package server;

import model.*;
import utils.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    private static final ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ChatJAXB chatJAXB;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    public ClientHandler(Socket socket, ChatJAXB chatJAXB) {
        this.socket = socket;
        ClientHandler.chatJAXB = chatJAXB;
        try {
            username="";
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            clients.add(this);
            objectOutputStream.writeObject(chatJAXB);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    @Override
    public void run() {
        while (!socket.isClosed()){
            try{
                Instruction i = (Instruction) objectInputStream.readObject();
                switch (i.getCommand()){
                    case SEND_MESSAGE -> sendMessage(i);
                    case CREATE_USER -> createUser(i);
                    case CREATE_ROOM -> createRoom(i);
                    case JOIN_ROOM -> joinRoom(i);
                    case LEAVE_ROOM -> leaveRoom(i);
                    case LOGIN -> login(i);
                    case LOGOUT -> logout(i);
                    case EXIT -> exit(i);
                }
            }catch (IOException | ClassNotFoundException e){
                LOGGER.log(Level.SEVERE,e,()->"");
            }
        }
    }

    private void exit(Instruction i) {
        clients.remove(this);
        i.setCommand(Command.LOGOUT);
        logout(i);
        closeEverything();

    }

    private void logout(Instruction i) {
        chatJAXB.logout(i.getUsername());
        clients.forEach(clientHandler -> {
            try{
                if(!clientHandler.username.equals(i.getUsername())){
                    clientHandler.objectOutputStream.writeObject(i);
                }
            }catch (IOException e){
                LOGGER.log(Level.SEVERE,e,()->"");
            }
        });

    }

    private void login(Instruction i) {
        username = i.getUsername();
    }


    /**************************************************************************
     * Socket Listening Handling
     *************************************************************************/
    private void leaveRoom(Instruction i) {
        Room room = chatJAXB.getRoom((String)i.getObject());
        room.leaveRoom(i.getUsername());

        clients.forEach(clientHandler -> {
            try{
                if(!clientHandler.username.equals(i.getUsername())){
                    clientHandler.objectOutputStream.writeObject(i);
                }
            }catch (IOException e){
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        });
    }

    private void joinRoom(Instruction i) {
        List<Object> params = (List<Object>) i.getObject();
        String roomName = (String) params.get(0);
        String username = (String) params.get(1);

        Room room = chatJAXB.getRoom(roomName);
        room.joinRoom(username);
        clients.forEach(clientHandler -> {
            try{
                if(!clientHandler.username.equals(i.getUsername())){
                    clientHandler.objectOutputStream.writeObject(i);
                }
            }catch (IOException e){
                LOGGER.log(Level.SEVERE,e.getMessage());
            }

        });
    }

    private void createUser(Instruction i) {
        User user = (User) i.getObject();
        chatJAXB.addUser(user);

        clients.forEach(clientHandler -> {
            try {
                if(!clientHandler.username.equals(i.getUsername())||clientHandler.username.equals("")){
                    clientHandler.objectOutputStream.writeObject(i);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        });

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

    private void sendMessage(Instruction i){
        chatJAXB.addMessage((Message)i.getObject());
        clients.forEach(clientHandler -> {
            try {
                if(!clientHandler.username.equals(i.getUsername())) {
                    clientHandler.objectOutputStream.writeObject(i);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        });
    }

    private void createRoom(Instruction i) {
        Room room = (Room) i.getObject();
        chatJAXB.addRoom(room);
        clients.forEach(clientHandler -> {
            try {
                if(!clientHandler.username.equals(i.getUsername())) {
                    clientHandler.objectOutputStream.writeObject(i);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        });
    }
}
