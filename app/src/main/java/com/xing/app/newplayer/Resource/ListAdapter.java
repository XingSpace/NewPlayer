package com.xing.app.newplayer.Resource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xing.app.newplayer.R;

import java.util.List;

/**
 * Created by wangxing on 16/8/23.
 */
public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<MusicModel> list;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, List<MusicModel> list){
        this.context = context;
        this.list = list;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.list_item,null);

        TextView title = (TextView) convertView.findViewById(R.id.titles);

        TextView name = (TextView) convertView.findViewById(R.id.name);

        TextView album = (TextView) convertView.findViewById(R.id.album);

        title.setText(((MusicModel)getItem(position)).getTitle());

        name.setText(((MusicModel)getItem(position)).getArtist());

        album.setText(((MusicModel)getItem(position)).getAlbum());

        return convertView;
    }
}
