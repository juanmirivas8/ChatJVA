package client.gui;

import client.App;
import client.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Room;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomsC extends Client implements Initializable {

    public RoomsC() {
        super();
        Client.controller=this;
        rooms = FXCollections.observableArrayList(chatJAXB.getRooms());
    }

    private ObservableList<Room> rooms;
    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, String> owner;

    @FXML
    private TableColumn<Room, String> name;

    @FXML
    private Button exit,create,join;

    @FXML
    private AnchorPane anchorPane;

    /**
     * Cargar rooms
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomsTable.setItems(rooms);

        owner.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserNickname()));

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        create.onMouseClickedProperty().setValue(event -> {
            String roomName = Utils.showDialogString((Stage)create.getScene().getWindow(), "New Room", "Room name","Insert the name of the room",30);
            Room room = new Room(roomName,username);

            if (!localAddRoom(room)) {
                Utils.mostrarAlerta("Error", "Error al crear sala", "Nombre repetido");
            }
        });

        join.onMouseClickedProperty().setValue(event -> {
            Room room = roomsTable.getSelectionModel().getSelectedItem();
            if (room != null) {
                localJoinRoom(room);
                App.loadScene(new Stage(),"gui/Home",chat,false,false);
                App.closeScene((Stage) exit.getScene().getWindow());
            }else{
                Utils.mostrarAlerta("Error", "No se ha seleccionado ninguna sala", "Por favor, seleccione una sala de la lista");
            }
        });

        exit.onMouseClickedProperty().setValue(event -> {
            localLogout();
            App.loadScene(new Stage(),"gui/SignInChat",chat,false,false);
            App.closeScene((Stage) exit.getScene().getWindow());
        });

        Platform.runLater(()->{
            Utils.closeRequest((Stage) anchorPane.getScene().getWindow(),this);
        });
    }
    @Override
    public void refresh() {
        rooms.removeAll(rooms);
        rooms.addAll(chatJAXB.getRooms());
    }
}
