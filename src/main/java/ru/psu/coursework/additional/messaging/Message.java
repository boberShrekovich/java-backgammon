package ru.psu.coursework.additional.messaging;

import java.io.Serializable;

public class Message implements Serializable {

    private int command;
    private Object data;
    private String errorMessage;

    public Message(){

    }

    public Message(int command, Object data) {
        this.command = command;
        this.data = data;
        this.errorMessage = null;
    }

    public Message(int command, String errorMessage) { //для отправки ошибки
        this.command = command;
        this.data = null;
        this.errorMessage = errorMessage;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
