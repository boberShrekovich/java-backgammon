package ru.psu.coursework.server.main;

import java.net.*;
import java.io.*;

public class Main {
    private static final int PORT = 8765;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            while (true) {
                Socket socket = serverSocket.accept();

            }
        } finally {
            serverSocket.close();
        }

    }
}
