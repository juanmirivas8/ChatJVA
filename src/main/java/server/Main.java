package server;

import utils.MyLogger;
import utils.Utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream("conf.txt"));
            BufferedReader br = new BufferedReader(in);
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null){
                lines.add(line);
            }
            br.close();
            in.close();
            serverSocket = new ServerSocket(Integer.parseInt(lines.get(1)));
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
    }
}
