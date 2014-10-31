package com.hustunique.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ggg.R;
import com.hustunique.Utils.DataConstances;
import com.hustunique.Views.Pointwithcolor;

import java.util.ArrayList;

/**
 * Created by chensq-ubuntu on 10/31/14.
 */
public class ChapBaseAdapter extends BaseAdapter{


    Context mcontext;
    ArrayList<String> mgrouplist;
    private int color= DataConstances.colors[0];

    public ChapBaseAdapter(Context context,ArrayList<String> grouplist,int color){
        this.mgrouplist=grouplist;
        this.mcontext=context;
        this.color=color;
    }


    @Override
    public int getCount() {
        return mgrouplist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view= LayoutInflater.from(mcontext).inflate(R.layout.addbooklist_item, null);
            holder.addpoint=(Pointwithcolor) view.findViewById(R.id.add_pointwithcolor);
            holder.addtext=(TextView) view.findViewById(R.id.add_name);
            view.setTag(holder);
        }else
            holder=(ViewHolder) view.getTag();

        holder.addtext.setText(mgrouplist.get(i));
        holder.addpoint.setColor(color);

        return view;
    }
    public void setColor(int color){
        this.color=color;
    }

    private class ViewHolder{
        TextView addtext;
        Pointwithcolor addpoint;
    }

}
