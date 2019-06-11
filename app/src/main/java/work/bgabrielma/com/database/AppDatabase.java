package work.bgabrielma.com.database;
import work.bgabrielma.com.interfaces.ITaskDatabase;
import work.bgabrielma.com.mysql_connection.BuildConfig;
import work.bgabrielma.com.tasks.TaskDatabase;

import java.sql.ResultSet;

public class AppDatabase implements ITaskDatabase {

    // Database url connection string
    protected static final String DB_URL = BuildConfig.dbUrl;

    //  Database credentials
    protected static final String USER = BuildConfig.user;
    protected static final String PASS = BuildConfig.pass;

    // Task Database
    TaskDatabase taskDatabase = new TaskDatabase(DB_URL, USER, PASS);

    public AppDatabase() {

        // Register events
        taskDatabase.setOnTaskDatabaseListener(this);
    }

    public void createConnection() {

        // Configure queries
        taskDatabase.prepare("SELECT name from users");
        taskDatabase.execute();
    }

    @Override
    public void OnFinishTaskDatabaseHandler(ResultSet resultSet) {
        // do stuff
    }
}
