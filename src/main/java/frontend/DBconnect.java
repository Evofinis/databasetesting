package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Created by User on 4/3/2018.
 */


public class DBconnect {

    private static Connection conn;
    private static String url = "jdbc:mysql://localhost/tutorialdb?useSSL=false";

    public static Connection connect(String user, String pw) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(url, user, pw);
                System.out.println("Connection Successful");
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Error 1: " + cnfe.getMessage());
            } catch (InstantiationException ie) {
                System.err.println("Error 2: " + ie.getMessage());
            } catch (IllegalAccessException iae) {
                System.err.println("Error 3: " + iae.getMessage());
            } catch (SQLException e) {
            }
        return conn;
    }

    public static String getID() throws SQLException{
        int id, c=isClosed();

        if (c==1) {

            ResultSet r = conn.createStatement().executeQuery("SELECT student_id FROM student ORDER BY student_id DESC limit 1");

            r.next();
            id = r.getInt("student_id");

            return Integer.toString(id + 1);
        } else{
            String s = "Database connection missing";
            return s;
        }
    }

    public static void getDatabaseNames (ObservableList<ObservableList> data) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SHOW DATABASES");

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                // Iterate Column
                String notthere = "---";
                rs.getString(i);
                if (rs.wasNull()) {
                    row.add(notthere);
                } else {
                    row.add(rs.getString(i));
                }
            }
            data.add(row);
        }
    }

    public static int isClosed() throws SQLException {
        int r = 1;

        try {
            if (conn.isClosed()) {
                r = 0;
            }
        }catch (SQLException e) {
            System.out.println("Invalid Credentials");
        }
        return r;
    }

}





