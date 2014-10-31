package com.hustunique.Adapters;

import java.util.ArrayList;
import java.util.Map;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ggg.R;
import com.hustunique.Utils.DataConstances;
import com.hustunique.Views.Pointwithcolor;

public class ChapterBaseAdapter  extends BaseAdapter{

	private Context mcontext;
	private ArrayList<Map<String,String>> mlist;
	private int togleposit=-1;
	private boolean istogle=false;
	private int color= DataConstances.colors[0];
    private boolean[] tags;

	public ChapterBaseAdapter(Context context,ArrayList<Map<String,String>> list,int color){
		this.mcontext=context;
		this.mlist=list;
        tags=new boolean[mlist.size()];
        for(int i=0;i<mlist.size();i++)
            tags[i]=false;
	}

    public void setColor(int color){
        this.color=color;
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
        final String tag=mlist.get(arg0).get("chapname");
        final RotateAnimation animation=new RotateAnimation(0f,-45f,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);;
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

        animation.setDuration(900);
        holder.chapname.setText(mlist.get(arg0).get("chapname"));
        holder.img.setImageResource(R.drawable.rotate);
        holder.point.setColor(color);
        holder.img.setVisibility(View.VISIBLE);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.bringToFront();
                view.setAnimation(animation);
                Log.i("tag",mlist.get(arg0).get("chapname"));
                final ImageView v=(ImageView)view;
                //animation.setFillAfter(true);
               // animation.setFillEnabled(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                   @Override
                   public void onAnimationStart(Animation animation) {
                       v.setVisibility(View.INVISIBLE);
                   }

                   @Override
                   public void onAnimationEnd(Animation animation) {
                       v.setVisibility(View.VISIBLE);
                   }

                   @Override
                   public void onAnimationRepeat(Animation animation) {

                   }
               });
                animation.startNow();
                tags[arg0]=true;
            }
        });

        if(tags[arg0]){
            holder.img.setImageResource(R.drawable.rotatelater);
        }else
            holder.img.setImageResource(R.drawable.rotate);
        	/*if(arg0==togleposit&&!istogle){
			istogle=true;
			arg1.setBackgroundColor(color+100);
			holder.point.setColor(color+40);
			holder.img.setVisibility(View.INVISIBLE);
		}else{
			istogle=false;
			arg1.setBackgroundColor(Color.WHITE);

		}*/

		return arg1;
	}
	
	public void togle(int postion){
		this.togleposit=postion;
	}

    public boolean[] getSelections(){
        return tags;
    }

	private class ViewHolder{
		Pointwithcolor point;
		TextView chapname;
		ImageView img;
	}
}
