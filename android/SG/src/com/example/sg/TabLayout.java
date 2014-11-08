package com.example.sg;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayout extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		TabHost tabHost = getTabHost();
        
        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("Info");
        // setting Title and Icon for the Tab
        photospec.setIndicator("Tranc", getResources().getDrawable(R.drawable.photos_lent));
        Intent photosIntent = new Intent(this, Lent.class);
        photospec.setContent(photosIntent);
         
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Borrowed");        
        songspec.setIndicator("Coupons", getResources().getDrawable(R.drawable.photos_borrowed));
        Intent songsIntent = new Intent(this, Borrowed.class);
        songspec.setContent(songsIntent);
        
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
	}
	
}
