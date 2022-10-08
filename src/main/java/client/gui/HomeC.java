package client.gui;

import client.App;
import client.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ChatJAXB;
import model.Message;
import model.Room;
import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import utils.Utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class HomeC extends Client implements Initializable {

    public HomeC() {

        super();
        controller=this;
    }

    private static final String chatName = "chat.xml";

    List<User> listUsers = chatJAXB.getUsers();
    List<Message> listMessage = new ArrayList<>();
    List<Room> roomChat = chatJAXB.getRooms();

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
        System.out.println(listUsers);
        System.out.println(listMessage);

        this.roomName.setText(this.chat);


        try {
            messageTable.getItems().addAll((Message) readXMLData());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        messageList();
        usersList();
        messageTable.refresh();
        usersTable.refresh();

        Platform.runLater(()->{
            Utils.closeRequest((Stage) borderPane.getScene().getWindow(),controller);
        });


    }

    private List<ChatJAXB> readXMLData() throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xmlDoc = docBuilder.parse(new InputSource(new FileReader(chatName)));
        Element root = xmlDoc.getDocumentElement();
        if (! root.getTagName().equals("chat")){
            //throw new XMLStreamException("Root element must be a Chat");
            System.out.println("hola");
        }
        List<ChatJAXB> chat = new ArrayList<ChatJAXB>();

        NodeList chatNodeList = root.getElementsByTagName("message");
        int numChat = chatNodeList.getLength();
        for (int i = 0; i < numChat ; i++) {
            Node chatNode = chatNodeList.item(i);
            String userName = "";
            String content = "";
            String date = "";
            NodeList chatChildNodes = chatNode.getChildNodes();
            for (int j = 0; j < chatChildNodes.getLength() ; j++) {
                Node childNode = chatChildNodes.item(j);
                if (childNode.getNodeName().equals("userNickname")){
                    userName = childNode.getTextContent();
                }else if (childNode.getNodeName().equals("content")){
                    content=childNode.getTextContent();
                }else if (childNode.getNodeName().equals("time")){
                    date = childNode.getTextContent();
                }
            }
            chat.add(new ChatJAXB());
            listMessage.add(new Message(userName,content,this.chat));
            System.out.println(listMessage);
        }

        return chat;
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
        this.username = "victor";
        this.chat="Room1";
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
        App.loadScene(new Stage(),"gui/Rooms","ChatJVA",false,false);
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
