package com.subratgyawali.iii.mycontact;
/**
 * Created by Subrat Gyawali on 11/26/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CODE = 34;
    ListView listView;
    ArrayAdapter<Contact> adapter;
    List<Contact> loadContact = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.lv_contact);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        db = new DatabaseHandler(MainActivity.this);
        loadContact = db.getAllContact();

        adapter = new ArrayAdapter<Contact>(getApplicationContext(), R.layout.item_contact, loadContact) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getApplicationContext(), R.layout.item_contact, null);
                }
                Contact c = getItem(position);
                ((TextView) convertView.findViewById(R.id.tv_name)).setText(c.getName());

                switch (position % 6) {
                    case 0:
                        ((ImageView) convertView.findViewById(R.id.iv_person)).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                        break;
                    case 1:
                        ((ImageView) convertView.findViewById(R.id.iv_person)).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        break;
                    case 2:
                        ((ImageView) convertView.findViewById(R.id.iv_person)).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        break;
                    case 3:
                        ((ImageView) convertView.findViewById(R.id.iv_person)).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        break;
                    case 4:
                        ((ImageView) convertView.findViewById(R.id.iv_person)).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.orange));
                        break;
                    case 5:
                        ((ImageView) convertView.findViewById(R.id.iv_person)).setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.pink));
                        break;
                }
                return convertView;
            }
        };
        sort();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "position selected ="+ i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                Contact c = (Contact) adapterView.getItemAtPosition(i);
                intent.putExtra("contact", c);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onRestart() {
        Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show();
        loadContact.clear();
        loadContact.addAll(db.getAllContact());
        sort();
        adapter.notifyDataSetChanged();
        super.onRestart();
    }

    private void sort() {
        adapter.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return (contact.getName()).compareToIgnoreCase(t1.getName());
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


    @Override
    public void onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            //finish();
            System.exit(0);
        }
    }

}
