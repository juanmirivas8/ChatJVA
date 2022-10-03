package client;

import model.Command;
import model.Instruction;
import model.Message;
import utils.MyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Client {
    private static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    private static boolean instance = false;
    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static String username;

    public Client(){
        try{
            if(!instance){
                socket = new Socket("localhost", 8080);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                instance = true;
            }
        }catch (IOException e){
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }

    public abstract void refresh();

    public void send(){
        String buffer = "";
        Scanner scanner = new Scanner(System.in);
        while (!buffer.equals("exit")){
            try{
                System.out.println("Enter your message: ");
                buffer = scanner.nextLine();
                objectOutputStream.writeObject(new Instruction(new Message("juanmi",buffer,"CFGS DAM"),Command.SEND_MESSAGE,"juanmi" ));
            }catch (IOException e){
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        }
    }

    public void listen(){
        new Thread(() -> {
            while (socket.isConnected()){
                try {
                    Instruction i = (Instruction) objectInputStream.readObject();
                    switch (i.getCommand()){
                        case SEND_MESSAGE -> {
                            sendMessage(i);
                        }
                    }
                    refresh();

                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE,e.getMessage());
                }
            }
        }).start();
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
        Message m = (Message) i.getObject();
        System.out.println(m);
    }


}
