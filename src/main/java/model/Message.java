package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Comparable<Message> {
    private String content;
    private String userNickname;
    private Integer roomID;
    private LocalDateTime time;

    @Override
    public int compareTo(Message o) {
        return this.time.compareTo(o.time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return userNickname.equals(message.userNickname) && roomID.equals(message.roomID) && time.equals(message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNickname, roomID, time);
    }
}
