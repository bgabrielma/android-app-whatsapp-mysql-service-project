package work.bgabrielma.com.tasks;

import android.os.AsyncTask;
import work.bgabrielma.com.database.DatabaseBuilder;
import work.bgabrielma.com.interfaces.ITaskDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
    protected boolean error = false;

    //Result SQL Object
    protected ArrayList<HashMap<String,Object>> rows =
            new ArrayList<>();

    public TaskDatabase(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    @Override
    protected void onPreExecute() {
        try {
            Class.forName(JBDC_DRIVER);

            // Reset params
            error = false;
            rows.clear();

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
    public void prepare(DatabaseBuilder sql) {
        if(sql.build().isEmpty())
            throw new IllegalArgumentException("Please provide a valid SQL querie");

        this.sql = sql.build();
    }

    private void executeStatement() {
        try {
            // Open connection
            connect();

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(this.sql);

            ResultSetMetaData metaData = resultSet.getMetaData();

            /**
             *
             * Do stuff with data received
             *
             */
            if(resultSet != null) {
                int colCount = metaData.getColumnCount();

                ArrayList<String> cols = new ArrayList<>();

                // Get and register into cols's list the name of column
                for (int index = 1; index <= colCount; index++)
                    cols.add(metaData.getColumnName(index));

                // For each row
                while (resultSet.next()) {

                    // For each column, obtain its respective value
                    HashMap<String,Object> row = new HashMap<>();

                    for (String colName : cols) {
                        Object val = resultSet.getObject(colName);
                        row.put(colName,val);
                    }
                    // Add rows
                    rows.add(row);
                }
            }

            for(int i = 0; i < rows.size() ; i++) {
                System.out.println("---------------------------");
                System.out.println("ROW " + (i +1) + "\n");
                for(Entry<String, Object> row : rows.get(i).entrySet()) {
                    System.out.println(row.getKey() + " - " + (row.getValue().getClass().getName()) + " --- " + row.getValue());
                }
            }

            // Close connection
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            this.error = true;
        }
    }

    /**
     *
     * Getters and setters
     *
     */
}
