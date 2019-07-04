package app.connect;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private DataSource dataSource;

    public Database(String jndiname) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
        } catch (NamingException e) {
            // Handle error that it not configured in JNDI.
            throw new IllegalStateException(jndiname + " is missing in JNDI!", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static Connection initializeDatabase()
            throws SQLException
    {
        // Initialize all the information regarding
        // Database Connection
        // Database name to access
        Database database = new Database("jdbc/db");
        return database.getConnection();
    }
}
