package com.hustunique.kyplanningapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ggg.R;
import com.hustunique.Adapters.BooksBaseAdapter;
import com.hustunique.Utils.Dbhelper;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by chensq-ubuntu on 10/25/14.
 */
public class BooksListActivity extends Activity {

    private ArrayList<Map<String,String>> mlist;
    private ListView bookslistview;
    private BooksBaseAdapter madapter;
    private TextView addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.allbooks_layout);

        addbtn=(TextView)findViewById(R.id.addbook_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BooksListActivity.this,AddBooksActivity.class);
                startActivity(intent);
            }
        });
        bookslistview=(ListView)findViewById(R.id.allbooks_list);
        new MyAsyncTask().execute();
        bookslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(BooksListActivity.this,BooksDetailActivity.class);
                intent.putExtra("BOOKSID",mlist.get(i).get("id"));
                startActivity(intent);
            }
        });

    }

    private class MyAsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            mlist= Dbhelper.querybook("select * from book",null);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            madapter=new BooksBaseAdapter(BooksListActivity.this,mlist);
            bookslistview.setAdapter(madapter);
        }
    }

}
