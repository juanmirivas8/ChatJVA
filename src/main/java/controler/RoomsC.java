package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import model.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomsC implements Initializable {

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, String> room;

    @FXML
    private Button exit;

    @FXML
    private ToggleButton darkMode;

    /**
     * Cargar rooms
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void selectRoom(Room r){

    }

    public void selectDarkMode(){

    }

    public void exit(){

    }
}
