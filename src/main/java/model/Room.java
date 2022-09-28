package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Room {
    private String name;
    private String userNickname;

    private ArrayList<Message> messages;

    public Room(String name, String userNickname) {
        this.name = name;
        this.userNickname = userNickname;
        messages = new ArrayList<>();
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

}
