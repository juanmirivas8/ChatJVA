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

public class SignUpC extends Client implements Initializable {

    public SignUpC() {
        super();
        controller=this;
    }

    @FXML
    private TextField txtUser;

    @FXML
    private Button btnSignUp,btnAtras;

    @FXML
    private PasswordField txtPass;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            Utils.closeRequest((Stage) btnAtras.getScene().getWindow(),this);
        });
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
            if(localAddUser(user)){
                App.loadScene(new Stage(),"gui/SignInChat","Login", false, false);
                App.closeScene((Stage) btnAtras.getScene().getWindow());
            }else{
                Utils.mostrarAlerta("Error", "Usuario o contraseña incorrectos", "Usuario o contraseña incorrectos");
                txtUser.setText("");
                txtPass.setText("");
            }
        }
    }

    @FXML
    private void atrasAction(ActionEvent event) throws IOException {
        App.loadScene(new Stage(),"gui/SignInChat","Login", false, false);
        App.closeScene((Stage) btnAtras.getScene().getWindow());
    }

    @Override
    public void refresh() {

    }
}
