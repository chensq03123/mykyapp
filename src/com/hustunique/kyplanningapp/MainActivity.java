package com.hustunique.kyplanningapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.ggg.R;
import com.hustunique.Adapters.MainListAdapter;

public class MainActivity extends ActionBarActivity {

	
	private SwipeMenuListView mainlist;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        InitWidgets();
        InitSwipeMenuListView(MainActivity.this);
        
      
    }
    
    private void InitWidgets(){
    	ActionBar bar=this.getActionBar();
    	bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0x00, 0xe5, 0xee)));
    	bar.setTitle("kyplanning");
    	mainlist=(SwipeMenuListView)findViewById(R.id.main_listview);
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
				deleteitem.setBackground(new  ColorDrawable(Color.YELLOW));
				deleteitem.setWidth(200);
				menu.addMenuItem(deleteitem);
				
				SwipeMenuItem poptopitem=new SwipeMenuItem(mcontext);
				poptopitem.setBackground(new ColorDrawable(Color.RED));
				poptopitem.setWidth(200);
				menu.addMenuItem(poptopitem);	
			}
		};
		
		  ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
	        for(int i=0;i<20;i++){
	        	HashMap<String, String> map=new HashMap<String, String>();
	        	map.put(String.valueOf(i),"");
	        	list.add(map);
	        }
	        
	        MainListAdapter adapter=new MainListAdapter(MainActivity.this, list);
	        mainlist.setAdapter(adapter);
	        mainlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(MainActivity.this,BooksDetailActivity.class);
					startActivity(intent);
					Toast.makeText(MainActivity.this, "itemclick", 1000).show();
				}
			});
	        mainlist.setMenuCreator(creator);
	        mainlist.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				// TODO Auto-generated method stub
				return false;
			}
		});
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
