package controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Server {
    static int port=80;
    private Set<String> userNames = new HashSet<>();

    public static void main(String[] args) throws SocketException {
        ServerSocket serverSocket = null;
        boolean serverListening = false;
        BufferedReader in;
        PrintWriter out;
        final Scanner sc=new Scanner(System.in);
        /*
        NetworkInterface ni = NetworkInterface.getByName("name");
        Enumeration e = ni.getInetAddresses();
        if (!e.hasMoreElements())
            return;
        InetAddress ia = (InetAddress) e.nextElement();
        */
        try {
            serverSocket = new ServerSocket(port);
            serverListening = true;
        } catch (IOException ex) {
            System.out.println("Server: puerto no disponible");
        }

        if(serverListening){
            System.out.println("Escuchando puerto: "+port);
            Socket socket = null;
            try {
                socket = serverSocket.accept();

                //listos para escribir
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Thread sender= new Thread(new Runnable() {
                    String msg; //variable that will contains the data writter by the user
                    @Override   // annotation to override the run method
                    public void run() {
                        while(true){
                            msg = sc.nextLine(); //reads data from user's keybord
                            out.println(msg);    // write data stored in msg in the clientSocket
                            out.flush();   // forces the sending of the data
                        }
                    }
                });
                sender.start();

                Socket finalSocket = socket;
                ServerSocket finalServerSocket = serverSocket;
                Thread receive= new Thread(new Runnable() {
                    String msg ;
                    @Override
                    public void run() {
                        try {
                            msg = in.readLine();
                            //mientras que el cliente este conectado
                            while(msg!=null){
                                System.out.println("Client : "+msg);
                                msg = in.readLine();
                            }

                            System.out.println("Client desconectado");

                            out.close();
                            finalSocket.close();
                            finalServerSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                receive.start();
            } catch (IOException ex) {
                System.out.println("Server: error en socket");
            }
        }
    }
}
