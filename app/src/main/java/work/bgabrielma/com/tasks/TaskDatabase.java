package work.bgabrielma.com.tasks;

import android.os.AsyncTask;
import work.bgabrielma.com.interfaces.ITaskDatabase;

import java.sql.*;

public class TaskDatabase extends AsyncTask<String, Integer, String> {

    // Connection and statements
    protected Connection conn = null;
    protected Statement stmt = null;
    protected ResultSet resultSet = null;

    // JBDC MySQL Driver
    protected static final String JBDC_DRIVER  = "com.mysql.jdbc.Driver";

    // Operation type

    //Task Database Event listener
    protected ITaskDatabase iTaskDatabaseListener;

    // Database credentials
    protected String DB_URL;
    protected String USER;
    protected String PASS;

    //Prepare SQL
    protected String sql;

    public TaskDatabase(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    @Override
    protected void onPreExecute() {
        try {
            Class.forName(JBDC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setOnTaskDatabaseListener(ITaskDatabase iTaskDatabaseListener) {
        this.iTaskDatabaseListener = iTaskDatabaseListener;
    }

    @Override
    protected void onPostExecute(String s) {

        // Trigger Event handler
        iTaskDatabaseListener.OnFinishTaskDatabaseHandler(resultSet);
    }

    @Override
    protected String doInBackground(String... params) {
        executeStatement();
        return null;
    }

    public void connect() {
        if(conn == null) {
            try {
                conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * Querie builder system
     *
     */

    // Querie construct phase
    public void prepare(String sql) {
        if(sql.isEmpty())
            throw new IllegalArgumentException("Please provide a valid SQL querie");

        this.sql = sql;
    }

    private void executeStatement() {
        try {
            // Open connection
            connect();

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(this.sql);

            /**
             *
             * Do stuff with data received
             *
             */

            // Close connection
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Getters and setters
     *
     */
}
