package com.hustunique.kyplanningapp;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggg.R;
import com.hustunique.Adapters.ChapExpandableListVIewAdapter;
import com.hustunique.Utils.BookInfo;
import com.hustunique.Utils.DataConstances;
import com.hustunique.Utils.HttpHelper;
import com.hustunique.Utils.JsonHelper;

public class AddBooksActivity extends Activity{
	
	private ExpandableListView chaplistview;
	private ArrayList<String> grouplist;
	private ArrayList<ArrayList<String>> childlist;
	private Button scanbook;
	private BookInfo book;
	private TextView bookname,author,publisher; 
	private ChapExpandableListVIewAdapter adapter;
	private View footer_add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbook_layout);
		
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
		chaplistview.setAdapter(adapter);
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
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==DataConstances.REQUEST_CODE){
			if(resultCode==RESULT_OK){
				String isbncode=data.getStringExtra("result");
				Toast.makeText(AddBooksActivity.this, isbncode, 2000).show();
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
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
			return null;
		}
		
	}	
}
