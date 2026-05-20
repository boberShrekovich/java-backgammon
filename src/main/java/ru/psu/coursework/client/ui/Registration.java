package ru.psu.coursework.client.ui;

import ru.psu.coursework.additional.messaging.Commands;
import ru.psu.coursework.additional.messaging.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration extends javax.swing.JPanel {

    private final MatchFrame frame;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSignUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldCreateLogin;
    private javax.swing.JPasswordField jPasswordFieldCreatePassword; // ЗАМЕНЕНО НА БЕЗОПАСНЫЙ КЛАСС

    //private ClientConnector connector;

//    public Registration() {
//        initComponents();
//        initNetworkActions();
//    }s

    public Registration(MatchFrame frame) {
        this.frame = frame; // Сохраняем ссылку на окно
        initComponents();
        initNetworkActions();
    }


    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextFieldCreateLogin = new javax.swing.JTextField();
        jPasswordFieldCreatePassword = new javax.swing.JPasswordField(); // Заменено поле
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonSignUp = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();


        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(128, 110, 99));

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabel1.setText("Create a login");

        jLabel2.setFont(new java.awt.Font("SimSun-ExtB", 1, 14));
        jLabel2.setText("Create a password");

        jButtonSignUp.setBackground(new java.awt.Color(171, 139, 119));
        jButtonSignUp.setFont(new java.awt.Font("SimSun-ExtB", 1, 18));
        jButtonSignUp.setForeground(new java.awt.Color(86, 57, 39));
        jButtonSignUp.setText("Sign Up");

        jButtonCancel.setBackground(new java.awt.Color(171, 139, 119));
        jButtonCancel.setFont(new java.awt.Font("SimSun-ExtB", 0, 12)); // NOI18N
        jButtonCancel.setForeground(new java.awt.Color(86, 57, 39));
        jButtonCancel.setText("Cancel");


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(243, 243, 243)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jTextFieldCreateLogin)
                                                                .addComponent(jPasswordFieldCreatePassword, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(319, 319, 319)
                                                .addComponent(jButtonSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(371, 371, 371)
                                                .addComponent(jButtonCancel))) // Теперь скобки закрываются математически верно
                                .addContainerGap(253, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(151, 151, 151)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldCreateLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPasswordFieldCreatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addComponent(jButtonSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jButtonCancel)
                                .addContainerGap(190, Short.MAX_VALUE))
        );


        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void initNetworkActions() {
        jButtonSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = jTextFieldCreateLogin.getText().trim();
                String password = new String(jPasswordFieldCreatePassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Registration.this,
                            "Please fill in all fields to register!", "ERROR!!!", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //логин и пароль
                String[] credentials = new String[] { username, password };

                Message regMsg = new Message(Commands.REGISTRATION_REQUEST, credentials);

                System.out.println("Sending a registration request to the server for: " + username);


                //connector.sendMessage(regMsg);
            }
        });

        jButtonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Registration canceled. Returning to login screen.");

                frame.showPanel(new Login(frame));
            }
        });
    }
}