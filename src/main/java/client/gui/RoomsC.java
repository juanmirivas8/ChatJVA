package client.gui;

import client.App;
import client.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
    }

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, String> room;

    @FXML
    private Button exit;

    @FXML
    private ToggleButton darkMode;

    @FXML
    private AnchorPane anchorPane;

    /**
     * Cargar rooms
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomList();
        roomsTable.refresh();
        Platform.runLater(()->{
            Utils.closeRequest((Stage) anchorPane.getScene().getWindow(),controller);
        });
    }

    public void roomList(){
        room.setCellValueFactory(r ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(r.getValue().getName());
            return ssp;
        });
    }

    public void selectRoom(){
        App.loadScene(new Stage(),"gui/Home","ChatJVA",false,false);
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
        App.loadScene(new Stage(),"gui/SignInChat","ChatJVA",false,false);
        App.closeScene((Stage) exit.getScene().getWindow());
    }

    @Override
    public void refresh() {

    }
}
