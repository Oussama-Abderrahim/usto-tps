package irdm;

import java.sql.*;


public class DatabaseManager {
    private static DatabaseManager databaseManager;

    private static final String DB_FILE_NAME = "irdm.sqlite";


    //Database Variables
    private Connection connection;
    private Statement statement;
    private String databaseURL = "jdbc:sqlite::resource:" + DB_FILE_NAME;

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    private DatabaseManager() {
        connect();
    }

    //Set connection
    private void connect() {
        try {
            connection = DriverManager.getConnection(databaseURL);
            statement = connection.createStatement();
            //statement.setQueryTimeout(30);

            checkTables();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void checkTables() {
        String imagesDBQuery = "CREATE TABLE IF NOT EXISTS \"images\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"path\"\tTEXT UNIQUE,\n" +
                "\t\"COLOR_DESCRIPTOR\"\tTEXT,\n" +
                "\t\"TEXTURE_DESCRIPTOR\"\tTEXT\n" +
                ");";

        executeUpdate(imagesDBQuery);
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void disconnect() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet fetchAllImages() {
        String QUERY = "SELECT * FROM images";

        return executeQuery(QUERY);
    }

    public int insertImage(String path, String descriptor1, String descriptor2) {
        String QUERY = "INSERT INTO \"main\".\"images\" " +
                "               (\"path\", \"COLOR_DESCRIPTOR\", \"TEXTURE_DESCRIPTOR\") " +
                "               VALUES ('" + path + "', '" + descriptor1 + "', '" + descriptor2 + "');";
        return executeUpdate(QUERY);
    }

}