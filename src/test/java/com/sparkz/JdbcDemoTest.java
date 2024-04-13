package com.sparkz;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcDemoTest {
    JdbcDemo jdbc;
    String url = "";
    String username = "";
    String password = "";

    @BeforeAll
    void init() throws SQLException {
        Properties prop = new Properties();
        try {
            var input = new FileInputStream("config.properties");
            prop.load(input);
            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        jdbc = new JdbcDemo(url, username, password);
        System.out.println("Init()");
    }

    @Test
    void testExecuteDQL() throws SQLException {
        int count = getCount("student");
        String query = "SELECT * FROM student";
        var rs = jdbc.executeDQL(query);
        String actualNames[] = new String[count];
        String expected[] = { "Sparkz", "Emeka", "Mara", "Pet", "Fred", "Dav" };

        int i = 0;
        while (rs.next()) {
            String name = rs.getString("username");
            actualNames[i] = name;
            System.out.println(name);
            i++;
        }
        assertArrayEquals(expected, actualNames);
    }

    @Test
    void testExecuteDML() throws SQLException {
        String query = "insert into student values (6, 'Dav')";
        int actual = jdbc.executeDML(query);
        int expected = 1;
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testExecuteDMLPreparedState() throws SQLException {
        String query = "insert into student values (?,?)";
        int userid = 6;
        String username = "Dav";
        var con = jdbc.getConnection();
        var preparedSt = con.prepareStatement(query);
        preparedSt.setInt(1, userid);
        preparedSt.setString(2, username);
        int actual = preparedSt.executeUpdate();
        int expected = 1;
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @AfterAll
    void close() throws SQLException {
        jdbc.closeConnection();
        System.out.println("close()");
    }

    private int getCount(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        var resultSet = jdbc.executeDQL(query);
        int count = 0;

        while (resultSet.next())
            count = +resultSet.getInt(1);

        return count;
    }
}
