package com.hustunique.kyplanningapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggg.R;
import com.hustunique.Adapters.ChapterBaseAdapter;
import com.hustunique.Utils.Dbhelper;
import com.hustunique.Views.Pointwithcolor;

public class BooksDetailActivity extends ActionBarActivity{

	private ListView chapterlistview;
    private Pointwithcolor largpoint;
    private TextView booknamechar,bookname,publisher,author,progress;
    private int color;
    ArrayList<Map<String,String>> list,booklist;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chapters_layout);
		Initwidgets();
        String id=getIntent().getStringExtra("BOOKSID");


        booklist=Dbhelper.querybook("select * from book where id="+id,null);
        list= Dbhelper.querychapter("select * from chaptable where bookid="+id,null);
        Map<String,String> map=booklist.get(0);
        color=Integer.valueOf(map.get("color"));
        largpoint.setColor(color);
        bookname.setText(map.get("bookname"));
        booknamechar.setText(map.get("bookname").substring(0,1));
        publisher.setText(map.get("publisher"));
        author.setText(map.get("author"));
        progress.setText(map.get("chapcomp")+"/"+map.get("nofchap"));

        Toast.makeText(BooksDetailActivity.this,id+"\\"+list.size(),Toast.LENGTH_LONG).show();
	    final ChapterBaseAdapter adapter=new ChapterBaseAdapter(BooksDetailActivity.this, list,this.color);
	    chapterlistview.setAdapter(adapter);
	    chapterlistview.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
						adapter.togle(arg2);
						adapter.notifyDataSetInvalidated();
					//chapterlistview.setSelection(arg2);

				}
			});
	
	}

	private void Initwidgets(){
			chapterlistview=(ListView)findViewById(R.id.chapter_listview);
            bookname=(TextView)findViewById(R.id.detail_bookname);
            booknamechar=(TextView)findViewById(R.id.detail_booknamechar);
            author=(TextView)findViewById(R.id.detail_author);
            publisher=(TextView)findViewById(R.id.detail_publisher);
            progress=(TextView)findViewById(R.id.detail_progress);
            largpoint=(Pointwithcolor)findViewById(R.id.detail_point);
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
