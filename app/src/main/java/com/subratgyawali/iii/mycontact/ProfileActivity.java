package com.subratgyawali.iii.mycontact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements DatabaseUpdatedListener{

    Contact contact;
    private static final int MY_PERMISSIONS_REQUEST_CALL = 100;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHandler(getApplicationContext());
        db.databaseUpdatedListener = this;
        if(getIntent().getSerializableExtra("contact") != null) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            ((TextView) findViewById(R.id.tv_profile_name)).setText(contact.getName());
            ((TextView) findViewById(R.id.tv_phone)).setText(contact.getPhone());
            ((TextView) findViewById(R.id.tv_email)).setText(contact.getEmail());
        }
        findViewById(R.id.cv_action_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(ProfileActivity.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL);
                    }
                    Toast.makeText(ProfileActivity.this, "permission requesting", Toast.LENGTH_SHORT).show();
                }else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+contact.getPhone()));
                    try{
                        startActivity(callIntent);
                    }catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });



    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CALL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);

                    callIntent.setData(Uri.parse("tel:"+contact.getPhone()));

                    try{
                        startActivity(callIntent);
                    }

                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.how

                } else {
//                    startInstalledAppDetailsActivity(ProfileActivity.this);
                    Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit){
            Intent intent = new Intent(this,AddContactActivity.class);
            intent.putExtra("contact",contact);
            startActivity(intent);
            finish();
        }else if (id == R.id.action_delete){
            db.deleteContact(contact);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void setDatabaseSuccess(String name, String phone, String email) {
        super.onBackPressed();
    }

    @Override
    public void setDatabaseError(String failureMessage) {

    }
}
