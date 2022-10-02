import model.ChatJAXB;
import model.Message;
import model.Room;
import model.User;
import org.junit.jupiter.api.Test;
import utils.JAXBManager;

public class JAXBManagerTests {
    //test marshalling

    @Test
    public void testMarshallUser() {
        User user = new User("John", "holamundo");
        JAXBManager.marshall("src/test/resources/user.xml", user);
    }

    @Test
    public void testUnmarshallUser() {
       User u = JAXBManager.unmarshall("src/test/resources/user.xml", User.class);
        System.out.println(u);
    }

    //Test unmarshalling Message
    @Test
    public void testUnmarshallMessage() {
       Message m = JAXBManager.unmarshall("src/test/resources/message.xml", Message.class);
        System.out.println(m);
    }

    //Test marshalling Message
    @Test
    public void testMarshallMessage() {
        Message m = new Message("John", "Hello World","2");
        JAXBManager.marshall("src/test/resources/message.xml", m);
    }

    //Test marshalling Room
    @Test
    public void testMarshallRoom() {
        Room r = new Room("Room1", "John");
        //add messages to the room
        r.getMessages().add(new Message("John", "Hello World","2"));
        r.getMessages().add(new Message("John", "Hello ","2"));
        r.getMessages().add(new Message("John", "Hello loco","2"));

        JAXBManager.marshall("src/test/resources/room.xml", r);
    }

    //Test unmarshalling Room
    @Test
    public void testUnmarshallRoom() {
       Room r = JAXBManager.unmarshall("src/test/resources/room.xml", Room.class);
        System.out.println(r);
    }

    //Test marshalling ChatJAXB
    @Test
    public void testMarshallChatJAXB() {
        ChatJAXB cj = new ChatJAXB();

        cj.getUsers().add(new User("John", "holamundo"));
        cj.getUsers().add(new User("John2", "holamundo2"));
        cj.getUsers().add(new User("John3", "holamundo3"));
        cj.getUsers().add(new User("John4", "holamundo4"));


        Message m1 = new Message("John", "Hello World","1");
        Message m2 = new Message("John", "Hello ","2");
        Message m3 = new Message("John", "Hello loco","3");

        Room r1 = new Room("Room1", "John");
        Room r2 = new Room("Room2", "John");
        Room r3 = new Room("Room3", "John");

        r1.getMessages().add(m1);
        r1.getMessages().add(m2);
        r1.getMessages().add(m3);

        r2.getMessages().add(m1);
        r2.getMessages().add(m2);
        r2.getMessages().add(m3);

        r3.getMessages().add(m1);
        r3.getMessages().add(m2);
        r3.getMessages().add(m3);

        cj.getRooms().add(r1);
        cj.getRooms().add(r2);
        cj.getRooms().add(r3);

        JAXBManager.marshall("src/test/resources/chat.xml", cj);
    }

    //Test unmarshalling ChatJAXB
    @Test
    public void testUnmarshallChatJAXB() {
       ChatJAXB cj = JAXBManager.unmarshall("src/test/resources/chat.xml", ChatJAXB.class);
        System.out.println(cj);
    }
}
