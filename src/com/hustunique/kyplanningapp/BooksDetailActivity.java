package com.hustunique.kyplanningapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ggg.R;
import com.hustunique.Adapters.ChapterBaseAdapter;
import com.hustunique.Utils.DataConstances;
import com.hustunique.Utils.Dbhelper;
import com.hustunique.Utils.Main_item;
import com.hustunique.Views.Pointwithcolor;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BooksDetailActivity extends ActionBarActivity{

	private ListView chapterlistview;
    private Pointwithcolor largpoint;
    private TextView booknamechar,bookname,publisher,author,progress;
    private TextView completebtn;
    private int color;
    private RotateAnimation animation;
    private  Map<String,String> map;
    ArrayList<Map<String,String>> list,booklist;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chapters_layout);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // Holo light action bar color is #DDDDDD
            int actionBarColor = Color.rgb(0x25, 0xdc, 0xca);
            tintManager.setTintColor(actionBarColor);
        }

        Initwidgets();


        String id=getIntent().getStringExtra("BOOKSID");


        booklist=Dbhelper.querybook("select * from book where id="+id,null);
        list= Dbhelper.querychapter("select * from chaptable where bookid="+id,null);
        map=booklist.get(0);
        color=Integer.valueOf(map.get("color"));
        largpoint.setColor(color);
        bookname.setText(map.get("bookname"));
        booknamechar.setText(map.get("bookname").substring(0,1));
        publisher.setText(map.get("publisher"));
        author.setText(map.get("author"));
        progress.setText(map.get("chapcomp")+"/"+map.get("nofchap"));
        final ChapterBaseAdapter adapter=new ChapterBaseAdapter(BooksDetailActivity.this, list,this.color);
	    chapterlistview.setAdapter(adapter);

        completebtn.setOnClickListener(new View.OnClickListener() {
            Main_item p1,p2;
            @Override
            public void onClick(View view) {
                Main_item tag= DataConstances.header;
                while(tag.next!=null&&tag.item!=null){
                    tag=tag.next;
                }
                p1=tag;
                boolean[] tags=adapter.getSelections();
                for(int i=0;i<list.size();i++){
                    if(tags[i]){
                        if(p1.item==null){
                            HashMap<String,String> mapt=new HashMap<String, String>();
                            mapt.put("chapname",list.get(i).get("chapname"));
                            mapt.put("bookname",map.get("bookname"));
                            mapt.put("color",String.valueOf(color));
                            p1.item=mapt;
                        }else{
                            p2=new Main_item();
                            HashMap<String,String> mapt=new HashMap<String, String>();
                            mapt.put("chapname",list.get(i).get("chapname"));
                            mapt.put("bookname",map.get("bookname"));
                            mapt.put("color",String.valueOf(color));
                            p2.item=mapt;
                            p1.next=p2;
                            p1=p2;
                        }
                    }
                }
                Intent intent =new Intent(DataConstances.ADDPLAN_ACTION);
                sendBroadcast(intent);
                BooksDetailActivity.this.finish();

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
            completebtn=(TextView)findViewById(R.id.addplan_completebtn);
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
