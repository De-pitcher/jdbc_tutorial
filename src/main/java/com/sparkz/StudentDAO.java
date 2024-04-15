package com.sparkz;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class StudentDAO {
    private JdbcDemo jdbc;
    private String url = "";
    private String username = "";
    private String password = "";

    StudentDAO() throws SQLException {
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
        System.out.println("Init StudentDAO()");
    }

    public int getCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM student";
        var resultSet = jdbc.executeDQL(query);
        int count = 0;

        while (resultSet.next())
            count = +resultSet.getInt(1);

        return count;
    }

    public Student getStudent(int userid) throws SQLException {
        String query = "select username from student where userid=" + userid;
        var rs = jdbc.executeDQL(query);
        rs.next();
        String name = rs.getString(1);
        return new Student(userid, name);
    }

    public Student[] getAllStudent() throws SQLException {
        String query = "SELECT * FROM student";
        int count = getCount();
        Student students[] = new Student[count];

        var rs = jdbc.executeDQL(query);
        int i = 0;
        while (rs.next()) {
            String username = rs.getString("username");
            int userid = rs.getInt("userid");
            students[i] = new Student(userid, username);
            i++;
        }
        return students;
    }

    public int addStudent(Student student) throws SQLException {
        String query = "insert into student values (?,?)";
        
        var con = jdbc.getConnection();
        var preparedSt = con.prepareStatement(query);
        preparedSt.setInt(1, student.userid());
        preparedSt.setString(2, student.username());

        return preparedSt.executeUpdate();
    }

    public int removeStudent(int userid) throws SQLException {
        String query = "delete from student where userid=" + userid;
        return jdbc.executeDML(query);
    }
}
