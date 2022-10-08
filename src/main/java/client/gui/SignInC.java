package client.gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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

        //Utils.closeRequest((Stage) hBox.getScene().getWindow());
    }

    @FXML
    private void eventRegistro(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if(evt.equals(btnSignIn)){
            client.App.loadScene(new Stage(),"SignUpChat","Pantalla Principal", false, false);
        }
    }
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if(evt.equals(btnSignIn)){
            if(!txtUser.getText().isEmpty() && !txtPass.getText().isEmpty()){
                String user = txtUser.getText();
                String pass = txtPass.getText();
                /*if(){

                }*/
            }else{
                //Usar alerta de utils
            }
        }else{
            //Usar alerta de utils
        }
    }

    @Override
    public void refresh() {
        //if ()
    }

}
