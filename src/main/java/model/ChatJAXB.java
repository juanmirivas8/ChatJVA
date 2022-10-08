package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChatJAXB implements Serializable {
    @Serial
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

    public boolean addMessage(Message object) {
        Room r = rooms.stream().filter(room -> room.getName().equals(object.getRoom())).findFirst().orElse(null);
        if (r != null) {
            r.addMessage(object);
            return true;
        }else{
            return false;
        }
    }

    public boolean addUser(User user) {
        if (findUser(user.getNickname())) {
            return false;
        } else {
            users.add(user);
            return true;
        }
    }

    public boolean findUser(String name){
        return users.stream().anyMatch(user -> user.getNickname().equals(name));
    }

    public boolean findRoom(String name){
        return rooms.stream().anyMatch(room -> room.getName().equals(name));
    }

    public boolean addRoom(Room room) {
        if(!findRoom(room.getName())){
            rooms.add(room);
            return true;
        }else{
            return false;
        }
    }

    public Room getRoom(String roomName) {
        return rooms.stream().filter(room -> room.getName().equals(roomName)).findFirst().orElse(null);
    }

    public boolean login(User user) {
        return users.stream().anyMatch(user1 -> user1.getNickname().equals(user.getNickname()) && user1.getPassword().equals(user.getPassword()));
    }

    public void logout(String nickname) {
        //recorre todas las salas y elimina al usuario de la lista de usuarios
        rooms.forEach(room -> room.leaveRoom(nickname));
    }

    @Override
    public String toString() {
        return "ChatJAXB{" +
                "users=" + users +
                ", rooms=" + rooms +
                '}';
    }
}
