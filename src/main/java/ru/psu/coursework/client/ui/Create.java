package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.client.network.ClientConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Create extends javax.swing.JPanel {

    private final MatchFrame frame;
    private final ClientConnection connection;
    private JTextField jTextField1;
    private JButton jButtonCreate;
    private JButton jButtonCancel;

    public Create(MatchFrame frame, ClientConnection connection) {
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
        gbc.insets = new Insets(10, 20, 10, 20); // Отступы между элементами

        JLabel jLabel1 = new JLabel("Create code for the game:");
        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", Font.BOLD, 14));
        jLabel1.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(jLabel1, gbc);

        jTextField1 = new JTextField();
        jTextField1.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        add(jTextField1, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        jButtonCreate = new JButton("Create");
        jButtonCreate.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCreate.setFont(new java.awt.Font("SimSun-ExtB", Font.PLAIN, 14));
        jButtonCreate.setForeground(new java.awt.Color(86, 57, 39));

        jButtonCancel = new JButton("Cancel");
        jButtonCancel.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCancel.setFont(new java.awt.Font("SimSun-ExtB", Font.PLAIN, 14));
        jButtonCancel.setForeground(new java.awt.Color(86, 57, 39));

        buttonPanel.add(jButtonCreate);
        buttonPanel.add(jButtonCancel);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 20, 10, 20); // Чуть увеличиваем отступ сверху для кнопок
        add(buttonPanel, gbc);
    }

    private void initNavigationActions() {
        jButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Creation canceled. Returning to main menu.");

                frame.setSize(840, 660);
                frame.setLocationRelativeTo(null);
                frame.showPanel(new MenuPanel(frame, connection));
            }
        });

        jButtonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomCode = jTextField1.getText().trim();
                if (roomCode.isEmpty()) {
                    JOptionPane.showMessageDialog(Create.this,
                            "Please enter a room code!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(frame,
                        "Waiting for an opponent...",
                        "Room " + roomCode,
                        JOptionPane.INFORMATION_MESSAGE);

                Message createMsg = new Message(Commands.CREATE_ROOM, roomCode);
                createMsg.setCommand(Commands.CREATE_ROOM);
                createMsg.setData(roomCode);

                System.out.println("Sending a request to CREATE a room with code: " + roomCode);
                try {
                    connection.sendMessage(createMsg);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}