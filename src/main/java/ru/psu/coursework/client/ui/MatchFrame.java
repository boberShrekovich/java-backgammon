package ru.psu.coursework.client.ui;

import ru.psu.coursework.client.network.ClientConnection;

import javax.swing.*;

public class MatchFrame extends JFrame {

    private ClientConnection connection;

    public MatchFrame() {
        setTitle("Backgammon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(840, 660);
        setResizable(false);
        setLocationRelativeTo(null);

        try {
            connection = new ClientConnection("localhost", 8765, this);
            connection.connect();

            Thread networkThread = new Thread(connection);
            networkThread.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to connect to the server!",
                    "Network error!!!", JOptionPane.ERROR_MESSAGE);

            System.exit(0);
        }

        showPanel(new Login(this, connection));
    }

    public void showPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MatchFrame().setVisible(true));
    }
}
