package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.client.network.ClientConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Join extends javax.swing.JPanel {

    private final MatchFrame frame;
    private final ClientConnection connection;
    private JTextField jTextField2;
    private JButton jButtonJoin;
    private JButton jButtonCancel;

    public Join(MatchFrame frame, ClientConnection connection) {
        this.frame = frame;
        this.connection = connection;
        initComponents();
        initNavigationActions();
    }

    private void initComponents() {
        setPreferredSize(new Dimension(400, 300));
        setBackground(new java.awt.Color(128, 110, 99));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        JLabel jLabel1 = new JLabel("Enter code for the game:");
        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", Font.BOLD, 14));
        jLabel1.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(jLabel1, gbc);

        jTextField2 = new JTextField();
        jTextField2.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        add(jTextField2, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        jButtonJoin = new JButton("Join");
        jButtonJoin.setBackground(new java.awt.Color(171, 139, 119));
        jButtonJoin.setFont(new java.awt.Font("SimSun-ExtB", Font.PLAIN, 14));
        jButtonJoin.setForeground(new java.awt.Color(86, 57, 39));

        jButtonCancel = new JButton("Cancel");
        jButtonCancel.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCancel.setFont(new java.awt.Font("SimSun-ExtB", Font.PLAIN, 14));
        jButtonCancel.setForeground(new java.awt.Color(86, 57, 39));

        buttonPanel.add(jButtonJoin);
        buttonPanel.add(jButtonCancel);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(buttonPanel, gbc);
    }

    private void initNavigationActions() {
        jButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Joining canceled. Returning to main menu.");
                frame.setSize(840, 660);
                frame.setLocationRelativeTo(null);
                frame.showPanel(new MenuPanel(frame, connection));
            }
        });

        jButtonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomCode = jTextField2.getText().trim();
                if (roomCode.isEmpty()) {
                    JOptionPane.showMessageDialog(Join.this,
                            "Please enter a room code to join!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Message joinMsg = new Message(Commands.JOIN_ROOM, roomCode);
                joinMsg.setCommand(Commands.JOIN_ROOM);
                joinMsg.setData(roomCode);

                System.out.println("Sending a request to JOIN room with code: " + roomCode);
                try {
                    connection.sendMessage(joinMsg);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}