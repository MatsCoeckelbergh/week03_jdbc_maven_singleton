package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {
    public Connection connection;
    private static DBConnectionManager dbConnectionManager_instance = null;

    private DBConnectionManager(String dbURL, String searchPath) {
        Properties dbProperties = new Properties();
        try {
            Class.forName("util.Secret");
            Secret.setPass(dbProperties);
        } catch (ClassNotFoundException e) {
            System.out.println("Class Secret with credentials not found!");
        }
        dbProperties.setProperty("ssl", "true");
        dbProperties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        dbProperties.setProperty("sslmode", "prefer");

        try {
            System.out.print("connecting to database ...");
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(dbURL, dbProperties);

            String setSearchPathQuery = "SELECT set_config('search_path', ?, false);"; // https://dba.stackexchange.com/questions/222080/postgresql-preparedstatement-to-execute-set-schema-command
            PreparedStatement setSearchPath = this.connection.prepareStatement(setSearchPathQuery);
            setSearchPath.setString(1, searchPath);
            setSearchPath.execute();

            System.out.println("done");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static DBConnectionManager getInstance(String dbURL, String searchPath) {
        if (dbConnectionManager_instance == null) {
            dbConnectionManager_instance = new DBConnectionManager(dbURL, searchPath);
        }
        return dbConnectionManager_instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

}
