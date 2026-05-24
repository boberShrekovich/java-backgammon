package ru.psu.coursework.client.network;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.additional.models.Board;
import ru.psu.coursework.additional.models.Cell;
import ru.psu.coursework.additional.models.Dices;
import ru.psu.coursework.client.ui.Login;
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
        this.isRunning = true;
    }

    public synchronized void sendMessage(Message message) throws IOException {
        if (oos != null) {
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
                System.out.println("AUTH_SUCCESS received, data: " + message.getData());
                System.out.println("Data class: " + (message.getData() != null ? message.getData().getClass() : "null"));

                String name = (String) message.getData();

                //final String finalName = name;
                System.out.println("Extracted name: '" + name + "'");

                SwingUtilities.invokeLater(() -> {
                    frame.showPanel(new MenuPanel(frame, this));

                    frame.setTitle("Backgammon - " + name);
                });

            } else if (message.getCommand() == Commands.CREATE_ROOM) {
                String createdCode = (String) message.getData();

                JOptionPane.showMessageDialog(frame, "The room " + createdCode + " has been created. Waiting for the opponent....");

            } else if (message.getCommand() == Commands.GAME_START) {
                try {
                    Object[] startData = (Object[]) message.getData();

                    int myColor = Integer.parseInt(startData[0].toString());
                    Dices initialDices = (Dices) startData[1];
                    Board initialBoard = (Board) startData[2];

                    javax.swing.SwingUtilities.invokeLater(() -> {
                        frame.showPanel(new Mainframe(frame, this, myColor, initialBoard, initialDices));

                        String colorText = (myColor == Cell.WHITE) ? " (White)" : " (Black)";
                        frame.setTitle(frame.getTitle() + colorText);

                        frame.pack();
                        frame.setLocationRelativeTo(null);
                    });

                } catch (Exception e) {
                    System.err.println("Ошибка на клиенте при старте игры: " + e.getMessage());
                    e.printStackTrace();
                }

            } else if (message.getCommand() == Commands.UPDATE_BOARD) {
                System.out.println("CLIENT: Received UPDATE_BOARD packet from server!");

                try {
                    Object[] gameData = (Object[]) message.getData();
                    Board updatedBoard = (Board) gameData[0];
                    Dices updatedDices = (Dices) gameData[1];
                    int currentTurnColor = (int) gameData[2];

                    javax.swing.SwingUtilities.invokeLater(() -> {
                        Mainframe activeGameWindow = null;
                        for (java.awt.Component comp : frame.getContentPane().getComponents()) {
                            if (comp instanceof Mainframe) {
                                activeGameWindow = (Mainframe) comp;
                                break;
                            }
                        }

                        if (activeGameWindow != null) {
                            System.out.println("CLIENT: Game window found, calling refreshGameState...");
                            activeGameWindow.refreshGameState(updatedBoard, updatedDices, currentTurnColor);
                        } else {
                            System.err.println("CLIENT ERROR: Mainframe panel not found among active window components!");
                        }

                    });
                } catch (Exception e) {
                    System.err.println("Error unpacking UPDATE_BOARD on the client: " + e.getMessage());
                    e.printStackTrace();
                }

            } else if (message.getCommand() == Commands.ERROR) {
                JOptionPane.showMessageDialog(frame, message.getErrorMessage(), "Server Error!!!", JOptionPane.ERROR_MESSAGE);

            } else if (message.getCommand() == Commands.REGISTRATION_REQUEST) {
                String result = (String) message.getData();

                if ("Success".equals(result) || message.getErrorMessage() == null) {
                    JOptionPane.showMessageDialog(frame,
                            "Регистрация прошла успешно! Теперь вы можете войти.",
                            "Успех", JOptionPane.INFORMATION_MESSAGE);

                    frame.showPanel(new Login(frame, this));
                }

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
