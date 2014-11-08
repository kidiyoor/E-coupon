package com.example.sg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class Borrowed extends Activity {
	String result;
	InputStream is;
	
	String a ;
	//List<AtomPayment> items;
	CustomListView adapter=null ;
	//List<RowItem> rowItems=null;
	ListView list1;
	public static List<String[]> myList = new ArrayList<String[]>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aborrowed);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		
		
		list1=(ListView)findViewById(R.id.list);

		a="1";
		retrive();
		//items=new ArrayList<AtomPayment>();
		ArrayList<RowItem> rowItems = new ArrayList<RowItem>();
		
		
		for(int i=0;i<myList.size();i++)
		{
			//AtomPayment testAtomPayment = new AtomPayment(myList.get(i)[1], 13);
			//System.out.println(myList.get(i)[0]);
			RowItem item = new RowItem(R.drawable.redi, myList.get(i)[0], myList.get(i)[1]);
			rowItems.add(item);
		}
		
		//registerForContextMenu(list1);	
		adapter = new CustomListView(this,android.R.layout.activity_list_item, rowItems);
		list1.setAdapter(adapter);
		// String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			//        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			  //      "Linux", "OS/2" };
		 //final ArrayList<String> list = new ArrayList<String>();
		   // for (int i = 0; i < values.length; ++i) {
		     // list.add(values[i]);
		   // }
		 //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			//        android.R.layout.simple_list_item_1, list);
			  //  setListAdapter(adapter);
		//couponAdapter adapter = new couponAdapter(Borrowed.this, R.layout.list_coupons, items);
		//ListView atomPaysListView = (ListView)findViewById(R.id.clist);
		//atomPaysListView.setAdapter(adapter);
		  list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    @Override
			    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
			    	
			    		//String snoS=String.valueOf(sno[pos]);
			    	//Intent open = new Intent("android.intent.action.CONTEXT");
					//startActivity(open);
			    	//setContentView(R.layout.cmenu);
			    	//clickVal=pos;
			    	//Intent i= new Intent(Borrowed.this,Coupon.class);
			    	//startActivity(i);
			    	
			    	
			    	
			    }

				
				
			});
	}
	
	void retrive(){
		try {
			 HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://192.168.0.199/egift/a.php");
		    //HttpPost httppost = new HttpPost("http://192.168.0.199/testj.json");
		   System.out.println("connect to website first time");
		     // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	            
				nameValuePairs.add(new BasicNameValuePair("a", a));
	            
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
       // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
        
	       HttpEntity entity = response.getEntity();

   	      is = entity.getContent();
   	    System.out.println("..............");
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
    

		download();
try{
	System.out.println("inside try catch1");
	//System.out.println("res="+result);
	//result="{\"a\":\"success\"}";
	//{"a":"success"}
	//JSONObject json_data=new JSONObject(result);
	//System.out.print("ll");
JSONArray jArray = new JSONArray(result);
System.out.println("inside try catch2");

//	int k=0;
 int y = jArray.length();

// System.out.println("y="+y);
 myList = new ArrayList<String[]>();
for(int i=1;i<y;i++)
{
	JSONObject js = jArray.getJSONObject(i);
	String[] a = new String[3];
	a[0]=js.getString("coup_name");
	a[1]=js.getString("expdate");
	a[2]=js.getString("coup_id");
	myList.add(a);
}
	System.out.println("inside try catch3");
	
//JSONObject js = jArray.getJSONObject(0);
//System.out.println("inside try catch 3.5");
//JSONObject json_data=new JSONObject(result);
//System.out.println(json_data.getString("a"));
//Toast.makeText(getApplicationContext(), json_data.getString("coup_name"), 
	//	   Toast.LENGTH_LONG).show();

System.out.println("inside try catch4");
	


} catch (JSONException e) {System.out.println("caught here");

}

	}
	void download()
	{ System.out.println("in download");
		
		try{
			
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			       StringBuilder sb = new StringBuilder();
		           String line = null;
		           int y=0;
		           while ((line = reader.readLine()) != null&&y==0) {
		                   sb.append(line + "\n");y=1;

		           }
		        
		           is.close();
		           result = sb.toString();
		          
		           System.out.println("end of first half");
		   }catch(Exception e){
		           Log.e("log_tag", "Error converting result "+e.toString());
		           System.out.println("caught in download");
		           
		   }

	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Context Menu");
		menu.add(0, v.getId(), 0, "Name");
		menu.add(0, v.getId(), 0, "Delete");
		menu.add(0, v.getId(), 0, "Cancel");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Action 1") {
			Toast.makeText(this, "Action 1 invoked", Toast.LENGTH_SHORT).show();
		} else if (item.getTitle() == "Action 2") {
			Toast.makeText(this, "Action 2 invoked", Toast.LENGTH_SHORT).show();
		} else if (item.getTitle() == "Action 3") {
			Toast.makeText(this, "Action 3 invoked", Toast.LENGTH_SHORT).show();
		} else {
			return false;
		}
		return true;
	}

}
