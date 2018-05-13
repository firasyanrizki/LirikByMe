package com.example.asus.LirikByMe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URL;

public class LyricActivity extends AppCompatActivity {

    SQLiteAdapter sqLiteAdapter;
    private TextView mWebsiteText, mTitleText, mLyricText;
    private String title, lyric;
    private URL link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);

        Bundle extras = getIntent().getExtras();
        mWebsiteText = (TextView) findViewById(R.id.link_url);
        mTitleText = (TextView) findViewById(R.id.title_name);
        mLyricText = (TextView) findViewById(R.id.lyric_display);

        title = extras.getString("title");
        sqLiteAdapter = new SQLiteAdapter(this);
        sqLiteAdapter.openToRead();
        Cursor cursor = sqLiteAdapter.selectWhereTitle(title);
        while (cursor.moveToNext()){
            mTitleText.setText(cursor.getString(0));
            mWebsiteText.setText(cursor.getString(1));
            mLyricText.setText(cursor.getString(2));
        }
        sqLiteAdapter.close();
    }
}

