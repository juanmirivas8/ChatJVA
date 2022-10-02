package server;

import model.ChatJAXB;
import utils.JAXBManager;
import utils.MyLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    private ServerSocket serverSocket;
    private static ChatJAXB chatJAXB;
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
       try{
           chatJAXB = JAXBManager.unmarshall("src/main/resources/chat.xml",ChatJAXB.class);
           while (!serverSocket.isClosed()) {
               Socket socket = serverSocket.accept();
               LOGGER.info("Client connected");
               ClientHandler clientHandler = new ClientHandler(socket,chatJAXB);

               Thread thread = new Thread(clientHandler);
               thread.start();
           }
       }catch (IOException e){
           LOGGER.log(Level.SEVERE,e.getMessage());
           closeServer();
       }
    }

    private void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
}
