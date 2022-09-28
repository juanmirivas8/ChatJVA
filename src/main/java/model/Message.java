package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Comparable<Message> {
    private String content;
    private String userNickname;
    private Integer roomID;
    @XmlElement
    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeBind.class)
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
