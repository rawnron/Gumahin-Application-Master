package com.ron.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EdituserActivity extends AppCompatActivity {

    EditText etusername, etpassword, etfullname;
    String username, password, fullname;
    DbHelper db;
    int success, userid;

    ArrayList<HashMap< String , String >> selecteduser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        db = new DbHelper(this);

        etusername = findViewById(R.id.ET_username);
        etpassword = findViewById(R.id.ET_password);
        etfullname = findViewById(R.id.ET_fullname);

        Intent intent = getIntent();

        userid = intent.getIntExtra(db.TBL_USER_ID, 0);
        selecteduser = db.getSelectedUserData(userid);

        etusername.setText(selecteduser.get(0).get(db.TBL_USER_USERNAME));
        etpassword.setText(selecteduser.get(0).get(db.TBL_USER_PASSWORD));
        etfullname.setText(selecteduser.get(0).get(db.TBL_USER_FULLNAME));

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_cancel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnSave:
                username = etusername.getText().toString();
                password = etpassword.getText().toString();
                fullname = etfullname.getText().toString();
                success = 3;

                if(username.equals("")){
                    etusername.setError("This field is required");
                    success --;
                }

                if(password.equals("")){
                    etpassword.setError("This field is required");
                    success --;
                }

                if(fullname.equals("")){
                    etfullname.setError("This field is required");
                    success --;
                }

                if(success == 3) {
                    HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_ID, String.valueOf(userid));
                    map_user.put(db.TBL_USER_USERNAME, username);
                    map_user.put(db.TBL_USER_PASSWORD, password);
                    map_user.put(db.TBL_USER_FULLNAME, fullname);
                    db.updateuser(map_user);

                    Toast.makeText(this, "User Successfully Updated", Toast.LENGTH_SHORT).show();
                    this.finish();
                }

                break;

            case R.id.btnCancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
