package ru.psu.coursework.server.network;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.server.database.DatabaseManager;

import java.io.*;
import java.net.*;

public class PlayerConnection implements Runnable {

    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private DatabaseManager database;
    private LobbyManager lobby; //добавить
    private String username = null;
    private boolean isConnected = true;

    public PlayerConnection(Socket socket, DatabaseManager database, LobbyManager lobby) {
        this.socket = socket;
        this.database = database;
        this.lobby = lobby;
    }


    @Override
    public void run() {
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connection Established");

            while (isConnected) {
                Message message = (Message) in.readObject();

                processMessage(message);
            }


        } catch (EOFException | SocketException e) {
            System.out.println("Player " + username + " disconnected");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println();
        } finally {
            closeConnection();
        }
    }

    private void processMessage(Message message) {
        if (message.getCommand() == Commands.REGISTRATION_REQUEST) {

        } else if (message.getCommand() == Commands.LOGIN_REQUEST) {

        } else if (message.getCommand() == Commands.AUTHENTICATION_SUCCESS) {

        } else if (message.getCommand() == Commands.CREATE_ROOM) {

        } else if (message.getCommand() == Commands.JOIN_ROOM) {

        } else if (message.getCommand() == Commands.GAME_START) {

        } else if (message.getCommand() == Commands.DICE_ROLL) {

        } else if (message.getCommand() == Commands.MAKE_MOVE) {

        } else if (message.getCommand() == Commands.UPDATE_BOARD) {

        } else {

        }
    }

    public String getUsername() {
        return username;
    }

    public synchronized void send(Message message) {
        try {
            if (out != null && !socket.isClosed()) {
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Error sending message to player " + e.getMessage());;
        }
    }

    private void closeConnection() {
        isConnected = false;

        if (lobby != null)
            lobby.removePlayer(this);

        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (!socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
