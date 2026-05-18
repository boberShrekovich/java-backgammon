package ru.psu.coursework.server.database;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/backgammon";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState()))
                System.out.println("Login already exists!!!");
            else e.printStackTrace();

            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { //если пользователь существует
                    String storedHash = rs.getString("password");//правильный пароь

                    return storedHash.equals(password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
