package client.gui;

import client.App;
import client.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Message;
import model.Room;
import utils.Utils;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class HomeC extends Client implements Initializable {

    public HomeC() {
        super();
        controller=this;
    }

    Room room = chatJAXB.getRoom(this.chat);

    List<String> listUsers = room.getUsers();
    List<Message> listMessage = room.getMessages();

    private ObservableList<String> observableUsers = FXCollections.observableArrayList(listUsers);
    private ObservableList<Message> observableMessage = FXCollections.observableArrayList(listMessage);

    @FXML
    private Button send;
    @FXML
    private Button exit;

    @FXML
    private TextField messageWriter;

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
    private TableView<String> usersTable;
    @FXML
    private TableColumn<String, String> usersColumn;

    @FXML
    private BorderPane borderPane;


    /**
     * Cargar chats y rooms
     * @param url
     * @param resourceBundle
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.roomName.setText(room.getName());


        messageTable.setItems(observableMessage);
        usersTable.setItems(observableUsers);

        messageList();
        usersList();

        Platform.runLater(()->{
            Utils.closeRequest((Stage) borderPane.getScene().getWindow(),controller);
        });


    }
    public void enter(){
        messageWriter.setOnKeyPressed( event -> {
            if(event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
            String formateDateTime = message.getValue().getTime().format(formatter);
            ssp.setValue(formateDateTime);
            return ssp;
        });
    }

    public void usersList(){
        usersColumn.setCellValueFactory(user ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(user.getValue());
            return ssp;
        });
    }


    public void sendMessage(){
        String n = messageWriter.getText();
        if(!messageWriter.getText().isEmpty()){
            Message m = new Message(this.username,n,this.chat);
            this.localAddMessage(m);
            messageWriter.clear();
            refresh();
        }
    }

    public void exit(){
        goExit();
    }

    private void goExit(){
        localLeaveRoom();
        App.loadScene(new Stage(),"gui/Rooms","ChatJVA",false,false);
        App.closeScene((Stage) exit.getScene().getWindow());
    }


    @Override
    public void refresh() {
        observableMessage.removeAll(observableMessage);
        observableMessage.addAll(listMessage);
        observableUsers.removeAll(observableUsers);
        observableUsers.addAll(listUsers);
    }
}
