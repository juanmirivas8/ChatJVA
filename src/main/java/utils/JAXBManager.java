package utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JAXBManager {
    private static final Logger LOGGER = MyLogger.getLogger("/logging.properties");
    public static <T> T unmarshall(String xml, Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            return (T) jaxbContext.createUnmarshaller().unmarshal(new FileReader(xml));
        } catch (JAXBException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        return null;
    }

    public static <T> void marshall(String xml, T object) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(object, new FileWriter(xml));
        } catch (JAXBException | IOException e) {
            LOGGER.log(Level.SEVERE,e.toString());
        }
    }
}
