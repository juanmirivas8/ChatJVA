package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Message;
import model.Room;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeC implements Initializable {

    @FXML
    private Button exit, send;

    @FXML
    private TextField messageWriter;

    @FXML
    private ToggleButton darkMode;

    @FXML
    private Label roomName;

    @FXML
    private TableView<Message> messageTable;

    @FXML
    private TableColumn<Message, String> userMessageColumn;
    @FXML
    private TableColumn<Message, String> messageMessageColumn;
    @FXML
    private TableColumn<Message, String> dateMessageColumn;

    @FXML
    private TableView<Room> usersTable;
    @FXML
    private TableColumn<Room, String> usersColumn;


    /**
     * Cargar chats y rooms
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void selectRoom(Room r){

    }

    public void sendMessage(Message m){

    }

    public void selectDarkMode(){

    }

    public void exit(){
        goExit();
    }

    private void goExit(){

    }



}
