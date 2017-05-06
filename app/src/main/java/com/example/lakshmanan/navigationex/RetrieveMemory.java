package com.example.lakshmanan.navigationex;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;

public class RetrieveMemory extends AppCompatActivity {

    MyHelper db = null;
    List<Moment> list ;
    ArrayAdapter<String> arrayAdapter;
    ListView l;
    ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new MyHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_memory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent memo = new Intent(RetrieveMemory.this, AddMemory.class);
                startActivity(memo);
            }
        });
        list = db.retrieveAllMemories();
        l = (ListView) findViewById(R.id.listView_memories);
        Log.d("Size:",""+list.size());
        if(list.size() != 0) {
            myList = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                myList.add(list.get(i).getMoment());
            }
            Log.d("Retrieved memo", myList.get(0));
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.rowlist, R.id.rowtextview, myList);
            l.setAdapter(arrayAdapter);
            registerForContextMenu(l);
        }
        else {
            ArrayList<String> temp = new ArrayList<String>();
            temp.add("Make your first ever memory");
            ArrayAdapter temparrayAdapter = new ArrayAdapter<String>(this, R.layout.rowlist, R.id.rowtextview, temp);
            l.setAdapter(temparrayAdapter);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");//groupId, itemId, order, title
        //menu.add(0, v.getId(), 0, "SMS");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Log.d("title:", ""+item.getTitle());
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            int i = info.position;
            Log.d("Position:",""+i);
            Log.d("Id:", "" + info.id);
            db.eraseMemory(list.get(i));
            list.remove(i);
            myList.remove(i);
            arrayAdapter.notifyDataSetChanged();
        }
        return false;
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
