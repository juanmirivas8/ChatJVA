package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Comparable<Message>, Serializable {
    private static final long serialVersionUID = 1L;

    private String content;
    private String userNickname;
    private Integer roomID;
    @XmlElement
    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeBind.class)
    private LocalDateTime time;

    public Message() {
        super();
    }

    public Message(String userNickname, String content, Integer roomID) {
        this.content = content;
        this.userNickname = userNickname;
        this.roomID = roomID;
        this.time = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

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

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", roomID=" + roomID +
                ", time=" + time +
                '}';
    }
}



        class LocalDateTimeBind extends XmlAdapter<String, LocalDateTime> {

            @Override
            public LocalDateTime unmarshal(String val) {
                return LocalDateTime.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS"));
            }

            @Override
            public String marshal(LocalDateTime localDateTime) {
                return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS"));
            }

}
