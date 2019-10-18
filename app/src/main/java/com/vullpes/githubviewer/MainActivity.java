package com.vullpes.githubviewer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private EditText editUser;
    private Button btnSearch;
    private ProgressBar progressBar;
    private User user;
    private AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editUser = findViewById(R.id.editUser);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.INVISIBLE);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.length() != 0){
                    LoadUser load = new LoadUser();
                    load.execute(editUser.getText().toString());
                }else{
                    Toast.makeText(view.getContext(), "Please, type an user before search",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class LoadUser extends AsyncTask<String,String,Object> {
        boolean networkError; //check if it is a network error
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(String... strings) {
            String json = HttpConection.getDados(strings[0]);
            if(json !=null && json.equals("Connection Error")){ //if there is no connection throws this exception
                networkError = true;
                return null;
            }else if (json ==null || json.equals("[]/n")){ //if there is no user throws this exception
                networkError = false;
                return null;
            }else{
                user = UserParcer.parseDados(json);
                return user;
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            progressBar.setVisibility(View.INVISIBLE);
            if(o == null && networkError){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning");
                builder.setMessage("A network error has ocurred. Check your internet connection and try again later.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                alert = builder.create();
                alert.show();
            }else if(o == null && networkError == false){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning");
                builder.setMessage("User not found. Please enter another name.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alert = builder.create();
                alert.show();

            }
            else{
                Intent intent = new Intent(MainActivity.this,UserDetailActivity.class);
                intent.putExtra("numero",10);
                intent.putExtra("user",(Serializable) o);
                startActivity(intent);
            }
            super.onPostExecute(o);
        }
    }
}
