/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBconnecter;

/**
 *
 * @author lamyu
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/theiairline?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "steve";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            // Establish the connection
            Connection conn = getConnection();
            System.out.println("Connection established successfully!");

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}


