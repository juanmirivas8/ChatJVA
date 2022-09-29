package controler;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Scanner;

import static controler.Server.port;

public class Client {

    public static void main(String[] args) throws IOException {
        final Socket clientSocket; // socket used by client to send and recieve data from server
        final BufferedReader in;   // object to read data from socket
        final PrintWriter out;     // object to write data into socket
        final Scanner sc = new Scanner(System.in); // object to read data from user's keybord
        /*
        NetworkInterface ni = NetworkInterface.getByName("name");
        Enumeration e = ni.getInetAddresses();
        if (!e.hasMoreElements())
            return;
        InetAddress ia = (InetAddress) e.nextElement();
         */
        try {
            //clientSocket = new Socket("google.com", 80);
            clientSocket = new Socket("127.0.0.1", port);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            sender.start();
            Thread receiver = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Server : "+msg);
                            msg = in.readLine();
                        }
                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Server out of service");
                    }
                }
            });
            receiver.start();
        } catch (IOException ex) {
            System.out.println("Server killed");
        }
    }
}
