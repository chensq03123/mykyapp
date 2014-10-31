package com.hustunique.kyplanningapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;


import com.example.ggg.R;
import com.hustunique.Adapters.MainListAdapter;
import com.hustunique.Utils.DataConstances;
import com.hustunique.Utils.Dbhelper;
import com.hustunique.Utils.Main_item;

import java.util.ArrayList;
import java.util.HashMap;
import com.baoyz.swipemenulistview.*;
public class MainActivity extends ActionBarActivity {

	
	private SwipeMenuListView mainlist;
    private ImageView add_bookbtn;
    private Main_item listitems,head,p1,temp_head,temp_currnext,temp_currpa;
    //private ArrayList<Map<String,String>> list;
    private ArrayList<Main_item> list;
    MainListAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);




        InitWidgets();
        InitSwipeMenuListView(MainActivity.this);
        
      
    }
    
    private void InitWidgets(){
    	mainlist=(SwipeMenuListView)findViewById(R.id.main_listview);
        add_bookbtn=(ImageView)findViewById(R.id.add_bookbtn);
        add_bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,BooksListActivity.class);
                startActivity(intent);
            }
        });
    }
    
    /*
     * Coding onMenuItemClickListenner
     * */
    private void InitSwipeMenuListView(Context context){
    	final Context mcontext=context;
    	SwipeMenuCreator creator=new SwipeMenuCreator() {
			
			@Override
			public void create(SwipeMenu menu) {
				// TODO Auto-generated method stub
				SwipeMenuItem deleteitem=new SwipeMenuItem(mcontext);
				deleteitem.setBackground(R.drawable.trashcan);
				deleteitem.setWidth(200);
                deleteitem.setHeight(144);
				menu.addMenuItem(deleteitem);

				SwipeMenuItem poptopitem=new SwipeMenuItem(mcontext);
				poptopitem.setBackground(R.drawable.tothetop);
				poptopitem.setWidth(200);
                poptopitem.setHeight(144);
				menu.addMenuItem(poptopitem);	
			}
		};
		
		 list=new ArrayList<Main_item>();
	       /* for(int i=0;i<20;i++){
	        	HashMap<String, String> map=new HashMap<String, String>();
	        	map.put("bookname",String.valueOf(i));
                if(i==0){
                    head=p1=listitems=new Main_item();
                    p1.item=map;
                }else{
                    listitems=new Main_item();
                    listitems.item=map;
                    p1.next=listitems;
                    p1=listitems;
                }
	        }*/
            head=DataConstances.header;
            p1=head;
            while(p1!=null){
                list.add(p1);
                p1=p1.next;
            }
	        adapter=new MainListAdapter(MainActivity.this, list);
	        mainlist.setAdapter(adapter);
	        mainlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub

				}
			});
	        mainlist.setMenuCreator(creator);
	        mainlist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				// TODO Auto-generated method stub
                switch(index){
                    case 1:Popup(position);break;
                    case 0:break;
                }

				return false;
			}
		});
    }

    private void Popup(int index){
         temp_currpa=list.get(index-1);
         temp_currnext=list.get(index).next;
        temp_currpa.next=temp_currnext;
        temp_head=list.get(index);
        temp_head.next=head;
        head=temp_head;
        p1=head;
        list.clear();
        while(p1!=null){
            list.add(p1);
            p1=p1.next;
        }
        adapter.notifyDataSetChanged();
        Intent intent=new Intent(DataConstances.POPULIST_ACTION);
        sendBroadcast(intent);
    }

    private void Delete(int index){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
