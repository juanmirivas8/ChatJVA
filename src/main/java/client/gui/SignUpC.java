package client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpC {

    @FXML
    private TextField txtUser;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPass;


    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if(evt.equals(btnSignUp)){
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
}
