package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Create extends javax.swing.JPanel {

    private final MatchFrame frame; // Ссылка на главное окно навигации

    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonCreate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;

    //private ClientConnector connector;

    public Create(MatchFrame frame) {
        this.frame = frame;
        initComponents();
        initNavigationActions();
    }


    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButtonCreate = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(128, 110, 99));

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabel1.setText("Create code for the game: ");

        jButtonCreate.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCreate.setFont(new java.awt.Font("SimSun-ExtB", 0, 14));
        jButtonCreate.setForeground(new java.awt.Color(86, 57, 39));
        jButtonCreate.setText("Create");

        jButtonCancel.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCancel.setFont(new java.awt.Font("SimSun-ExtB", 0, 14));
        jButtonCancel.setForeground(new java.awt.Color(86, 57, 39));
        jButtonCancel.setText("Cancel");


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(102, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jButtonCreate)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jButtonCancel))
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel1))
                                .addGap(95, 95, 95))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonCreate)
                                        .addComponent(jButtonCancel))
                                .addContainerGap(119, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void initNavigationActions() {
        jButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Creation canceled. Returning to main menu.");

                frame.showPanel(new MenuPanel(frame));
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

                Message createMsg = new Message(Commands.CREATE_ROOM, roomCode);
                System.out.println("Sending a request to CREATE a room with code: " + roomCode);

                //connector.sendMessage(createMsg);
            }
        });
    }
}