package utils;

import client.Client;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private final static Logger LOGGER = MyLogger.getLogger("/logging.properties");

    /**
     * Método que encripta una cadena mediante SHA256
     * @param s Cadena a encriptar
     * @return Cadena encriptada
     */
    public static String encryptSHA256 (String s){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");
            md.update(s.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : md.digest()) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,MyLogger.exceptionInfo(e));
        }
        return result;
    }

    /**
     * Devuelve una lista con cada una de las lineas de un fichero usando BufferedReader
     * @param url Ubicacion del fichero a leer
     * @return Lista con las lineas
     */
    public static List<String> getFileAsLines(String url){
        try {
            InputStreamReader in = new InputStreamReader(Utils.class.getResourceAsStream(url));
            BufferedReader br = new BufferedReader(in);
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null){
                lines.add(line);
            }
            br.close();
            in.close();
            return lines;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,MyLogger.exceptionInfo(e));
        }
        return null;
    }

    /**
     * Metodo que lee sentencias de un fichero usando ; como delimitador
     * @param url Ubicacion del fichero
     * @return Lista con las sentencias
     */
    public static List<String> getFileAsLinesWithScanner(String url){
        try {
            Scanner sc = new Scanner(Utils.class.getResourceAsStream(url));
            sc.useDelimiter(";");
            List<String> l = new ArrayList<>();
            while (sc.hasNext()){
                l.add(sc.next());
            }
            return l;
        }catch (Exception e){
            LOGGER.log(Level.SEVERE,MyLogger.exceptionInfo(e));
        }
        return null;
    }
    public static boolean mostrarConfirmacion(String header,String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText(header);
        alert.setContentText(description);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
    public static void mostrarAlerta(String title, String header, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.showAndWait();
    }

    public static void closeRequest(Stage stage, Client client){
        stage.setOnCloseRequest(windowEvent -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Confirmacion de cierre");
            a.setHeaderText("¿Esta seguro de salir del programa?");
            Stage s =(Stage)a.getDialogPane().getScene().getWindow();
            s.initOwner(stage);
            s.toFront();
            a.showAndWait().filter(buttonType -> buttonType== ButtonType.OK).ifPresentOrElse(buttonType -> {Platform.exit();client.localExit();},windowEvent::consume);
        });
    }

    public static String showDialogString(Stage stage, String title, String header, String description, int max_characters){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(description);
        Stage s =(Stage)dialog.getDialogPane().getScene().getWindow();
        s.initOwner(stage);
        s.toFront();
        Utils.addTextLimiter(dialog.getEditor(),max_characters);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }


}
