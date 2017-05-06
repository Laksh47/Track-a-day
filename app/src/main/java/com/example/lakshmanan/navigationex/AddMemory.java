package com.example.lakshmanan.navigationex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddMemory extends AppCompatActivity {

    public MyHelper db = null;
    public EditText memo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MyHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent memo = new Intent(AddMemory.this, RetrieveMemory.class);
                startActivity(memo);
            }
        });
    }

    public void addMemory(View v){
        memo = (EditText)findViewById(R.id.editText);
        String temp = memo.getText().toString();
        memo.setText("");
        Moment m = new Moment(temp);
        db.makeMemory(m);
    }

}
