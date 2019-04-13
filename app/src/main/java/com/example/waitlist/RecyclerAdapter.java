package com.example.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.My_View_Holder> {

    public Context context;
    public Cursor cursor;

    public RecyclerAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.listitem,viewGroup,false);

        return new My_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder my_view_holder, int i) {

        if (!cursor.moveToPosition(i))
        {
            return;
        }

        String name=cursor.
                getString(cursor.getColumnIndex(WaitListContract.WaitListEntry.Column_Name));
        String number=cursor.
                getString(cursor.getColumnIndex(WaitListContract.WaitListEntry.Column_Amount));

        long id=cursor.getLong(cursor.getColumnIndex(WaitListContract.WaitListEntry._ID));

        my_view_holder.text_number.setText(number);
        my_view_holder.text_name.setText(name);
        my_view_holder.itemView.setTag(id);


    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void SwapCursor(Cursor MyCursor)
    {
        if (cursor != null)
        {
            cursor.close();
        }

        cursor=MyCursor;

        if (MyCursor != null)
        {
            notifyDataSetChanged();
        }
    }

    public class My_View_Holder extends RecyclerView.ViewHolder{

       public  TextView text_number,text_name;

        public My_View_Holder(@NonNull View itemView) {
            super(itemView);

            text_number=itemView.findViewById(R.id.GuestNumber_text);
            text_name=itemView.findViewById(R.id.GuestName_text);
        }
    }
}
