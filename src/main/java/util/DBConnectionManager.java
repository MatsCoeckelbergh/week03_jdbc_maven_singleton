package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {
    private Connection connection;

    public DBConnectionManager(String dbURL, String searchPath) {
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
        dbProperties.setProperty("allowMultiQueries", "true");
        dbProperties.setProperty("prepareThreshold", "0");

        try {
            System.out.print("connecting to database ...");
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(dbURL, dbProperties);
            this.connection.setAutoCommit(false);
            String setSearchPathQuery = "SELECT set_config('search_path', ?, false);"; // https://dba.stackexchange.com/questions/222080/postgresql-preparedstatement-to-execute-set-schema-command
            PreparedStatement setSearchPath = this.connection.prepareStatement(setSearchPathQuery);
            setSearchPath.setString(1, searchPath);
            setSearchPath.execute();
            //this.connection.commit();
            System.out.println("done");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}
