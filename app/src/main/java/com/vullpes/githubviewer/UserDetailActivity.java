package com.vullpes.githubviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;



public class UserDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private ImageView imageView;
    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        User user = (User) getIntent().getSerializableExtra("user");
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageUser);
        userName = findViewById(R.id.txtUserName);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bitmap bitmap = Utils.byteArrayToBitmap(user.getPicByte());
        imageView.setImageBitmap(bitmap);
        userName.setText(user.getNome());

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,user.getRepositorios(),
        R.layout.list_item,new String[]{"First Line","Second Line"},new int[]{R.id.text1,R.id.text2});
        listView.setAdapter(simpleAdapter);

    }

}
