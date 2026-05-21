package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;
import ru.psu.coursework.client.network.ClientConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Join extends javax.swing.JPanel {

    private final MatchFrame frame; // Ссылка на главное окно навигации
    private final ClientConnection connection;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonJoin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField2;


    public Join(MatchFrame frame, ClientConnection connection) {
        this.frame = frame;
        this.connection = connection;
        initComponents();
        initNavigationActions();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonJoin = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(128, 110, 99));

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabel1.setText("Enter code for the game:");

        jButtonJoin.setBackground(new java.awt.Color(171, 139, 119));
        jButtonJoin.setFont(new java.awt.Font("SimSun-ExtB", 0, 14));
        jButtonJoin.setForeground(new java.awt.Color(86, 57, 39));
        jButtonJoin.setText("Join");

        jButtonCancel.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCancel.setFont(new java.awt.Font("SimSun-ExtB", 0, 14));
        jButtonCancel.setForeground(new java.awt.Color(86, 57, 39));
        jButtonCancel.setText("Cancel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(108, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButtonJoin)
                                                .addGap(59, 59, 59)
                                                .addComponent(jButtonCancel))
                                        .addComponent(jTextField2)
                                        .addComponent(jLabel1))
                                .addGap(99, 99, 99))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonCancel)
                                        .addComponent(jButtonJoin))
                                .addContainerGap(116, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void initNavigationActions() {
        jButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Joining canceled. Returning to main menu.");

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