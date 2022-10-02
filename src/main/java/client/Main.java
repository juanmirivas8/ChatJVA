package client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main  {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        Client client = new Client(socket, "username");
        client.listen();
        client.send();
    }
}
