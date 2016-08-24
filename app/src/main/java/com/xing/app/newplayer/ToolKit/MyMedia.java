package com.xing.app.newplayer.ToolKit;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.xing.app.newplayer.InterFace.OnMediaPlaying;
import com.xing.app.newplayer.Resource.MusicModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangxing on 16/8/9.
 * 顺序歌曲列表：
 *            歌曲A
 *            歌曲B
 *            歌曲C
 *            歌曲D
 * 随机歌曲列表：
 *            歌曲B
 *            歌曲D
 *            歌曲C
 *            歌曲A
 * BUG描述：假设在随机播放时播放到歌曲A，这时取消随机同时按下切歌键
 * 按理说这时歌曲应该切到A的下一首B，可是由于顺序播放和随机播放不在一个list中，却共用一个current标记的原因
 * 导致这种情况下歌曲A会被再次播放一遍
 */
public class MyMedia extends MediaPlayer implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,Runnable{

    /**
     * 歌曲信息列表
     */
    private List<MusicModel> list;

    /**
     * 乱序以后的列表
     */
    private List<MusicModel> randomList;

    private int currentMusic = 0;//记录当前所播放歌曲在list中的下标

    private MusicModel musicModel;//记录下当前歌曲

    /**
     * 三个记录播放顺序的值
     * cycle表示按列表循环
     * single表示单曲循环
     * random表示随机播放
     */
    public static final int CYCLE = 0,SINGLE = 1,RANDOM = 2;
    private int state = CYCLE;//设置默认为列表循环

    private boolean isSingle = false;

    private boolean isRandom = false;

    /**
     * 记录是否已有缓存
     */
    private boolean isCache = false;

    /**
     * 保存歌曲的时间信息
     */
    private String nowTime = "00:00";
    private String maxTime = "00:00";

    /**
     * 歌曲信息对外公开的接口
     */
    private OnMediaPlaying onMediaPlaying;

    /**
     * 做为计时器使用
     */
    private Handler handler;

    public MyMedia(){
        setOnPreparedListener(this);//给自己设定一个加载完成监听
        setOnCompletionListener(this);//播放完一首歌后，继续搞事
    }

    /**
     * 嘿嘿嘿，高大上的firstPlay，用于在打开程序时，随机抽取一首歌播放
     * （这里播放原理不变，依旧是让play方法去播放）
     */
    public void firstPlay(){
        Log.d("firstPlay", list.size() + " .");
        if (list != null){
            int i = new Random().nextInt(list.size());//随机拿一个歌曲出来
            musicModel = list.get(i);
            play(musicModel.getUrl());//传过去播放
            currentMusic = i;//切记把current参数也改过来
        }

    }

    /**
     * 把路径传进来。。。就可以开始播放了
     * @param s 一个歌曲的路径
     */
    public void play(String s){
        if (list != null){
            try {
                setDataSource(s);//设置文件路径 （本行待更改
            } catch (IOException e) {
                e.printStackTrace();
            }
            prepareAsync();
        }
    }

    public void play(int i){
        if (list == null){ return; };

        if (getIsCache()){
            pause();
            stop();
            reset();
        }
        musicModel = list.get(i);
        play(musicModel.getUrl());
        Log.d("play","executed");
    }

    /**
     * 下一首
     * 切歌方法，每一次调用都直接按照既定的播放顺序（循环，单曲，随机）
     */
    public void next(){

        pause();//暂停歌曲的播放
        stop();//完全停止歌曲的播放
        reset();//移除原本加载的歌曲
        switch (state){
            case CYCLE:
                //按列表循环播放
                if (currentMusic < list.size()-1){
                    currentMusic++;
                }else {
                    currentMusic = 0;
                }
                musicModel = list.get(currentMusic);
                play(musicModel.getUrl());
                break;

            case SINGLE:
                //直接单曲循环
                play(musicModel.getUrl());

                break;

            case RANDOM:
                //随机播放
                if (currentMusic < randomList.size()-1){
                    currentMusic++;
                }else {
                    currentMusic = 0;
                }
                musicModel = randomList.get(currentMusic);
                play(musicModel.getUrl());

                break;
        }

    }

