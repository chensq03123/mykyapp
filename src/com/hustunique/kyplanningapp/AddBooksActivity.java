package com.hustunique.kyplanningapp;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggg.R;
import com.hustunique.Adapters.ChapExpandableListVIewAdapter;
import com.hustunique.Utils.BookInfo;
import com.hustunique.Utils.DataConstances;
import com.hustunique.Utils.Dbhelper;
import com.hustunique.Utils.HttpHelper;
import com.hustunique.Utils.JsonHelper;
import com.hustunique.Views.Pointwithcolor;

public class AddBooksActivity extends Activity{
	
	private ExpandableListView chaplistview;
	private ArrayList<String> grouplist;
	private ArrayList<ArrayList<String>> childlist;
	private Button scanbook,completebtn;
	private BookInfo book;
	private TextView bookname,author,publisher; 
	private ChapExpandableListVIewAdapter adapter;
	private View footer_add;
    private LinearLayout Colorselect_layout;
    private ArrayList<Pointwithcolor> colorlist;
	private ActionBar actionBar;
    private int colorselected=DataConstances.colors[0];


    private void setActionBarLayout( int layoutId ){

            actionBar = getActionBar( );

        if( null != actionBar ){
            actionBar.setDisplayShowHomeEnabled( false );
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater)   this.getSystemService(AddBooksActivity.this.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(layoutId, null);
            ActionBar.LayoutParams layout = new  ActionBar.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            actionBar.setCustomView(v,layout);

        }

    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbook_layout);
        setActionBarLayout(R.layout.actionbar_port_layout);
		IniteWidgets();
		/*for(int i=0;i<200;i++){
				grouplist.add(String.valueOf(i));
				ArrayList<String> templist=new ArrayList<String>();
				for(int j=0;j<10;j++){
					templist.add(String.valueOf(j));
				}
				childlist.add(templist);
		}*/
		scanbook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
						Intent intent=new Intent(AddBooksActivity.this,MipcaActivityCapture.class);
						startActivityForResult(intent, DataConstances.REQUEST_CODE);
			}
		});

		adapter=new ChapExpandableListVIewAdapter(AddBooksActivity.this, grouplist, childlist);
		chaplistview.addFooterView(footer_add);
        chaplistview.setGroupIndicator(null);
		chaplistview.setAdapter(adapter);

        completebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grouplist.size()==0){
                    Toast.makeText(AddBooksActivity.this,"错误！还没填写书信息",Toast.LENGTH_LONG).show();
                }else{
                    book.setcolor(colorselected);
                    Dbhelper.insertbook(book);
                    int id=Integer.parseInt(Dbhelper.querybookid("select * from book where bookname=\""+book.getname()+"\"",null));
                   for(int i=0;i<grouplist.size();i++){
                       Dbhelper.insertchap(grouplist.get(i),id);
                   }
                }
            }
        });

	}
	
	private void IniteWidgets(){
		chaplistview=(ExpandableListView)findViewById(R.id.add_chapexplist);
		scanbook=(Button)findViewById(R.id.scan);
		bookname=(TextView)findViewById(R.id.add_bookname);
		author=(TextView)findViewById(R.id.add_author);
		publisher=(TextView)findViewById(R.id.add_publisher);
		grouplist=new ArrayList<String>();
		childlist=new ArrayList<ArrayList<String>>();
		footer_add=LayoutInflater.from(AddBooksActivity.this).inflate(R.layout.chapterlist_item, null);
        Colorselect_layout=(LinearLayout)findViewById(R.id.colorselect_layout);
	    colorlist=new ArrayList<Pointwithcolor>();
        completebtn=(Button)findViewById(R.id.addbook_complete);
	    for(int i=0;i<DataConstances.colors.length;i++){

            Pointwithcolor point=new Pointwithcolor(AddBooksActivity.this);
           // ViewGroup.LayoutParams para=point.getLayoutParams();
            //para.width=30;
            //para.height=30;
            //point.setLayoutParams(para);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(60,60);
            layoutParams.setMargins(10, 10, 10, 10);
            point.setColor(DataConstances.colors[i]);
            point.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionBar.setBackgroundDrawable(new ColorDrawable(((Pointwithcolor) view).getColor()));
                    colorselected=((Pointwithcolor) view).getColor();
                }
            });
            point.setOnTouchListener(new View.OnTouchListener() {
                int tempcolor;
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN: {
                            tempcolor = ((Pointwithcolor) view).getColor();
                            ((Pointwithcolor) view).setColor(tempcolor-90);
                            break;
                        }
                        case MotionEvent.ACTION_UP:{
                            ((Pointwithcolor)view).setColor(tempcolor);
                            break;
                        }
                    }
                    return false;
                }
            });
            colorlist.add(point);
            Colorselect_layout.addView(point,layoutParams);
        }

    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==DataConstances.REQUEST_CODE){
			if(resultCode==RESULT_OK){
				String isbncode=data.getStringExtra("result");
				Toast.makeText(AddBooksActivity.this, isbncode, Toast.LENGTH_LONG).show();
				new HttpgetAsynTask().execute(isbncode);
			}
		}
	}

	private class HttpgetAsynTask extends AsyncTask<String, Void, Integer>{
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bookname.setText(book.getname());
			author.setText(book.getauthor());
			publisher.setText(book.getpublisher());
			//Toast.makeText(AddBooksActivity.this, book.getcatolog(), 2000).show();
			adapter.notifyDataSetInvalidated();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
		String result=HttpHelper.Httpget(arg0[0]);
		JsonHelper jhelper=new JsonHelper(result);
		try {
			book=jhelper.getBookInfor();
			
			ArrayList<String> cl =new ArrayList<String>();
			int tag=0;
			String catalogstr=book.getcatolog();
			String[] catalarry=catalogstr.split("\n");
			for(int i=0;i<catalarry.length;i++){
				if(catalarry[i].contains("第")&&catalarry[i].contains("章")){
					grouplist.add(catalarry[i]);
					cl=new ArrayList<String>();
					childlist.add(cl);
				}else{
					cl.add(catalarry[i]);
				}
			}
            book.setChapcount(grouplist.size());
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		}
		
	}	
}
