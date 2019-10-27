package com.example.foragmentexercise1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foragmentexercise1.Datas.recordData;
import com.example.foragmentexercise1.R;

import java.util.List;

public class RecordAdapter extends BaseAdapter {
    private List<recordData> data;
    private View mView;
    private Context context;
    public RecordAdapter(Context context,List<recordData> data) {
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
        mView = LayoutInflater.from(context).inflate(R.layout.record_items,viewGroup,false);
        TextView PlateNumber = mView.findViewById(R.id.recordPlateNumber);
        TextView Money = mView.findViewById(R.id.recordMoney);
        TextView DateTime = mView.findViewById(R.id.recordDateTime);

        PlateNumber.setText(data.get(data.size() - i - 1).getPlateNumber());
        Money.setText(String.valueOf(data.get(data.size() - i - 1).getMoney()));
        DateTime.setText(data.get(data.size() - i - 1).getDateTime());
        return mView;
    }
}
