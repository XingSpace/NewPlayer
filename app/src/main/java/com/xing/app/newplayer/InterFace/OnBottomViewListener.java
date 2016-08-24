package com.xing.app.newplayer.InterFace;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by wangxing on 16/8/15.
 * 专门为bottomview的回调设计的监听接口
 */
public interface OnBottomViewListener {

    /**
     * 当上一曲按钮按下时
     */
    public void onBackClick();

    /**
     * 下一曲按钮按下时
     */
    public void onNextClick();

    /**
     * 暂停/播放 按钮按下时
     */
    public void onControlClick();

    /**
     *
     * @param seekBar
     * @param progress
     * @param fromUser 根据官网的原话，应该是用于判断此次触发的改变是否源于用户自己（
     * @param textView
     */
    public void onSeekbarListener(SeekBar seekBar,int progress,boolean fromUser,TextView textView);

    public void onSeekbarStart(SeekBar seekBar);

    public void onSeekbarStop(SeekBar seekBar);
}
