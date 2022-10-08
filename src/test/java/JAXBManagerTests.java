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

       //add users to the chat
        cj.getUsers().add(new User("John", "holamundo"));
        cj.getUsers().add(new User("John2", "holamundo2"));

        //add rooms to the chat
        cj.getRooms().add(new Room("Room1", "John"));
        cj.getRooms().add(new Room("Room2", "John2"));

        //add messages from John to the Room1
        cj.getRooms().get(0).getMessages().add(new Message("John", "Hello World from John","Room1"));
        cj.getRooms().get(0).getMessages().add(new Message("John", "Hello from John","Room1"));
        cj.getRooms().get(0).getMessages().add(new Message("John", "Hello loco from John","Room1"));

        cj.getRooms().get(0).getMessages().add(new Message("John2", "Hello World from John2","Room1"));
        cj.getRooms().get(0).getMessages().add(new Message("John2", "Hello from John2","Room1"));
        cj.getRooms().get(0).getMessages().add(new Message("John2", "Hello loco from John2","Room1"));

        cj.getRooms().get(1).getMessages().add(new Message("John", "Hello World from John","Room2"));
        cj.getRooms().get(1).getMessages().add(new Message("John", "Hello from John","Room2"));
        cj.getRooms().get(1).getMessages().add(new Message("John", "Hello loco from John","Room2"));

        cj.getRooms().get(1).getMessages().add(new Message("John2", "Hello World from John2","Room2"));
        cj.getRooms().get(1).getMessages().add(new Message("John2", "Hello from John2","Room2"));
        cj.getRooms().get(1).getMessages().add(new Message("John2", "Hello loco from John2","Room2"));

        JAXBManager.marshall("src/test/resources/chat.xml", cj);
    }

    //Test unmarshalling ChatJAXB
    @Test
    public void testUnmarshallChatJAXB() {
       ChatJAXB cj = JAXBManager.unmarshall("src/test/resources/chat.xml", ChatJAXB.class);
        System.out.println(cj);
    }
}
