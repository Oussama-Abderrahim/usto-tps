package congress;

import congress.organisation.SpeakerFormPanel;

import javax.swing.*;
import java.sql.*;


public class DatabaseManager
{
    private static DatabaseManager databaseManager;

    private static final String DB_FILE_NAME = "congress.sqlite";


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
        String attandantQuery = "CREATE  TABLE IF NOT EXISTS \"Attandant\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"Name\" VARCHAR, \"Email\" VARCHAR, \"Occupation\" VARCHAR)";
        String speakerQuery = "CREATE TABLE IF NOT EXISTS \"Speaker\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"Name\" VARCHAR, \"Description\" VARCHAR)";
        String confQuery = "CREATE TABLE IF NOT EXISTS \"Conference\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"Title\" VARCHAR, \"Day\" INTEGER DEFAULT 0, \"Time\" VARCHAR, \"Speaker\"  INTEGER NOT NULL ,\n" +
                "FOREIGN KEY (Speaker) REFERENCES Speaker(id))";

        String congressQuery = "CREATE TABLE IF NOT EXISTS \"Congres\" (\"Nom\" VARCHAR NOT NULL , \"Date_Debut\" DATETIME, \"Date_Fin\" DATETIME, \"Date_Fin_Inscription\" DATETIME, \"Lieu\" VARCHAR, \"Citation\" VARCHAR)";

        executeUpdate(attandantQuery);
        executeUpdate(speakerQuery);
        executeUpdate(confQuery);
        executeUpdate(congressQuery);
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

    public ResultSet fetchCongressData()
    {
        String QUERY = "SELECT * FROM Congres";

        return executeQuery(QUERY);
    }
    public ResultSet fetchConferences(int jour)
    {
        String QUERY = "SELECT Conference.Time, Conference.Title, Speaker.Name \n" +
                "FROM Conference, Speaker\n" +
                "WHERE Conference.Speaker = Speaker.id\n" +
                "AND Conference.day = "+jour+";";

        return executeQuery(QUERY);
    }

    public ResultSet fetchSpeakers()
    {
        String QUERY = "SELECT * FROM Speaker";

        return executeQuery(QUERY);
    }

    public void insertSpeakers(String no, String nom, String prenom, String bio)
    {
        String QUERY = "INSERT INTO Speaker (id, Name, Description) VALUES ('" + no + "', '" + nom + " " + prenom + "', '" + bio + "')";
        executeUpdate(QUERY);
    }

    public void updateCongressData(String nom, String jourDu, String moisDu, String anneeDu, String jourAu, String moisAu, String anneeAu, String jourInscription, String moisInscription, String anneeInscription, String adresse, String citation)
    {
        String QUERY = "UPDATE Congres SET " +
                "Nom = '" + nom + "', " +
                "Date_Debut = '" + jourDu + "/" + moisDu + "/" + anneeDu + "', " +
                "Date_Fin = '" + jourAu + "/" + moisAu + "/" + anneeAu + "', " +
                "Date_Fin_Inscription = '" + jourInscription + "/" + moisInscription + "/" + anneeInscription + "', " +
                "Lieu = '" + adresse + "', " +
                "Citation = '" + citation + "'";
    }
}