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
    private Room currentRoom = null;

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
            String[] credentials = (String[]) message.getData();
            String regLogin = credentials[0];
            String regPassword = credentials[1];

            boolean isRegOk = database.registerUser(regLogin, regPassword);

            if (isRegOk) {
                send(new Message(Commands.REGISTRATION_REQUEST, "Registration successful!"));
                System.out.println("User registered successfully: " + regLogin);

            } else {
                send(new Message(Commands.ERROR, "Login is already taken!"));
            }

        } else if (message.getCommand() == Commands.LOGIN_REQUEST) {
            String[] credentials = (String[]) message.getData();
            String loginName = credentials[0];
            String password = credentials[1];

            boolean isAuthOk = database.loginUser(loginName, password);

            if (isAuthOk) {
                this.username = loginName;

                lobby.addPlayer(this);

                send(new Message(Commands.AUTHENTICATION_SUCCESS, loginName));
                System.out.println("User authenticated: " + loginName);

            } else {
                send(new Message(Commands.ERROR, "Invalid login or password!"));
            }

        } else if (message.getCommand() == Commands.AUTHENTICATION_SUCCESS) {
            System.out.println("Warning: Received outbound-only command from client!!!");

        } else if (message.getCommand() == Commands.CREATE_ROOM) {
            String roomCode = (String) message.getData();

            lobby.createRoom(this, roomCode);

        } else if (message.getCommand() == Commands.JOIN_ROOM) {
            String roomCode = (String) message.getData();

            lobby.joinRoom(this, roomCode);

        } else if (message.getCommand() == Commands.GAME_START) {
            System.out.println("Warning: Received outbound-only command from client!!!");

        } else if (message.getCommand() == Commands.DICE_ROLL) {

        } else if (message.getCommand() == Commands.MAKE_MOVE) {
            if (currentRoom != null) {
                int[] moveCoords = (int[]) message.getData();
                int from = moveCoords[0];
                int to = moveCoords[1];

                currentRoom.operatePlayersMove(this, from, to);

            } else {
                send(new Message(Commands.ERROR, "You are not in the active game room!"));
            }

        } else if (message.getCommand() == Commands.UPDATE_BOARD) {
            System.out.println("Warning: Received outbound-only command from client!!!");

        } else {
            send(new Message(Commands.ERROR, "Unknown server command code!"));

        }
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
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
