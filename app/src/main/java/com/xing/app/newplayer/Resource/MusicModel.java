package com.xing.app.newplayer.Resource;

/**
 * Created by wangxing on 16/8/2.
 */
public class MusicModel {

    /**
     * 歌曲名
     */
    private String title;

    /**
     * 专辑名
     */
    private String album;

    /**
     * 歌手名
     */
    private String artist;

    /**
     * 路径地址
     */
    private String url;

    /**
     * 播放时长
     */
    private int duration;

    /**
     * 歌曲大小
     */
    private int size;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
