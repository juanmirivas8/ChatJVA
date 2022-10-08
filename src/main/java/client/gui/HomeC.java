package client.gui;

import client.App;
import client.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Message;
import model.User;
import utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class HomeC extends Client implements Initializable {

    public HomeC() {

        super();
        controller=this;
    }


    List<User> listUsers = new ArrayList<>();
    List<Message> listMessage = new ArrayList<>();

    private ObservableList<User> observableUsers = FXCollections.observableArrayList(listUsers);
    private ObservableList<Message> observableMessage = FXCollections.observableArrayList(listMessage);

    @FXML
    private Button send;
    @FXML
    private Button exit;

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
        /*
        this.roomName.setText(this.chat);
        messageList();
        usersList();
        messageTable.refresh();
        usersTable.refresh();

         */
        //Utils.closeRequest((Stage) borderPane.getScene().getWindow());

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

    public void sendMessage(){
        String n = messageWriter.getText();
        if(!messageWriter.getText().isEmpty()){
            Message m = new Message(this.username,n,this.chat);
            this.localAddMessage(m);
            messageTable.getColumns().addAll(userMessageColumn,messageMessageColumn,dateMessageColumn);
            refresh();
        }
    }

    public void selectDarkMode(){
        darkMode.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                //scene.getStyleSheets().add("dark-theme.css");
            } else {
                //scene.getStyleSheets().remove("dark-theme.css");
            }
        });
    }

    public void exit(){
        goExit();
    }

    private void goExit(){
        App.loadScene(new Stage(),"gui/Rooms","ChatJVA",true,true);
        App.closeScene((Stage) exit.getScene().getWindow());
    }


    @Override
    public void refresh() {
        if (messageTable!=null){

        }
        if (usersTable!=null){

        }
    }
}
