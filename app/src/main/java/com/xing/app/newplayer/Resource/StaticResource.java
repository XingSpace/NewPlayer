package com.xing.app.newplayer.Resource;

import java.util.List;

/**
 * Created by wangxing on 16/8/2.
 */
public class StaticResource {

    /**
     * 保存好歌曲信息
     */
    private static List<MusicModel> musicModelList;

    public static void setMusicModelList(List<MusicModel> list){
        musicModelList = list;
    }

    public static List<MusicModel> getMusicModelList(){
        return musicModelList;
    }

}
