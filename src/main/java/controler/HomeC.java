package controler;

import client.App;
import client.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Message;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class HomeC extends Client implements Initializable {

    public HomeC() {
        super();
    }

    App a = new App();
    private static Stage stg=new Stage();

    List<User> listUsers = new ArrayList<>();
    List<Message> listMessage = new ArrayList<>();

    private ObservableList<User> observableUsers = FXCollections.observableArrayList(listUsers);
    private ObservableList<Message> observableMessage = FXCollections.observableArrayList(listMessage);

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
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> usersColumn;


    /**
     * Cargar chats y rooms
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.roomName.setText(this.roomname);
        messageList();
        usersList();
        messageTable.refresh();
        usersTable.refresh();

    }

    public void messageList(){
        userMessageColumn.setCellValueFactory(message ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(message.getValue().getUserNickname());
            return ssp;
        });

        messageMessageColumn.setCellValueFactory(message ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(message.getValue().getContent());
            return ssp;
        });

        dateMessageColumn.setCellValueFactory(message ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(message.getValue().getTime().toString());
            return ssp;
        });
    }

    public void usersList(){
        usersColumn.setCellValueFactory(user ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(user.getValue().getNickname());
            return ssp;
        });
    }

    private void sendMessage(){
        String n = messageWriter.getText();
        if(!messageWriter.getText().isEmpty()){
            Message m = new Message(this.username,n,this.roomname);
            this.addMessage(m);
            refresh();
        }
    }

    public void selectDarkMode(){

    }

    public void exit(){
        goExit();
    }

    private void goExit(){
        App.loadScene(stg,"Home.fxml","ChatJVA",true,true);
    }


    @Override
    public void refresh() {
        if (messageTable!=null){

        }
        if (usersTable!=null){

        }
    }
}
