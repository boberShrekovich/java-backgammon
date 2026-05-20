package ru.psu.coursework.client.ui;

import javax.swing.*;

public class MatchFrame extends JFrame {

    public MatchFrame() {
        setTitle("Backgammon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(840, 660);
        setResizable(false);
        setLocationRelativeTo(null);


        showPanel(new Login(this));
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
