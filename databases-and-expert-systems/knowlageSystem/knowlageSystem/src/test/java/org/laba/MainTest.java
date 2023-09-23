package org.laba;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * Unit test for simple App.
 */
public class MainTest
{
    /**
     * Rigorous Test :-)
     */
    private static Connection connection;

    private Connection getNewConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    @Before
    public void init() throws SQLException{
        connection = getNewConnection();
    }

    @After
    public void close() throws SQLException{
        connection.close();
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException {
        try (Connection connection = getNewConnection()){
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    @Test
    public void shouldCreateCustomerTable() throws SQLException {
        createCustomerTable();
        connection.createStatement().execute("use customers; SELECT * FROM customers");
    }

    private void createCustomerTable() throws SQLException {
        String customerTableQuery = "CREATE TABLE customers " +
                "(id INTEGER PRIMARY KEY, name TEXT, age INTEGER)";
        //String customerEntryQuery = "INSERT INTO customers " +
                //"VALUES (73, 'Brian', 33)";
        executeUpdate(customerTableQuery);
        //executeUpdate(customerEntryQuery);
    }

    private int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        // Для Insert, Update, Delete
        int result = statement.executeUpdate("use customers " + query);
        return result;
    }

    @Test
    public void shouldSelectData() throws SQLException {
        //createCustomerTable();
        String query = "use knowledge_base";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        query = "SELECT * FROM facts";
        statement = connection.prepareStatement(query);
        //statement.setString(1, "Brian");
        boolean hasResult = statement.execute();
        assertTrue(hasResult);
    }
}
