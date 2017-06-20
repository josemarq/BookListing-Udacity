package josemarq.booklisting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Find the View Button Search
            final Button buttonSearch = (Button) findViewById(R.id.search_button);

            // Set a click listener on that View
            buttonSearch.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when Search Button was clicked.
                @Override
                public void onClick(View v) {


                    EditText termView = (EditText) findViewById(R.id.term);
                    String term = termView.getText().toString();
                    Intent i = new Intent(getApplicationContext(), BooksActivity.class);
                    //Pass the value of the TextView to the BooksActivity
                    i.putExtra("QueryTerm", term);
                    startActivity(i);
                }
            });
        }

}