package model;

import java.sql.*;

import static model.Secrets.*;

public class DBConnection {

    private static Consultant consultant;

    public void dbConnect (String UN, String PW) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } // end try

        try (Connection conn = DriverManager.getConnection(url, user, password);
        Statement statement = conn.createStatement()) {
            ResultSet data = statement.executeQuery("SELECT * FROM user");
            while (data.next()) {
                System.out.println(data);
            } // end while
        } catch (SQLException e) {
            e.printStackTrace();
        } // end try
    } // end dbConnect
} // end DBConnection
