
package com.sparkz;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 1. Import the package (java.sql)
 * 2. (i). Load the driver (com.mysql.jdbc.Driver) and
 * (ii). Register the driver using forName("com.mysql.jdbc.Driver")
 * 3. Establish the connection (Instantiate Connection)
 * 4. Create the statment (Normal statement, prepared statement, callable
 * statement)
 * 5. Execute the query
 * 6. Process the results
 * 7. Close
 */

public class Main {
    public static void main(String[] args) {
        String url = "";
        String username = "";
        String password = "";
        // String query = "SELECT username FROM student WHERE userid=1";
        String query = "SELECT * FROM student";
        // String query = "insert into student values (4, 'Pet')";
        try {

            Properties prop = new Properties();
            var input = new FileInputStream("config.properties");
            prop.load(input);
            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");

            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            // int rs = st.executeUpdate(query);

            // System.out.println(rs);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            System.out.println("Column Count: " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column Name: " + rsmd.getColumnName(i));
                System.out.println("Column Type: " + rsmd.getColumnTypeName(i));
            }
            while (rs.next()) {
                String userData = rs.getInt(1) + ": " + rs.getString("username");
                System.out.println(userData);
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}