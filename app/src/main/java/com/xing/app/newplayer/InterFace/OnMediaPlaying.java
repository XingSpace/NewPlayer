package com.xing.app.newplayer.InterFace;

/**
 * Created by wangxing on 16/8/19.
 */
public interface OnMediaPlaying {
    /**
     * 在歌曲开始播放时调用
     * @param name 歌曲的名字
     * @param maxTime 歌曲时间的总长度 返回格式为 00：00
     * @param nowTime 当前播放到了哪个时间 返回格式为 00：00
     * @param duration 歌曲时间总长的int类型返回。。。单位为毫秒
     * @param progress 这个int数据是一个以毫秒为单位的时间数据，表示当前歌曲已经播放了这么多
     */
    public void OnStartPlay(String name,String maxTime,String nowTime,int duration,int progress);

    /**
     * 歌曲开始播放之后，每隔一秒会被调用一次
     * @param nowTime 该字符串格式为 ”00：00“ ，表示当前已经播放的时间
     * @param progress 以毫秒为单位记录当前歌曲播放了多久
     */
    public void OnPlaying(String nowTime,int progress);

    /**
     * 歌曲被暂停时会被调用
     */
    public void OnPausePlay();
}
