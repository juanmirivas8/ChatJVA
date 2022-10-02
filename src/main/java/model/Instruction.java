package model;

import java.io.Serial;
import java.io.Serializable;

public class Instruction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Object object;
    private Command command;
    private String username;

    public Instruction(Object object, Command command, String username) {
        this.object = object;
        this.command = command;
        this.username = username;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
