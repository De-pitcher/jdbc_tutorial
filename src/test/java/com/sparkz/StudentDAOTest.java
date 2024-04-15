package com.sparkz;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDAOTest {
    StudentDAO studentDAO;

    @BeforeAll
    void init() throws SQLException {
        studentDAO = new StudentDAO();
        System.out.println("Init()");
    }

    @Test
    void testGetStudent() throws SQLException {
        Student expected = new Student(1, "Sparkz");
        Student actual = studentDAO.getStudent(1);
        System.out.println(actual.toString());
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllStudent() throws SQLException {
        Student atual[] = studentDAO.getAllStudent();
        Student expected[] = { new Student(1, "Sparkz"), new Student(2, "Emeka"), new Student(3, "Mara"),
                new Student(4, "Pet"), new Student(5, "Fred"), new Student(6, "Dav") };

        assertArrayEquals(expected, atual);
    }

    @Test
    void testAddStudent() throws SQLException {
        Student student = new Student(7, "Barney");
        int actual = studentDAO.addStudent(student);
        assertEquals(1, actual);
    }

    @Test
    void testRemoveStudent() throws SQLException {
        int actual = studentDAO.removeStudent(7);
        assertEquals(1, actual);
    }
}
