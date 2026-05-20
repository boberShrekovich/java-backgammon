//package ru.psu.coursework.client.ui;
//
//import javax.swing.*;
//import java.awt.*;
//
//
//public class AuthPanel extends JPanel {
//
//    private JTextField loginField;
//    private JPasswordField passwordField;
//    private JButton signIn;
//    private JButton signUp;
//
//    public AuthPanel() {
//        setBackground(new Color(128, 110, 99));
//        setPreferredSize(new Dimension(840, 660));
//
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.insets = new Insets(5, 10, 5, 10);
//
//        JLabel loginLabel = new JLabel("Login:");
//        loginLabel.setForeground(Color.BLACK);
//        loginLabel.setFont(new Font("SimSun-ExtB", Font.BOLD, 14));
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        add(loginLabel, gbc);
//
//        loginField = new JTextField();
//        loginField.setPreferredSize(new Dimension(300, 22)); // Ширина 300, высота 30
//        loginField.setBackground(Color.WHITE);
//        loginField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        gbc.gridy = 1;
//        add(loginField, gbc);
//
//        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setForeground(Color.BLACK);
//        passwordLabel.setFont(new Font("SimSun-ExtB", Font.BOLD, 14));
//        gbc.gridy = 2;
//        add(passwordLabel, gbc);
//
//        passwordField = new JPasswordField();
//        passwordField.setPreferredSize(new Dimension(300, 22));
//        passwordField.setBackground(Color.WHITE);
//        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        gbc.gridy = 3;
//        add(passwordField, gbc);
//
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
//        buttonPanel.setOpaque(false);
//
//        signIn = new JButton("Sign In");
//        signIn.setPreferredSize(new Dimension(97, 26));
//        signIn.setBackground(new Color(171, 139, 119));
//        signIn.setForeground(new Color(86, 57, 39));
//        signIn.setFont(new Font("SimSun-ExtB", Font.BOLD, 18));
//        signIn.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 80), 1));
//        signIn.setFocusPainted(false);
//        buttonPanel.add(signIn);
//
//        signUp = new JButton("Sign Up");
//        signUp.setPreferredSize(new Dimension(97, 26));
//        signUp.setBackground(new Color(171, 139, 119));
//        signUp.setForeground(new Color(86, 57, 39));
//        signUp.setFont(new Font("SimSun-ExtB", Font.BOLD, 18));
//        signUp.setBorder(BorderFactory.createLineBorder(new Color(110, 90, 80), 1));
//        signUp.setFocusPainted(false);
//        buttonPanel.add(signUp);
//
//    }
//
//
//
//}
