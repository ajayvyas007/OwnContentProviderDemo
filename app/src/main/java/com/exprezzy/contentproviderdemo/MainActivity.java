package com.exprezzy.contentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //---add a book---
                ContentValues values = new ContentValues();

                values.put(BooksProvider.TITLE,((EditText)
                        findViewById(R.id.txtTitle)).getText().toString());

                values.put(BooksProvider.ISBN, ((EditText)
                        findViewById(R.id.txtISBN)).getText().toString());

                Uri uri = getContentResolver().insert(
                        BooksProvider.CONTENT_URI, values);

                Toast.makeText(getBaseContext(),uri.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        Button btnRetrieve = (Button) findViewById(R.id.btnRetrieve);

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                //---retrieve the titles---
                Uri allTitles = Uri.parse(
                        "content://net.learn2develop.provider.Books/books");
                Cursor c = managedQuery(allTitles, null, null, null,
                        "title desc");
                if (c.moveToFirst()) {
                    do{
                        Toast.makeText(getBaseContext(), "ContentProviders "+
                                c.getString(c.getColumnIndex(
                                        BooksProvider._ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BooksProvider.TITLE)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BooksProvider.ISBN))+"", Toast.LENGTH_LONG).show();
                    } while (c.moveToNext());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
