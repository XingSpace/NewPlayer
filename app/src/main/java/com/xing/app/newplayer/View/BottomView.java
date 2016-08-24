package com.xing.app.newplayer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xing.app.newplayer.InterFace.OnBottomViewListener;
import com.xing.app.newplayer.R;

/**
 * Created by wangxing on 16/7/31.
 * 下方控制层
 */
public class BottomView extends LinearLayout implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    private Context context;

    private SeekBar seekBar;

    private TextView textView1,textView2;

    private ImageView imageView1,imageView2,imageView3;

    private OnBottomViewListener onBottomViewListener;

    public BottomView(Context context) {
        super(context,null);
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setup();
    }

    private void setup(){
        LayoutInflater.from(context).inflate(R.layout.view_bottom, this, true);

        seekBar = (SeekBar)findViewById(R.id.seekbar);

        textView1 = (TextView)findViewById(R.id.nowtime);

        textView2 = (TextView)findViewById(R.id.maxtime);

        imageView1 = (ImageView)findViewById(R.id.back);

        imageView2 = (ImageView)findViewById(R.id.control);

        imageView3 = (ImageView)findViewById(R.id.next);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(this);
    }

    public void setNowTime(String s){
        textView1.setText(s);
    }

    public void setMaxtime(String s){
        textView2.setText(s);
    }

    /**
     * 歌曲开始播放时，使用此方法完成图标变更
     */
    public void play(){
        imageView2.setImageResource(R.drawable.pause);
    }

    /**
     * 歌曲暂停时
     */
    public void pause(){
        imageView2.setImageResource(R.drawable.play);
    }

    /**
     * @param duration 拖动条的最大限制
     */
    public void setSeekBarMax(int duration){
        this.seekBar.setMax(duration);
    }

    /**
     * @param progress 拖动条当前位置
     */
    public void setSeekBarProgress(int progress){
        this.seekBar.setProgress(progress);
    }

    public void setOnBottomViewListener(OnBottomViewListener onBottomViewListener){
        this.onBottomViewListener = onBottomViewListener;
    }

    /**
     * 改 改 改。。。这特么要改。。。
     * 楼上的。。。我改完了
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:
                if (onBottomViewListener !=null){
                    onBottomViewListener.onBackClick();
                }
                break;

            case R.id.next:
                if (onBottomViewListener !=null){
                    onBottomViewListener.onNextClick();
                }
                break;

            case R.id.control:
                if (onBottomViewListener !=null){
                    onBottomViewListener.onControlClick();
                }
                break;
        }

    }

    /**
     * 改完了。。。
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (onBottomViewListener !=null){
            onBottomViewListener.onSeekbarListener(seekBar,progress,fromUser,textView1);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (onBottomViewListener !=null){
            onBottomViewListener.onSeekbarStart(seekBar);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (onBottomViewListener !=null){
            onBottomViewListener.onSeekbarStop(seekBar);
        }
    }
}
