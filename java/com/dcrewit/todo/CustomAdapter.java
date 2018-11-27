package com.dcrewit.todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<Task> messageActivityList;
    private List<Task> listOfTasksSelected;
    private List<View> listSelectedRows;


    public CustomAdapter(Context mContext, List<Task> messageActivityList) {
        this.mContext = mContext;
        this.messageActivityList = messageActivityList;
        listSelectedRows = new ArrayList<>();
        listOfTasksSelected=new ArrayList<>();

    }
    public void handleLongPress(int i, View view){
        if(listSelectedRows.contains(view)){
            listSelectedRows.remove(view);
            listOfTasksSelected.remove(i);
            view.setBackgroundResource(R.color.White);
        }else{
            listOfTasksSelected.add(messageActivityList.get(i));
            listSelectedRows.add(view);
            view.setBackgroundResource(R.color.DarkGray);
        }
    }

    @Override
    public int getCount() {
        return messageActivityList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageActivityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(mContext,R.layout.custom_listview,null);
        TextView todoitem= v.findViewById(R.id.todoitem);
        TextView date_time= v.findViewById(R.id.textView_date_time);
        todoitem.setText(messageActivityList.get(i).getMessage());
        date_time.setText(messageActivityList.get(i).getDatetime());
        return v;
    }
    public List<Task> getListOfTasksSelected(){
        return listOfTasksSelected;
    }
    public void removeSelectedPersons(){
        messageActivityList.removeAll(getListOfTasksSelected());
        for(View view : listSelectedRows)
            view.setBackgroundResource(R.color.colorWhite);
        listSelectedRows.clear();
    }
}

