package com.xing.app.newplayer.ToolKit;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.xing.app.newplayer.Resource.MusicModel;
import com.xing.app.newplayer.Resource.StaticResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxing on 16/8/2.
 */
public class InitResource {

    public static void findMusicResource(Context context){

        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        List<MusicModel> list = new ArrayList<>();

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                MusicModel musicModel = new MusicModel();

                String s = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                musicModel.setTitle(s);//歌曲名字

                s = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                musicModel.setAlbum(s);//专辑名

                s = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                musicModel.setArtist(s);//歌手名

                s = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                musicModel.setUrl(s);//歌曲路径

                musicModel.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                //歌曲时长

                musicModel.setSize(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                //歌曲大小

                list.add(musicModel);

            }while (cursor.moveToNext());
        }

        StaticResource.setMusicModelList(list);

    }

}
