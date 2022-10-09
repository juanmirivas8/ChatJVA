package server;

import model.ChatJAXB;
import utils.JAXBManager;
import utils.MyLogger;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
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
        File f= new File("chat.xml");
        if(f.isFile() && !f.isDirectory()) {
            chatJAXB = JAXBManager.unmarshall("chat.xml",ChatJAXB.class);
        } else {
            chatJAXB = new ChatJAXB();
        }
        try{
           Timer timer = new Timer(true);
           timer.scheduleAtFixedRate(new TimerTask() {
               @Override
               public void run() {
                   JAXBManager.marshall("chat.xml",chatJAXB);
                   System.out.println(chatJAXB);
               }
           },10000,10000);

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
