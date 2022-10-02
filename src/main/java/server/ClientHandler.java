package server;

import model.ChatJAXB;
import model.Command;
import model.Instruction;
import model.Message;
import utils.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    private static final ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ChatJAXB chatJAXB;
    private final Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    public ClientHandler(Socket socket, ChatJAXB chatJAXB) {
        this.socket = socket;
        ClientHandler.chatJAXB = chatJAXB;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            clients.add(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());

        }
    }

    @Override
    public void run() {
        while (socket.isConnected()){
            try{
                Instruction i = (Instruction) objectInputStream.readObject();
                switch (i.getCommand()){
                    case SEND_MESSAGE -> sendMessage(i);

                }
            }catch (IOException | ClassNotFoundException e){
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        }
    }


    private void removeClient(){
        clients.remove(this);
        //notificar
    }
    private void closeEverything(){
        removeClient();
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
                clientHandler.objectOutputStream.writeObject(i);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        });
    }
}
