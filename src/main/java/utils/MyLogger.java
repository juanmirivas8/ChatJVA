package utils;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
    /**
     * Inicializa el logger de java util con la configuracion del fichero logging.properties
     *
     * @return Logger inicializado o null si hubo un fallo
     */
    public static Logger getLogger(String url){
        try{
            Logger l;
            InputStream configFile = Utils.class.getResourceAsStream(url);
            LogManager.getLogManager().readConfiguration(configFile);
            l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            return l;
        }catch(Exception s){
            System.out.println("Error al cargar el logger");
        }
        return null;
    }
    public static String exceptionInfo(Throwable e){
        return e.getClass().getName()+" - "+e.getMessage();
    }
}