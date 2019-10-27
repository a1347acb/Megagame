package com.example.foragmentexercise1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foragmentexercise1.Datas.DialogMessageItemData;
import com.example.foragmentexercise1.R;

import java.util.List;

public class DialogMessageItemAdapter extends BaseAdapter {
    private Context context;
    private List<DialogMessageItemData> data;
    private View mView;
    public DialogMessageItemAdapter(Context context, List<DialogMessageItemData> data){
        this.data = data;
        this.context = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mView = LayoutInflater.from(context).inflate(R.layout.bus_message_list_item,viewGroup,false);
        TextView number = mView.findViewById(R.id.number);
        TextView busNumber = mView.findViewById(R.id.busNumber);
        TextView peopleNubmer = mView.findViewById(R.id.peopleNumber);
        number.setText(data.get(i).getNumber());
        busNumber.setText(data.get(i).getBusNumber());
        peopleNubmer.setText(data.get(i).getPeopleNumber());
        return mView;
    }
}
