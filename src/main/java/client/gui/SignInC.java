package client.gui;

import client.App;
import client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInC extends Client implements Initializable {

    public SignInC() {
        super();
        Client.controller = this;
    }

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private HBox hBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            Utils.closeRequest((Stage) hBox.getScene().getWindow(),this);
        });
    }

    @FXML
    private void eventRegistro(ActionEvent event) throws IOException {
        client.App.loadScene(new Stage(),"gui/SignUpChat","Login ChatJVA", false, false);
        App.closeScene((Stage) hBox.getScene().getWindow());
    }
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        String username = txtUser.getText();
        String password = txtPass.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Utils.mostrarAlerta("Error", "Campos vacíos", "Usuario y/o contraseña vacíos");
            txtUser.setText("");
            txtPass.setText("");
        } else {
            password=Utils.encryptSHA256(password);
            User user = new User(username, password);
            if(localLogin(user)){
                App.loadScene(new Stage(),"gui/Home","ChatJVA", false, false);
                App.closeScene((Stage) hBox.getScene().getWindow());
            }else{
                Utils.mostrarAlerta("Error", "Usuario o contraseña incorrectos", "Usuario o contraseña incorrectos");
                txtUser.setText("");
                txtPass.setText("");
            }
        }
    }

    @Override
    public void refresh() {

    }

}
