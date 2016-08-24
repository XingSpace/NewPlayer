package com.xing.app.newplayer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xing.app.newplayer.InterFace.OnTopViewListener;
import com.xing.app.newplayer.R;

/**
 * Created by wangxing on 16/7/31.
 * Top标题栏的数据
 */
public class TopView extends LinearLayout implements View.OnClickListener{

    private Context context;

    private TextView textView;

    private ImageView imageView1;

    private ImageView imageView2;

    private OnTopViewListener onTopViewListener;

    public TopView(Context context) {
        super(context,null);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    private void setup(){
        LayoutInflater.from(context).inflate(R.layout.view_top, this, true);
        textView = (TextView)findViewById(R.id.top);

        imageView1 = (ImageView)findViewById(R.id.single);
        imageView1.setTag(false);

        imageView2 = (ImageView)findViewById(R.id.random);
        imageView2.setTag(false);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    public void setTextView(String s){
        textView.setText(s);
    }

    public void setOnTopViewListener(OnTopViewListener onTopViewListener){
        this.onTopViewListener = onTopViewListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.single:
                if (onTopViewListener!=null){
                    onTopViewListener.onSingleClick(imageView1);
                }
                break;

            case R.id.random:
                if (onTopViewListener!=null){
                    onTopViewListener.onRandomClick(imageView2);
                }
                break;
        }
    }
}
