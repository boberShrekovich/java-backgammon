package ru.psu.coursework.client.network;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.additional.models.Board;
import ru.psu.coursework.additional.models.Dices;
import ru.psu.coursework.client.ui.Mainframe;
import ru.psu.coursework.client.ui.MatchFrame;
import ru.psu.coursework.client.ui.MenuPanel;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ClientConnection implements Runnable {
    private String ip;
    private int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final MatchFrame frame;
    boolean isRunning = false;

    public ClientConnection(String ip, int port, MatchFrame frame) {
        this.ip = ip;
        this.port = port;
        this.frame = frame;
    }

    public void connect() throws IOException {
        this.socket = new Socket(ip, port);
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public synchronized void sendMessage(Message message) throws IOException {
        if (oos == null) {
            oos.writeObject(message);
            oos.flush();
        }
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Message incomingMessage = (Message) ois.readObject();

                processMessage(incomingMessage);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Connection to the server was lost: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    //ответ сервера
    private void processMessage(Message message) {
        SwingUtilities.invokeLater(() -> {
            if (message.getCommand() == Commands.AUTHENTICATION_SUCCESS) {
                String name = (String) message.getData();

                frame.showPanel(new MenuPanel(frame, this));

            } else if (message.getCommand() == Commands.CREATE_ROOM) {
                String createdCode = (String) message.getData();

                JOptionPane.showMessageDialog(frame, "The room " + createdCode + " has been created. Waiting for the opponent....");

            } else if (message.getCommand() == Commands.GAME_START) {
                Object[] startData = (Object[]) message.getData();
                int myColor = (int) startData[0];
                Dices initialDices = (Dices) startData[1];
                Board initialBoard = (Board) startData[2];

                frame.showPanel(new Mainframe(frame, this, myColor, initialBoard, initialDices));

            } else if (message.getCommand() == Commands.UPDATE_BOARD) {
                Object[] gameData = (Object[]) message.getData();
                Board updatedBoard = (Board) gameData[0];
                Dices updatedDices = (Dices) gameData[1];
                int currentTurnColor = (int) gameData[2];

            } else if (message.getCommand() == Commands.ERROR) {
                JOptionPane.showMessageDialog(frame, message.getErrorMessage(), "Server Error!!!", JOptionPane.ERROR_MESSAGE);

            }
        });
    }

    private void closeConnection() {
        isRunning = false;

        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
