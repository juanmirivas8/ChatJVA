package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
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
}
