package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends javax.swing.JPanel {

    private final MatchFrame frame;

    private javax.swing.JButton jButtonCreateGame;
    private javax.swing.JButton jButtonJoin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelPhoto;
    private javax.swing.JMenu jMenuAbout;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenu jMenuSettings;
    private javax.swing.JPanel jPanel1;

    //private ClientConnector connector;

    public MenuPanel(MatchFrame frame) {
        this.frame = frame;
        initComponents();
        initNavigationActions();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonCreateGame = new javax.swing.JButton();
        jButtonJoin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabelPhoto = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuSettings = new javax.swing.JMenu();
        jMenuItemLogout = new javax.swing.JMenuItem();
        jMenuAbout = new javax.swing.JMenu();

        // Главный менеджер для всего экрана лобби
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(128, 110, 99));

        jButtonCreateGame.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCreateGame.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jButtonCreateGame.setForeground(new java.awt.Color(86, 57, 39));
        jButtonCreateGame.setText("Create the game");

        jButtonJoin.setBackground(new java.awt.Color(171, 139, 119));
        jButtonJoin.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jButtonJoin.setForeground(new java.awt.Color(86, 57, 39));
        jButtonJoin.setText("Join the game");

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 36));
        jLabel1.setText("Backgammon");


        jLabelPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/psu/coursework/other/CQs5VPCxauzDaoRD5wYO1dc7Dvzlf15szKr1tOGESLMIDfcM4z8n4r59_wmTpmzuur6WGcCP2MJXn234-Eh-XLH0.jpg")));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabelPhoto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButtonCreateGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonJoin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(244, 244, 244)
                                                .addComponent(jButtonCreateGame, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(65, 65, 65)
                                                .addComponent(jButtonJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addComponent(jLabel1)
                                                .addGap(61, 61, 61)
                                                .addComponent(jLabelPhoto)))
                                .addContainerGap(106, Short.MAX_VALUE))
        );

        jMenuSettings.setText("Settings");
        jMenuItemLogout.setText("Logout");
        jMenuSettings.add(jMenuItemLogout);
        jMenuBar.add(jMenuSettings);

        jMenuAbout.setText("About");
        jMenuBar.add(jMenuAbout);

        add(jMenuBar, java.awt.BorderLayout.NORTH);
        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void initNavigationActions() {
        jMenuItemLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Logging out... Returning to login window.");

                frame.showPanel(new Login(frame));
            }
        });

        jButtonCreateGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showPanel(new Create(frame));
            }
        });


        jButtonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showPanel(new Join(frame));
            }
        });
    }
}