package com.hustunique.Adapters;

import java.util.ArrayList;
import java.util.Map;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ggg.R;
import com.hustunique.Views.Pointwithcolor;

public class ChapterBaseAdapter  extends BaseAdapter{

	private Context mcontext;
	private ArrayList<Map<String,String>> mlist;
	private int togleposit=-1;
	private boolean istogle=false;
	
	public ChapterBaseAdapter(Context context,ArrayList<Map<String,String>> list){
		this.mcontext=context;
		this.mlist=list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=LayoutInflater.from(mcontext).inflate(R.layout.chapterlist_item, null);
			holder.chapname=(TextView)arg1.findViewById(R.id.chapname);
			holder.img=(ImageView)arg1.findViewById(R.id.deletebtn);
			holder.point=(Pointwithcolor)arg1.findViewById(R.id.chap_point);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		
		if(arg0==togleposit&&!istogle){
			istogle=true;
			arg1.setBackgroundColor(Color.CYAN);
			holder.point.setColor(Color.BLUE);
			holder.img.setVisibility(View.INVISIBLE);
		}else{
			istogle=false;
			arg1.setBackgroundColor(Color.WHITE);
			holder.point.setColor(Color.GREEN);
			holder.img.setVisibility(View.VISIBLE);
		}
		
		return arg1;
	}
	
	public void togle(int postion){
		this.togleposit=postion;
	}
	
	private class ViewHolder{
		Pointwithcolor point;
		TextView chapname;
		ImageView img;
	}
}
