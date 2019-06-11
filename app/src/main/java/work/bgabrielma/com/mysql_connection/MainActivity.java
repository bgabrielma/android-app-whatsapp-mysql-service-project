package work.bgabrielma.com.mysql_connection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import work.bgabrielma.com.database.AppDatabase;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase appDatabase = new AppDatabase();
        appDatabase.createConnection();
    }
}
