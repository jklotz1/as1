package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //when add button is pressed bring up the page to add in an entry
        Button addLogActivity = (Button) findViewById(R.id.btnAdd);
        addLogActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this,
                        AddActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
            }
        });

        //when view button is selected show the page of all entries
        Button viewLogActivity = (Button) findViewById(R.id.btnViewEdit);
        viewLogActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this,
                        ViewActivity.class);
                startActivity(intent);
            }
        });

    }

}
