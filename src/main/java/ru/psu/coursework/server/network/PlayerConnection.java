package ru.psu.coursework.server.network;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.additional.models.Cell;
import ru.psu.coursework.additional.models.Dices;
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
            System.err.println("Critical error while processing message: " + e.getMessage());
            e.printStackTrace();
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
//            System.out.println("LOGIN_REQUEST received");
//            System.out.println("Message data: " + message.getData());

            String[] credentials = (String[]) message.getData();

//            if (credentials == null || credentials.length < 2) {
//                System.out.println("ERROR: Invalid credentials array");
//                send(new Message(Commands.ERROR, "Invalid login data"));
//                return;
//            }
//            //
            String loginName = credentials[0];
            String password = credentials[1];


//            System.out.println("Login: '" + loginName + "', Password: '" + password + "'");

            boolean isAuthOk = database.loginUser(loginName, password);

            if (isAuthOk) {
                System.out.println("loginName from client: '" + loginName + "'");

                this.username = loginName;

                lobby.addPlayer(this);

                Message successMsg = new Message();
                successMsg.setCommand(Commands.AUTHENTICATION_SUCCESS);
                successMsg.setData(loginName);
                send(successMsg);

//                send(new Message(Commands.AUTHENTICATION_SUCCESS, loginName));
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
            Room room = this.getCurrentRoom();
            if (room != null) {

                System.out.println("The server accepted the Roll request from the player: " + this.getUsername());
                System.out.println("Now the color is moving: " + room.getCurrentTurnColor());
                System.out.println("White player on the server: " + (room.getWhitePlayer() != null ? room.getWhitePlayer().getUsername() : "null"));
                System.out.println("Black player on the server: " + (room.getBlackPlayer() != null ? room.getBlackPlayer().getUsername() : "null"));



                if (room.getCurrentTurnColor() == Cell.WHITE && this != room.getWhitePlayer()) {
                    send(new Message(Commands.ERROR, "Now it's White's turn! You can't roll the dice!!!"));
                    return;
                }
                if (room.getCurrentTurnColor() == Cell.BLACK && this != room.getBlackPlayer()) {
                    send(new Message(Commands.ERROR, "Now it's Black's turn! You can't roll the dice!!!"));
                    return;
                }

                Dices roomDices = room.getDices();
                roomDices.roll();

                System.out.println("SERVER GENERATED: " + roomDices.getDiceOne() + " and " + roomDices.getDiceTwo());

                //room.getDices().roll();

                ru.psu.coursework.additional.logic.MoveCalculator calculator = new ru.psu.coursework.additional.logic.MoveCalculator();

                boolean currentHeadMoveDone = (room.getCurrentTurnColor() == Cell.WHITE) ? room.getWhiteHeadMoveDone() : room.getBlackHeadMoveDone();
                boolean currentFirstMove = (room.getCurrentTurnColor() == Cell.WHITE) ? room.getWhiteFirstMove() : room.getBlackFirstMove();
                boolean isDouble = roomDices.getDiceOne() == roomDices.getDiceTwo();

                boolean hasMoves = calculator.hasAnyValidMoves(
                        room.getBoard(), room.getCurrentTurnColor(), currentHeadMoveDone,
                        currentFirstMove, isDouble, room.getHeadPiecesTaken(), roomDices.getAvailableValues()
                );

                if (!hasMoves) {
                    System.out.println("SERVER: No moves for player " + this.getUsername() + "!!!");

                    this.send(new Message(Commands.ERROR, "You have no available moves! The turn goes to your opponent"));
                    room.forcePassTurn();

                    return;
                }



                Object[] gameData = new Object[] { room.getBoard(), room.getDices(), room.getCurrentTurnColor() };
                Message updateMsg = new Message(Commands.UPDATE_BOARD, gameData);

                if (room.getWhitePlayer() != null) {
                    room.getWhitePlayer().resetSocketCache();
                    room.getWhitePlayer().send(updateMsg);
                }
                if (room.getBlackPlayer() != null) {
                    room.getBlackPlayer().resetSocketCache();
                    room.getBlackPlayer().send(updateMsg);
                }

//                room.getWhitePlayer().send(updateMsg);
//                room.getBlackPlayer().send(updateMsg);
            }

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
                out.reset();
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

    public synchronized void resetSocketCache() {
        try {
            if (out != null && !socket.isClosed()) {
                out.reset();
            }
        } catch (IOException e) {
            System.err.println("Socket cache flush error: " + e.getMessage());
        }
    }

}
