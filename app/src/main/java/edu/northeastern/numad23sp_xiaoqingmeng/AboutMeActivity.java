package edu.northeastern.numad23sp_xiaoqingmeng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        name.setText(R.string.convert_name);
        email.setText(R.string.convert_email);

    }
}