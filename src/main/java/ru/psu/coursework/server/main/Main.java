package ru.psu.coursework.server.main;

import ru.psu.coursework.server.database.DatabaseManager;
import ru.psu.coursework.server.network.LobbyManager;
import ru.psu.coursework.server.network.PlayerConnection;

import java.net.*;
import java.io.*;

public class Main {
    private static final int PORT = 8765;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server...");

        DatabaseManager database = new DatabaseManager();
        LobbyManager lobby = new LobbyManager();

        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            System.out.println("Server successfully started and listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();

                PlayerConnection connection = new PlayerConnection(socket, database, lobby);

                Thread thread = new Thread(connection);
                thread.start();


            }
        } finally {
            serverSocket.close();
        }

    }
}
