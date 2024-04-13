/**
 * The JdbcDemo class provides methods for interacting with a relational database using JDBC.
 * It allows executing Data Manipulation Language (DML) and Data Query Language (DQL) statements,
 * managing transactions, and closing database connections.
 */
package com.sparkz;

import java.sql.*;

public class JdbcDemo {
    private Connection connection;

    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * Constructs a new JdbcDemo object with the provided database connection
     * parameters.
     * 
     * @param url      The URL of the database.
     * @param username The username for connecting to the database.
     * @param password The password for connecting to the database.
     * @throws SQLException If a database access error occurs.
     */
    public JdbcDemo(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Retrieves the Connection object representing the database connection.
     * 
     * @return The Connection object representing the database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Executes a Data Manipulation Language (DML) statement.
     * 
     * @param dmlQuery The DML statement to be executed.
     * @return The number of rows affected by the DML statement.
     * @throws SQLException If a database access error occurs.
     */
    public int executeDML(String dmlQuery) throws SQLException {
        statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(dmlQuery);
        statement.close();
        return rowsAffected;
    }

    /**
     * Executes a Data Query Language (DQL) statement.
     * 
     * @param dqlQuery The DQL statement to be executed.
     * @return A ResultSet object containing the results of the DQL statement.
     * @throws SQLException If a database access error occurs.
     */
    public ResultSet executeDQL(String dqlQuery) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(dqlQuery);
        return resultSet;
    }

    /**
     * Begins a transaction by turning off auto-commit mode.
     * 
     * @throws SQLException If a database access error occurs.
     */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * Commits the current transaction and turns on auto-commit mode.
     * 
     * @throws SQLException If a database access error occurs.
     */
    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    /**
     * Closes the database connection, along with associated resources such as
     * statements and result sets.
     * 
     * @throws SQLException If a database access error occurs.
     */
    public void closeConnection() throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (preparedStatement != null)
            preparedStatement.close();
        if (connection != null)
            connection.close();
    }
}
