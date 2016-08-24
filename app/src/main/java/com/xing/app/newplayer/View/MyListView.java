package com.xing.app.newplayer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.xing.app.newplayer.InterFace.OnMyListViewListener;
import com.xing.app.newplayer.R;

/**
 * Created by wangxing on 16/7/31.
 */
public class MyListView extends LinearLayout implements AdapterView.OnItemClickListener{

    private Context context;

    private ListView listView;

    private OnMyListViewListener onMyListViewListener;

    public MyListView(Context context) {
        super(context,null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    private void setup(){
        LayoutInflater.from(context).inflate(R.layout.view_list, this, true);

        listView = (ListView)findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    public void setAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);
    }

    public void setOnMyListViewListener(OnMyListViewListener onMyListViewListener){
        this.onMyListViewListener = onMyListViewListener;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onMyListViewListener != null){
            onMyListViewListener.onItemClick(position);
        }
    }

}
