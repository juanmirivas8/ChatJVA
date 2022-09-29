package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChatJAXB implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<User> users;
    private ArrayList<Room> rooms;

    public ChatJAXB() {
        super();
        users = new ArrayList<>();
        rooms = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "ChatJAXB{" +
                "users=" + users +
                ", rooms=" + rooms +
                '}';
    }
}
