package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String userNickname;

    private ArrayList<Message> messages;

    @XmlTransient
    private ArrayList<String> users;

    public Room() {
        super();
        messages = new ArrayList<>();
        users = new ArrayList<>();
    }
    public Room(String name, String userNickname) {
        this.name = name;
        this.userNickname = userNickname;
        messages = new ArrayList<>();
        users = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name) && userNickname.equals(room.userNickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userNickname);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", messages=" + messages +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message object) {
        messages.add(object);
    }

    public void joinRoom(String nickname){
        users.add(nickname);
    }

    public void leaveRoom(String nickname){
        users.remove(nickname);
    }
}
