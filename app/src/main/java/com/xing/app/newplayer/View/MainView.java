package com.xing.app.newplayer.View;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xing.app.newplayer.InterFace.OnBottomViewListener;
import com.xing.app.newplayer.InterFace.OnMediaPlaying;
import com.xing.app.newplayer.InterFace.OnMyListViewListener;
import com.xing.app.newplayer.InterFace.OnTopViewListener;
import com.xing.app.newplayer.R;
import com.xing.app.newplayer.Resource.ListAdapter;
import com.xing.app.newplayer.Resource.StaticResource;
import com.xing.app.newplayer.ToolKit.InitResource;
import com.xing.app.newplayer.ToolKit.MyMedia;

/**
 * Created by wangxing on 16/7/31.
 * 显示的主界面
 */
public class MainView extends FragmentActivity implements OnBottomViewListener,OnMediaPlaying,OnTopViewListener,OnMyListViewListener{

    private MyMedia myMedia;

    private BottomView bottomView;

    private TopView topView;

    private MyListView myListView;

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
    }

    private void init(){

        //以线程的方式开启歌曲列表搜索，并且设置到mymedia
        new Thread(){
            @Override
            public void run() {
                super.run();
                InitResource.findMusicResource(MainView.this);//从系统中找出歌曲
                myMedia = new MyMedia();
                myMedia.setOnMediaPlaying(MainView.this);//获取歌曲时间信息的接口
                myMedia.setMusicList(StaticResource.getMusicModelList());//设置好被播放歌曲的列表
                listAdapter = new ListAdapter(MainView.this,StaticResource.getMusicModelList());
                myListView = (MyListView)findViewById(R.id.mylist);
                myListView.setOnMyListViewListener(MainView.this);
                myListView.setAdapter(listAdapter);
            }
        }.start();

        bottomView = (BottomView)findViewById(R.id.bottonview);
        bottomView.setOnBottomViewListener(this);

        topView = (TopView)findViewById(R.id.topview);
        topView.setOnTopViewListener(this);

    }

    /**
     * 当 “上一首” 按钮触发后
     */
    @Override
    public void onBackClick() {
        if(myMedia != null){
            myMedia.previous();
        }
    }

    @Override
    public void onNextClick() {
        if (myMedia != null){
            myMedia.next();
        }
    }

    @Override
    public void onControlClick() {
        if (myMedia != null){//确认播放器已经加载好了
            if (myMedia.isPlaying()){
                //到这里就表示，已经有歌曲在播放
                myMedia.pause();
            }else {
                //到这里表示，还没有歌曲被播放
                if (myMedia.getIsCache()){//如果已经有了缓存就直接播放，没有就调用firstplay
                    myMedia.start();
                }else {
                    myMedia.firstPlay();
                }
            }
        }
    }

    @Override
    public void onSeekbarListener(SeekBar seekBar, int progress, boolean fromUser, TextView textView) {
        if (myMedia.getIsCache()){
            //这一段看着冗余很高。。。我很不满意 (生气脸  2016-08-15
            //改还是不改呢。。。 (改起来好麻烦的样子内，怎么改好呢  2016-08-17
            //不改了就这样。。。反正用户也看不到 你特么来打我呀 (假装啥都不知道的样子  2016-08-20
            int musicTime = progress / 1000;
            String m = "";
            int timeM = musicTime /60;
            int timeS = musicTime %60;
            if (timeM < 10){
                m += 0 +""+ timeM;
            }
            if (timeS < 10){
                m += ":"+0+""+timeS;
            }else {
                m += ":"+timeS;
            }
            textView.setText(m);//目的是，在拖动过程中，更新时间数据  2016-08-15
        }else {
            seekBar.setProgress(0);
        }
    }

    @Override
    public void onSeekbarStart(SeekBar seekBar) {
        if (!myMedia.getIsCache()){//如果没有歌曲被缓存就直接放弃继续执行
            return;
        }
        if (myMedia.isPlaying()){
            myMedia.pause();
        }
    }

    @Override
    public void onSeekbarStop(SeekBar seekBar) {
        if(!myMedia.getIsCache()){
            return;
        }
        myMedia.seekTo(seekBar.getProgress());
        myMedia.start();
    }

    @Override
    public void OnStartPlay(String name,String maxTime, String nowTime, int duration,int progress) {
        bottomView.setNowTime(nowTime);
        bottomView.setMaxtime(maxTime);
        bottomView.setSeekBarMax(duration);
        bottomView.setSeekBarProgress(progress);
        bottomView.play();

        topView.setTextView(name);
    }

    @Override
    public void OnPlaying(String nowTime,int progress) {
        bottomView.setNowTime(nowTime);
        bottomView.setSeekBarProgress(progress);
    }

    @Override
    public void OnPausePlay() {
        bottomView.pause();
    }

    @Override
    public void onSingleClick(ImageView imageView) {
        boolean b = (boolean)imageView.getTag();
        if (b){
            imageView.setImageResource(R.drawable.single);
            imageView.setTag(false);
            myMedia.setIsSingle(false);
        }else {
            imageView.setImageResource(R.drawable.single_press);
            imageView.setTag(true);
            myMedia.setIsSingle(true);
        }
    }

    @Override
    public void onRandomClick(ImageView imageView) {
        boolean b = (boolean)imageView.getTag();
        if (b){
            imageView.setImageResource(R.drawable.random);
            imageView.setTag(false);
            myMedia.setIsRandom(false);
        }else {
            imageView.setImageResource(R.drawable.random_press);
            imageView.setTag(true);
            myMedia.setIsRandom(true);
        }
    }

    @Override
    public void onItemClick(int position) {
        myMedia.play(position);
        Log.d("itemclick","executed");
    }
}
