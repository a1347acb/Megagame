package com.example.foragmentexercise1.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.foragmentexercise1.Datas.oneListData;
import com.example.foragmentexercise1.Interfaces.RechargeInterfaces;
import com.example.foragmentexercise1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class oneListAdapter extends BaseAdapter{
    public Map<Integer,String> getPlateNumberList() {
        return plateNumberList;
    }

    private Map<Integer,String> plateNumberList = new HashMap<>();
    private Context context;
    private List<oneListData> data;
    private View mView;
    private RechargeInterfaces rechargeForm;

    private TextView number;//数字序号TextView控件
    private ImageView carLogoImage;//车标显示图片控件
    private TextView plateNumber;//车牌信息显示文本控件
    private TextView owner;//车主姓名显示文本控件
    private TextView balance;//余额显示文本控件
    private Button recharge;//充值按钮
    private LinearLayout layout;//子项父容器
    private CheckBox select;

    public oneListAdapter(Context context, List<oneListData> data, RechargeInterfaces recharge){
        this.context = context;
        this.data = data;
        this.rechargeForm = recharge;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        mView = LayoutInflater.from(context).inflate(R.layout.uid_manage_items,viewGroup,false);
        number = mView.findViewById(R.id.number);
        carLogoImage = mView.findViewById(R.id.carLogoImage);
        plateNumber = mView.findViewById(R.id.plateNumber);
        owner = mView.findViewById(R.id.owner);
        balance = mView.findViewById(R.id.balance);
        recharge = mView.findViewById(R.id.recharge);
        layout = mView.findViewById(R.id.item);
        select = mView.findViewById(R.id.select);

        select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    plateNumberList.put(i + 1,data.get(i).getPlateNumber());
                }else{
                    plateNumberList.remove(data.get(i).getPlateNumber());
                }
            }
        });

        //加载图片
        carLogoImage.setImageResource(data.get(i).getImageSrc());
        number.setText(String.valueOf(i+1));
        plateNumber.setText(data.get(i).getPlateNumber());
        owner.setText(data.get(i).getOwner());
        balance.setText(String.valueOf(data.get(i).getBalance()));
        if(data.get(i).getBalance() < 200){
            layout.setBackgroundColor(context.getResources().getColor(R.color.rechargeFormItemNoBackgroundColor));
        }

        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回调活动中的在弹窗上面显示数据的方法
                @SuppressLint("UseSparseArrays") Map<Integer,String> dt = new HashMap<>();
                dt.put(data.get(i).getNumber(),data.get(i).getPlateNumber());
                rechargeForm.plateNumber(dt);
            }
        });
        return mView;
    }
}