    /**
     * 上一首
     * 切换至上一首歌
     */
    public void previous(){

        pause();//暂停歌曲的播放
        stop();//完全停止歌曲的播放
        reset();//移除原本加载的歌曲
        switch (state){
            case CYCLE:
                //按列表循环播放
                if (currentMusic > 0){
                    currentMusic--;
                }else {
                    currentMusic = list.size()-1;
                }
                musicModel = list.get(currentMusic);
                play(musicModel.getUrl());
                break;

            case SINGLE:
                //直接单曲循环
                play(musicModel.getUrl());

                break;

            case RANDOM:
                //随机播放
                if (currentMusic > 0){
                    currentMusic--;
                }else {
                    currentMusic = randomList.size()-1;
                }
                musicModel = randomList.get(currentMusic);
                play(musicModel.getUrl());

                break;
        }

    }

    /**
     * 设置好歌曲列表之后就要设置好随机播放的列表
     * @param list
     */
    public void setMusicList(List<MusicModel> list){
        this.list = list;//顺序列表设置完毕
        OutOfOrderAlgorithm();//乱序列表设置
    }

    /**
     * @param i 设置以何种方式继续播放
     */
    private void setState(int i){
        state = i;
    }

    /**
     * 临时加的方法。。。设计之初不是这样打算的
     * setIsSingle 和 setIsRandom（有空再补具体
     * @param b
     */
    public void setIsSingle(boolean b){
        this.isSingle = b;
        if (b){
            setState(SINGLE);
        }else {
            if (isRandom){
                setState(RANDOM);
            }else {
                setState(CYCLE);
            }
        }
    }

    public void setIsRandom(boolean b){
        this.isRandom = b;
        if (b){
            if (isSingle){
                setState(SINGLE);
            }else {
                setState(RANDOM);
            }
        }else {
            if (isSingle){
                setState(SINGLE);
            }else {
                setState(CYCLE);
            }
        }
    }

    /**
     * 如果已有缓存歌曲返回true，否则false
     */
    public boolean getIsCache(){
        return isCache;
    }

    /**
     * 以下四个方法为时间信息的保存和获取
     */
    public String getNowTime() {
        return nowTime;
    }

    private void setNowTime() {
        int musicTime = getCurrentPosition() / 1000;
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
        this.nowTime = m;
    }

    public String getMaxTime() {
        return maxTime;
    }

    private void setMaxTime() {
        int musicTime = getDuration() / 1000;
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
        this.maxTime = m;
    }

    public void setOnMediaPlaying(OnMediaPlaying onMediaPlaying){
        this.onMediaPlaying = onMediaPlaying;
    }

    /**
     * 缓存完成时的监听
     * 当缓存完毕，自动开始播放歌曲
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        isCache = true;
        mp.start();
    }

    /**
     * 重写。。。每次开始播歌都要传递出歌曲的时间信息
     * @throws IllegalStateException
     */
    @Override
    public void start() throws IllegalStateException {
        super.start();
        if (onMediaPlaying != null){
            setMaxTime();//设置好歌曲时间
            setNowTime();//设置歌曲当前的时间
            onMediaPlaying.OnStartPlay(musicModel.getTitle(),getMaxTime(),getNowTime(),getDuration(),getCurrentPosition());
        }
        if (handler == null){
            handler = new Handler();
        }
        handler.postDelayed(this, 1000);
    }

    /**
     * 每次暂停都要调用接口
     * 顺便关闭计时线程
     * @throws IllegalStateException
     */
    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        if (handler != null){
            handler.removeCallbacks(this);
        }
        if (onMediaPlaying != null){
            onMediaPlaying.OnPausePlay();
        }
    }

    /**
     * 歌曲播放完成监听
     * 当歌曲播放完毕，自动调用切歌方法
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        next();//一首歌播放完成时自动切歌
    }

    /**
     * 乱序算法。。。（为了让随机的宝宝们也能听到固定的上一曲，我也是拼了
     */
    private void OutOfOrderAlgorithm(){
        List<MusicModel> list = new ArrayList<>();
        list.addAll(this.list);

        while (list.size() != 0){
            int i = new Random().nextInt(list.size());
            if (randomList == null){
                randomList = new ArrayList<>();
            }
            randomList.add(list.get(i));
            list.remove(i);
        }
    }

    @Override
    public void run() {
        setNowTime();
        if (onMediaPlaying != null){
            onMediaPlaying.OnPlaying(getNowTime(),getCurrentPosition());
        }
        handler.postDelayed(this,1000);
    }
}
