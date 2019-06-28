package app.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static Connection initializeDatabase()
            throws SQLException
    {
        return DriverManager.getConnection("jdbc:mysql://localhost/demoprj?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "12345cs123");
    }
}
