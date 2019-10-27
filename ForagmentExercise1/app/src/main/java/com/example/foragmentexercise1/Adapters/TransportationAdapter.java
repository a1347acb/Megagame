package com.example.foragmentexercise1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foragmentexercise1.Datas.TransportationData;
import com.example.foragmentexercise1.Interfaces.TransportationInterface;
import com.example.foragmentexercise1.R;

import java.util.Collections;
import java.util.List;


public class TransportationAdapter extends BaseAdapter{
    private Context context;
    private String titleName;
    private List<TransportationData> data;
    private int count = 0;
    private TransportationInterface face;

    public void setData(List<TransportationData> data) {
        Collections.sort(data);
        this.data = data;
    }

    public TransportationAdapter(Context context, String titleName, List<TransportationData> data){
        this.context = context;
        this.titleName = titleName;
        this.data = data;
        this.face = (TransportationInterface)context;
    }

    @Override
    public int getCount() {
        return count + 1;
    }

    @Override
    public Object getItem(int i) {
        if(i == 0){
            return titleName;
        }else{
            return data.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mView;
        if(i == 0){
            mView = LayoutInflater.from(context).inflate(R.layout.transportation_listview_headline,viewGroup,false);
            TextView TitleName = mView.findViewById(R.id.platFormName);
            TitleName.setText(titleName);
            ImageView image = mView.findViewById(R.id.away);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(count == 0){
                        count = data.size();
                        //调用回调刷新数据
                        face.itemShow(titleName);
                    }else{
                        count = 0;
                        //调用回调刷新数据
                        face.itemShow(titleName);
                    }
                }
            });
        }
        else{
            mView = LayoutInflater.from(context).inflate(R.layout.transportation_listview_items,viewGroup,false);
            TextView BusID,numberPeopleSum,Minutes,distance;//公交编号、公交当前承载人数、预计到站时间、离站点的距离
            BusID = mView.findViewById(R.id.BusID);
            numberPeopleSum = mView.findViewById(R.id.numberPeopleSum);
            Minutes = mView.findViewById(R.id.Minutes);
            distance = mView.findViewById(R.id.distance);

            BusID.setText(String.valueOf(data.get(i-1).getBusNumber()));
            numberPeopleSum.setText(String.valueOf(data.get(i-1).getNumberPeople()));
            Minutes.setText(String.valueOf(data.get(i-1).getMinuteTime()));
            distance.setText(String.valueOf(data.get(i-1).getDistance()));
        }
        return mView;
    }
}
