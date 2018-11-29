package model;

import java.sql.*;

import static model.Secrets.*;

public class DBConnection {

    private static Connection conn;

    public static Connection dbConnect() {
        try {
            Class.forName(driver);
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Failed to create the database connection.");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not Found.");
            e.printStackTrace();
        }
        return conn;
    } // end dbConnect
} // end DBConnection
