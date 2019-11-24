import java.sql.*;


public class DatabaseManager
{
    private static DatabaseManager databaseManager;

    private static final String DB_FILE_NAME = "irdm.sqlite";


    //Database Variables
    private Connection connection;
    private Statement statement;
    private String databaseURL = "jdbc:sqlite::resource:"+ DB_FILE_NAME;

    public static DatabaseManager getInstance()
    {
        if(databaseManager == null)
        {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }
    private DatabaseManager()
    {
        connect();
    }

    //Set connection
    private void connect()
    {
        try
        {
            connection = DriverManager.getConnection(databaseURL);
            statement = connection.createStatement();
            //statement.setQueryTimeout(30);

            checkTables();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void checkTables()
    {
        String imagesDBQuery = "CREATE TABLE IF NOT EXISTS `images` (\"ID\" INT PRIMARY KEY, " +
                "                                                         \"path\" VARCHAR(255)," +
                "                                                         \"description\" TEXT);";

        executeUpdate(imagesDBQuery);
    }

    public ResultSet executeQuery(String query)
    {
        try
        {
            return statement.executeQuery(query);
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void executeUpdate(String query) {
        try
        {
            statement.executeUpdate(query);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            if(connection != null)
                connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet fetchAllImages()
    {
        String QUERY = "SELECT * FROM images";

        return executeQuery(QUERY);
    }

    public void insertSpeakers(String nom, String prenom, String bio)
    {
        String QUERY = "INSERT INTO Speaker (Name, Description) VALUES ('" + nom + " " + prenom + "', '" + bio + "')";
        executeUpdate(QUERY);
    }

}