package com.example.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText edit_number,edit_name;
    Button Add;
    RecyclerView recyclerView;
    SQLiteDatabase liteDatabase;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       WaitListDBHelper  waitListDBHelper=new WaitListDBHelper(this);
        liteDatabase=waitListDBHelper.getWritableDatabase();

        Add=findViewById(R.id.btn_Add);

        edit_number=findViewById(R.id.GuestNumber);
        edit_name=findViewById(R.id.GuestName);

        recyclerView=findViewById(R.id.Recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new RecyclerAdapter(this,GetAllData());
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                RemoveItem( (long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem();
            }
        });

    }


    public void AddItem()
    {
        if (edit_number.getText().toString().trim().length()==0||
                edit_name.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"No Data To Added",Toast.LENGTH_LONG).show();
            return;
        }




        String number=edit_number.getText().toString();
        String name=edit_name.getText().toString();


        ContentValues values=new ContentValues();

        values.put(WaitListContract.WaitListEntry.Column_Amount,number);
        values.put(WaitListContract.WaitListEntry.Column_Name,name);


        liteDatabase.insert(
                WaitListContract.WaitListEntry.Table_Name,
                null
                 ,values);

        adapter.SwapCursor(GetAllData());

        edit_name.getText().clear();
        edit_number.getText().clear();



    }
    private  void  RemoveItem(long id)
    {
        liteDatabase.delete(WaitListContract.WaitListEntry.Table_Name,
                WaitListContract.WaitListEntry._ID + "="+ id,null);

        adapter.SwapCursor(GetAllData());

    }

    public Cursor GetAllData()

    {
        return liteDatabase.query
                (
                        WaitListContract.WaitListEntry.Table_Name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        WaitListContract.WaitListEntry.Column_TIMESTAMP +" DESC"
                );
    }
}
