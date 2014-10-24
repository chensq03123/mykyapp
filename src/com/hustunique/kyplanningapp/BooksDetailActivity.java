package com.hustunique.kyplanningapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.ggg.R;
import com.hustunique.Adapters.ChapterBaseAdapter;

public class BooksDetailActivity extends ActionBarActivity{
	
	private ActionBar bar;
	private ListView chapterlistview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chapters_layout);
		Initwidgets();
		
		 ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
	        for(int i=0;i<20;i++){
	        	HashMap<String, String> map=new HashMap<String, String>();
	        	map.put(String.valueOf(i),"");
	        	list.add(map);
	        }
	  
	        final ChapterBaseAdapter adapter=new ChapterBaseAdapter(BooksDetailActivity.this, list);
	        chapterlistview.setAdapter(adapter);			
	        chapterlistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
						//adapter.togle(arg2);
						//adapter.notifyDataSetInvalidated();
					chapterlistview.setSelection(arg2);
				}
			});
	
	}

	private void Initwidgets(){
			chapterlistview=(ListView)findViewById(R.id.chapter_listview);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
}
